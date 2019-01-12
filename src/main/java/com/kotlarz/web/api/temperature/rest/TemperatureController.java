package com.kotlarz.web.api.temperature.rest;

import com.kotlarz.service.temperature.LatestTemperaturesResolver;
import com.kotlarz.service.temperature.TemperatureService;
import com.kotlarz.web.api.temperature.dto.SingleSensorTemperatureLogDto;
import com.kotlarz.web.api.temperature.sensor.dto.SensorWithLogsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "temperature" )
public class TemperatureController
{
    @Autowired
    private TemperatureService temperatureService;

    @Autowired
    private LatestTemperaturesResolver latestTemperaturesResolver;

    @GetMapping( "latest" )
    public List<SingleSensorTemperatureLogDto> latest()
    {
        return latestTemperaturesResolver.getLatest().stream()
                        .map( SingleSensorTemperatureLogDto::from )
                        .collect( Collectors.toList() );
    }

    @GetMapping( "later/{date}" )
    public List<SensorWithLogsDto> laterThan( @PathVariable( "date" ) Long date )
    {
        return temperatureService.findLaterThan( new Date( date ) );
    }
}
