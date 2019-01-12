package com.kotlarz.web.api.temperature.sensor.rest;

import com.kotlarz.service.temperature.sensor.SensorService;
import com.kotlarz.web.api.temperature.sensor.dto.SensorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "temperature/sensor" )
public class SensorController
{
    @Autowired
    private SensorService sensorService;

    @GetMapping
    public List<SensorDto> all()
    {
        return sensorService.getAll().stream()
                        .map( SensorDto::from )
                        .collect( Collectors.toList() );
    }

    @PostMapping
    public void update( @RequestBody SensorDto dto )
    {
        sensorService.change( dto );
    }

    @PostMapping( "initialize" )
    public void initialize( @RequestParam( "id" ) Long sensorId )
    {
        sensorService.initialize( sensorId );
    }

    @PostMapping( "reset" )
    public void reset( @RequestParam( "id" ) Long sensorId )
    {
        sensorService.reset( sensorId );
    }
}
