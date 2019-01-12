package com.kotlarz.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor
{
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

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<TemperatureLog> temperatureLogs;

    public static Sensor createNew( String address )
    {
        return Sensor.builder()
                        .address( address )
                        .name( address )
                        .isAlive( true )
                        .initialized( false )
                        .build();
    }
}
