package com.kotlarz.web.api.temperature.uncompressed.stomp;

import com.kotlarz.service.temperature.TemperatureService;
import com.kotlarz.web.api.temperature.dto.NewTemperatureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller( "furnace/stomp" )
public class NewUncompressedTemperatureStompController
{
    @Autowired
    private TemperatureService temperatureService;

    @MessageMapping( "temperature" )
    public void reportNew( List<NewTemperatureDto> temperatureDtos )
    {
        temperatureService.report( temperatureDtos );
    }
}
