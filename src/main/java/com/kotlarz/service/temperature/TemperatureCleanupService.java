package com.kotlarz.service.temperature;

import com.kotlarz.persistance.TemperatureLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TemperatureCleanupService
{
    public static final Integer CLEAR_OLDER_THAN_DAYS = 30;

    private final TemperatureLogRepository temperatureLogRepository;

    @Autowired
    public TemperatureCleanupService( TemperatureLogRepository temperatureLogRepository )
    {
        this.temperatureLogRepository = temperatureLogRepository;
    }

    // TODO refactor or delete
    //
    //    @Transactional
    //    @Scheduled( fixedDelay = 3600 * 1000 )
    //    public void cleanup()
    //    {
    //        log.info( "Cleaning temperature logs" );
    //
    //        DateTime now = DateTime.now();
    //        DateTime monthBefore = now.minusDays( CLEAR_OLDER_THAN_DAYS );
    //        List<TemperatureLog> toDelete = temperatureLogRepository.findEarlierThan( new Date( monthBefore.getMillis() ) );
    //        log.info( "Found " + toDelete.size() + " logs to delete" );
    //
    //        toDelete.forEach( log -> temperatureLogRepository.delete( log ) );
    //    }
}
