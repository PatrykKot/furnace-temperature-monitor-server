package com.kotlarz.service.temperature;

import com.goebl.simplify.Point;
import com.goebl.simplify.Simplify;
import com.kotlarz.domain.TemperatureLog;
import com.kotlarz.service.temperature.util.LogPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemperatureSimplificator
{
    private static final Boolean HIGH_QUALITY = true;

    public Point[] simplify( List<TemperatureLog> values, Double tolerance )
    {
        Simplify<Point> simplify = new Simplify<>( new LogPoint[0] );
        LogPoint[] points = values.stream()
                        .map( temperatureLog -> new LogPoint( temperatureLog.getDate().getTime(),
                                                              temperatureLog.getValue() ) )
                        .toArray( LogPoint[]::new );

        return simplify.simplify( points, tolerance, HIGH_QUALITY );
    }
}
