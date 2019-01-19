package com.kotlarz.service.temperature.util;

import com.goebl.simplify.Point;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogPoint
                implements Point
{
    private Long time;

    private Double value;

    @Override
    public double getX()
    {
        return time;
    }

    @Override
    public double getY()
    {
        return value;
    }
}
