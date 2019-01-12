package com.kotlarz.web.api.temperature.dto;

import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.web.api.temperature.sensor.dto.SensorDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode( callSuper = true )
public class SingleSensorTemperatureLogDto
                extends TemperatureLogDto
{
    private SensorDto sensor;

    @Builder( builderMethodName = "buildSingleSensorTemperatureLog" )
    public SingleSensorTemperatureLogDto( Long id, Date date, Double value, SensorDto sensor )
    {
        super( id, date, value );
        this.sensor = sensor;
    }

    public static SingleSensorTemperatureLogDto from( TemperatureLog domain )
    {
        return SingleSensorTemperatureLogDto.buildSingleSensorTemperatureLog()
                        .id( domain.getId() )
                        .date( domain.getDate() )
                        .value( domain.getValue() )
                        .sensor( SensorDto.from( domain.getSensor() ) )
                        .build();
    }
}
