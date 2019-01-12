package com.kotlarz.web.api.temperature.uncompressed.rest;

import com.kotlarz.service.temperature.TemperatureService;
import com.kotlarz.web.api.temperature.dto.NewTemperatureDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping( "temperatures" )
public class UncompressedTemperatureController
{
    @Autowired
    private TemperatureService temperatureService;

    @PostMapping( "uncompressed" )
    public void report( @RequestBody List<NewTemperatureDto> temperatures )
    {
        temperatureService.report( temperatures );
    }
}
