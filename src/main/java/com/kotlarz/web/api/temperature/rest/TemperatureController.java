package com.kotlarz.web.api.temperature.rest;

import com.goebl.simplify.Point;
import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.LatestTemperaturesResolver;
import com.kotlarz.service.temperature.TemperatureService;
import com.kotlarz.service.temperature.TemperatureSimplificator;
import com.kotlarz.web.api.temperature.dto.SingleSensorTemperatureLogDto;
import com.kotlarz.web.api.temperature.dto.TemperatureLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TemperatureSimplificator simplificator;

    @GetMapping( "latest" )
    public List<SingleSensorTemperatureLogDto> latest()
    {
        return latestTemperaturesResolver.getLatest().stream()
                        .map( SingleSensorTemperatureLogDto::from )
                        .collect( Collectors.toList() );
    }

    @GetMapping( "history/{sensorId}/between/{from}/{to}" )
    public List<TemperatureLogDto> laterThan( @PathVariable( "sensorId" ) Long sensorId,
                                              @PathVariable( "from" ) Long from, @PathVariable( "to" ) Long to )
    {
        return temperatureService.findBetween( sensorId, new Date( from ), new Date( to ) ).stream()
                        .map( TemperatureLogDto::from )
                        .collect( Collectors.toList() );
    }

    @GetMapping( "history/{sensorId}/between/{from}/{to}/simplified" )
    public Point[] laterThanSimplified( @PathVariable( "sensorId" ) Long sensorId,
                                        @PathVariable( "from" ) Long from, @PathVariable( "to" ) Long to,
                                        @RequestParam( name = "tolerance", defaultValue = "0.5", required = false ) Double tolerance )
    {
        List<TemperatureLog> values = temperatureService.findBetween( sensorId, new Date( from ), new Date( to ) );
        return simplificator.simplify( values, tolerance );
    }
}
