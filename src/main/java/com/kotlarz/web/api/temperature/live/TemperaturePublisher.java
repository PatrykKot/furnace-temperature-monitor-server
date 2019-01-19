package com.kotlarz.web.api.temperature.live;

import com.kotlarz.service.temperature.LatestTemperaturesResolver;
import com.kotlarz.web.api.temperature.dto.SingleSensorTemperatureLogDto;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    private Boolean reported = false;

    private List<SingleSensorTemperatureLogDto> lastSent = new LinkedList<>();

    @Synchronized
    public void publishLatest()
    {
        reported = true;
    }

    @Synchronized
    public void forcePublishLatest()
    {
        try
        {
            sendLatestIfNeeded();
        }
        finally
        {
            reported = false;
        }
    }

    @Synchronized
    @Scheduled( fixedRate = 1000 )
    public void queueSend()
    {
        if ( reported )
        {
            try
            {
                sendLatestIfNeeded();
            }
            finally
            {
                reported = false;
            }
        }
    }

    private void sendLatestIfNeeded()
    {
        List<SingleSensorTemperatureLogDto> toSend = latestTemperaturesResolver.getLatest().stream()
                        .map( SingleSensorTemperatureLogDto::from )
                        .collect( Collectors.toList() );
        if ( !lastSent.equals( toSend ) )
        {
            send( toSend );
            lastSent = toSend;
        }
    }

    private void send( List<SingleSensorTemperatureLogDto> logs )
    {
        log.info( "Sending " + logs.size() + " logs to stomp clients" );
        messagingTemplate.convertAndSend( DESTINATION, logs );
    }
}
