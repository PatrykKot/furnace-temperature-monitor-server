package com.kotlarz.service.temperature.sensor;

import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.TemperatureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AliveSensorResolver
{
    private static final Integer MAX_NOT_ACTIVE_TIME_MIN = 10;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private TemperatureService temperatureService;

    public void resolveInactiveSensors()
    {
        DateTime minLatestLogTime = DateTime.now()
                        .minusMinutes( MAX_NOT_ACTIVE_TIME_MIN );

        sensorService.getAll().stream()
                        .filter( sensor -> {
                            Optional<TemperatureLog> optionalLog = temperatureService.findLatestForSensor( sensor );
                            if ( optionalLog.isPresent() )
                            {
                                DateTime logDate = new DateTime( optionalLog.get().getDate() );
                                return minLatestLogTime.isAfter( logDate );
                            }
                            else
                            {
                                return true;
                            }
                        } )
                        .forEach( sensor -> sensor.setIsAlive( false ) );
    }
}
