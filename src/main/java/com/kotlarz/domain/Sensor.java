package com.kotlarz.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor
{
    private static final Integer DEFAULT_MAX_INACTIVE_TIME_SEC = 60;

    @Id
    @GeneratedValue
    private Long id;

    @Column( nullable = false )
    private String name;

    @Column( nullable = false, unique = true )
    private String address;

    private Boolean isAlive;

    private Double alarmValue;

    private Boolean initialized;

    @Column( nullable = false )
    private Date added;

    @Column( nullable = false )
    private Integer maxInactiveTimeSec;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<TemperatureLog> temperatureLogs;

    public static Sensor createNew( String address )
    {
        return Sensor.builder()
                        .address( address )
                        .name( address )
                        .isAlive( true )
                        .initialized( false )
                        .added( new Date() )
                        .maxInactiveTimeSec( DEFAULT_MAX_INACTIVE_TIME_SEC )
                        .build();
    }
}
