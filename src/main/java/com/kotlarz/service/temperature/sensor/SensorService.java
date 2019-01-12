package com.kotlarz.service.temperature.sensor;

import com.kotlarz.domain.Sensor;
import com.kotlarz.persistance.SensorRepository;
import com.kotlarz.web.api.temperature.sensor.dto.SensorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
public class SensorService
{
    @Autowired
    private SensorRepository repository;

    public Optional<Sensor> getByAddress( String address )
    {
        return repository.findByAddress( address );
    }

    public List<Sensor> getAll()
    {
        return repository.findAll();
    }

    public void change( SensorDto dto )
    {
        Assert.notNull( dto.getId(), "Sensor id cannot be null" );

        log.info( "Updating sensor with id " + dto.getId() );
        Sensor sensor = repository.getOne( dto.getId() );
        sensor.setName( dto.getName() );
        sensor.setAddress( dto.getAddress() );
        sensor.setIsAlive( dto.getIsAlive() );
        sensor.setAlarmValue( dto.getAlarmValue() );
        sensor.setInitialized( dto.getInitialized() );
    }

    public void initialize( Long sensorId )
    {
        log.info( "Initializing sensor with id " + sensorId );
        repository.getOne( sensorId ).setInitialized( true );
    }

    public void reset( Long sensorId )
    {
        log.info( "Resetting sensor with id " + sensorId );
        Sensor sensor = repository.getOne( sensorId );
        sensor.setInitialized( false );
        sensor.setName( sensor.getAddress() );
        sensor.setAlarmValue( null );
    }
}
