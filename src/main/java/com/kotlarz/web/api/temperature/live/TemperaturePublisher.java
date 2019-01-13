package com.kotlarz.web.api.temperature.live;

import com.kotlarz.service.temperature.LatestTemperaturesResolver;
import com.kotlarz.web.api.temperature.dto.SingleSensorTemperatureLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TemperaturePublisher
{
    private static final String DESTINATION = "/topic/temperature/latest";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LatestTemperaturesResolver latestTemperaturesResolver;

    public void publishLatest()
    {
        send( latestTemperaturesResolver.getLatest().stream()
                              .map( SingleSensorTemperatureLogDto::from )
                              .collect( Collectors.toList() ) );
    }

    private void send( List<SingleSensorTemperatureLogDto> logs )
    {
        log.info( "Sending " + logs.size() + " logs to stomp clients" );
        messagingTemplate.convertAndSend( DESTINATION, logs );
    }
}
