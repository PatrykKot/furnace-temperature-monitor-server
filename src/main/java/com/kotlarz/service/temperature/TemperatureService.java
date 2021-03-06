package com.kotlarz.service.temperature;

import com.kotlarz.domain.Sensor;
import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.persistance.TemperatureLogRepository;
import com.kotlarz.service.temperature.reporter.UncompressedReporter;
import com.kotlarz.web.api.temperature.dto.NewTemperatureDto;
import com.kotlarz.web.api.temperature.live.TemperaturePublisher;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
public class TemperatureService
{
    @Autowired
    private TemperatureLogRepository repository;

    @Autowired
    private UncompressedReporter uncompressedReporter;

    @Autowired
    private TemperaturePublisher publisher;

    public void report( List<NewTemperatureDto> logs )
    {
        uncompressedReporter.report( logs );
        publisher.publishLatest();
    }

    public List<TemperatureLog> findBetween( Long sensorId, Date from, Date to )
    {
        return repository.findBetween( sensorId, from, to );
    }

    public Optional<TemperatureLog> findLatestForSensor( Sensor sensor )
    {
        List<TemperatureLog> results =
                        repository.findLatestForSensor( sensor.getId(), PageRequest.of( 0, 1 ) );
        if ( results.isEmpty() )
        {
            return Optional.empty();
        }
        else
        {
            return Optional.of( results.get( 0 ) );
        }
    }

    public void saveAll( List<TemperatureLog> logs )
    {
        repository.saveAll( logs );
    }
}
