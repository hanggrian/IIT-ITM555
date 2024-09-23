package com.example.tempconverter2;

import androidx.annotation.NonNull;
import java.util.Locale;

/**
 * Data class that represents a temperature value in fahrenheit or celsius.
 */
public class Temperature {
    private double value;
    private Unit unit;

    /**
     * Define temperature value and unit of this temperature.
     *
     * @param value a decimal.
     * @param unit  celsius or fahrenheit.
     */
    public Temperature(double value, @NonNull Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Toggles temperature unit, thereby changing its value.
     */
    public void convert() {
        if (unit == Unit.FAHRENHEIT) {
            value = (value - 32) * 5 / 9;
            unit = Unit.CELSIUS;
            return;
        }
        value = value * 9 / 5 + 32;
        unit = Unit.FAHRENHEIT;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "%3.1f \u00B0%s", value, unit.toString());
    }

    public enum Unit {
        FAHRENHEIT {
            @NonNull
            @Override
            public String toString() {
                return "F";
            }
        },
        CELSIUS {
            @NonNull
            @Override
            public String toString() {
                return "C";
            }
        },
    }
}
