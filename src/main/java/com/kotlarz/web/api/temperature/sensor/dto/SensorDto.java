package com.kotlarz.web.api.temperature.sensor.dto;

import com.kotlarz.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder( builderMethodName = "buildSensor" )
@AllArgsConstructor
public class SensorDto
{
    private Long id;

    private String name;

    private String address;

    private Boolean isAlive;

    private Double alarmValue;

    private Boolean initialized;

    public static SensorDto from( Sensor domain )
    {
        return SensorDto.buildSensor()
                        .id( domain.getId() )
                        .name( domain.getName() )
                        .address( domain.getAddress() )
                        .isAlive( domain.getIsAlive() )
                        .alarmValue( domain.getAlarmValue() )
                        .initialized( domain.getInitialized() )
                        .build();
    }
}
