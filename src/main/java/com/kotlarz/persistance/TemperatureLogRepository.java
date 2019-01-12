package com.kotlarz.persistance;

import com.kotlarz.domain.TemperatureLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TemperatureLogRepository
                extends JpaRepository<TemperatureLog, Long>
{
    @Query( "select u from TemperatureLog u where u.date > ?1" )
    List<TemperatureLog> findLaterThan( Date date );

    @Query( "select u from TemperatureLog u where u.date <= ?1" )
    List<TemperatureLog> findEarlierThan( Date date );

    @Query( "select u from TemperatureLog u where u.sensor.id = ?1 order by u.date DESC" )
    List<TemperatureLog> findLatestForSensor( Long sensorId, Pageable pageable );
}
