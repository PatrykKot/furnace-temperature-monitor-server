package com.kotlarz.service.temperature.reporter;

import com.kotlarz.domain.Sensor;
import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.TemperatureService;
import com.kotlarz.service.temperature.sensor.SensorService;
import com.kotlarz.web.api.temperature.dto.NewTemperatureDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
public class UncompressedReporter
{
    @Autowired
    private TemperatureService temperatureService;

    @Autowired
    private SensorService sensorService;

    public void report( List<NewTemperatureDto> temperatures )
    {
        log.info( "Reporting " + temperatures.size() + " new logs" );
        List<Sensor> sensors = resolveSensors( temperatures );
        sensors.forEach( sensor -> sensor.setIsAlive( true ) );

        log.info( "Found " + sensors.size() + " sensors for new logs" );
        List<TemperatureLog> logs = resolveLogs( temperatures, sensors );

        log.info( "Saving " + logs.size() + " new log entities" );
        temperatureService.saveAll( logs );
    }

    private List<Sensor> resolveSensors( List<NewTemperatureDto> temperatureDtos )
    {
        return temperatureDtos.stream()
                        .map( NewTemperatureDto::getAddress )
                        .distinct()
                        .map( address -> sensorService.getByAddress( address )
                                        .orElseGet( () -> Sensor.createNew( address ) ) )
                        .collect( Collectors.toList() );
    }

    private List<TemperatureLog> resolveLogs( List<NewTemperatureDto> temperatureDtos, List<Sensor> sensors )
    {
        return temperatureDtos.stream()
                        .map( dto -> TemperatureLog.builder()
                                        .date( dto.getDate() )
                                        .sensor( sensors.stream()
                                                                 .filter( sensor -> sensor.getAddress()
                                                                                 .equals( dto.getAddress() ) )
                                                                 .findFirst()
                                                                 .orElseThrow( RuntimeException::new ) )
                                        .value( dto.getValue() )
                                        .build() )
                        .collect( Collectors.toList() );
    }
}
