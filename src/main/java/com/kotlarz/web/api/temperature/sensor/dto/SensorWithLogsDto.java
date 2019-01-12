package com.kotlarz.web.api.temperature.sensor.dto;

import com.kotlarz.domain.Sensor;
import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.web.api.temperature.dto.TemperatureLogDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode( callSuper = true )
public class SensorWithLogsDto
                extends SensorDto
{
    private List<TemperatureLogDto> logs;

    @Builder( builderMethodName = "buildSensorWithLogs" )
    public SensorWithLogsDto( Long id, String name, String address, Boolean isAlive, Double alarmValue,
                              Boolean initialized, List<TemperatureLogDto> logs )
    {
        super( id, name, address, isAlive, alarmValue, initialized );
        this.logs = logs;
    }

    public static SensorWithLogsDto from( Sensor sensor )
    {
        return SensorWithLogsDto.buildSensorWithLogs()
                        .id( sensor.getId() )
                        .name( sensor.getName() )
                        .address( sensor.getAddress() )
                        .isAlive( sensor.getIsAlive() )
                        .alarmValue( sensor.getAlarmValue() )
                        .initialized( sensor.getInitialized() )
                        .build();
    }

    public static List<SensorWithLogsDto> from( List<TemperatureLog> logs )
    {
        return logs.stream()
                        .map( TemperatureLog::getSensor )
                        .distinct()
                        .map( domain -> {
                            SensorWithLogsDto dto = SensorWithLogsDto.from( domain );
                            dto.setLogs( logs.stream()
                                                         .filter( temperatureLog -> temperatureLog.getSensor()
                                                                         .getId().equals( dto.getId() ) )
                                                         .map( TemperatureLogDto::from )
                                                         .collect( Collectors.toList() ) );
                            return dto;
                        } )
                        .collect( Collectors.toList() );
    }
}
