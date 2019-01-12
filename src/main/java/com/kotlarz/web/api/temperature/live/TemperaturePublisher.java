package com.kotlarz.web.api.temperature.live;

import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.LatestTemperaturesResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TemperaturePublisher
{
    private static final String DESTINATION = "/topic/temperature/live";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LatestTemperaturesResolver latestTemperaturesResolver;

    public void publishLatest()
    {
        send( latestTemperaturesResolver.getLatest() );
    }

    private void send( List<TemperatureLog> logs )
    {
        log.info( "Sending " + logs.size() + " logs to stomp clients" );
        messagingTemplate.convertAndSend( DESTINATION, logs );
    }
}
