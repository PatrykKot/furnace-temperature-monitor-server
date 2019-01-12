package com.kotlarz.service.temperature;

import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.sensor.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LatestTemperaturesResolver
{
    @Autowired
    private TemperatureService temperatureService;

    @Autowired
    private SensorService sensorService;

    @Transactional
    public List<TemperatureLog> getLatest()
    {
        return sensorService.getAll().stream()
                        .map( sensor -> temperatureService.findLatestForSensor( sensor )
                                        .orElse( null ) )
                        .filter( Objects::nonNull )
                        .collect( Collectors.toList() );
    }
}
