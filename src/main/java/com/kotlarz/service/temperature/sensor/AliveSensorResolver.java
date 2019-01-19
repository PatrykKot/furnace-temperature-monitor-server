package com.kotlarz.service.temperature.sensor;

import com.kotlarz.domain.Sensor;
import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.TemperatureService;
import com.kotlarz.web.api.temperature.live.TemperaturePublisher;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AliveSensorResolver
{
    @Autowired
    private SensorService sensorService;

    @Autowired
    private TemperatureService temperatureService;

    @Autowired
    private TemperaturePublisher publisher;

    @Scheduled( fixedRate = 10000 )
    public void resolveInactiveSensors()
    {
        DateTime now = DateTime.now();

        List<Sensor> toChange = sensorService.getAll().stream()
                        .filter( Sensor::getIsAlive )
                        .filter( sensor -> {
                            Optional<TemperatureLog> optionalLog = temperatureService.findLatestForSensor( sensor );
                            if ( optionalLog.isPresent() )
                            {
                                DateTime logDate = new DateTime( optionalLog.get().getDate() );
                                return now.minusSeconds( sensor.getMaxInactiveTimeSec() ).isAfter( logDate );
                            }
                            else
                            {
                                return true;
                            }
                        } )
                        .collect( Collectors.toList() );

        if ( !toChange.isEmpty() )
        {
            toChange.forEach( sensor -> sensor.setIsAlive( false ) );
            publisher.publishLatest();
        }
    }
}
