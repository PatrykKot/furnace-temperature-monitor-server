package com.kotlarz.web.api.temperature.dto;

import com.kotlarz.domain.TemperatureLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder( builderMethodName = "buildTemperatureLog" )
@AllArgsConstructor
public class TemperatureLogDto
{
    private Long id;

    private Date date;

    private Double value;

    public static TemperatureLogDto from( TemperatureLog temperatureLog )
    {
        return TemperatureLogDto.buildTemperatureLog()
                        .id( temperatureLog.getId() )
                        .date( temperatureLog.getDate() )
                        .value( temperatureLog.getValue() )
                        .build();
    }
}
