package com.example.tempconverter1;

import androidx.annotation.NonNull;
import java.time.temporal.ValueRange;
import java.util.Locale;

/**
 * Data class that represents a temperature value in fahrenheit or celsius.
 */
public class Temperature {
    private static final ValueRange RANGE_FAHRENHEIT = ValueRange.of(-130, 140);
    private static final ValueRange RANGE_CELSIUS = ValueRange.of(-90, 60);

    private Double value;
    private Unit unit;

    /**
     * Define temperature value and unit of this temperature.
     *
     * @param value a decimal.
     * @param unit celsius or fahrenheit.
     */
    public Temperature(double value, @NonNull Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Returns the predicted season given current temperature, or {@link Season#ERROR} if user
     * enters a temperature that does not fall within reasonable range.
     */
    @NonNull
    public Season getSeason() {
        if (unit == Unit.FAHRENHEIT) {
            if (!RANGE_FAHRENHEIT.isValidIntValue(value.intValue())) {
                return Season.ERROR;
            } else if (value >= 90) {
                return Season.SUMMER;
            } else if (value >= 70) {
                return Season.SPRING;
            } else if (value >= 50) {
                return Season.AUTUMN;
            }
            return Season.WINTER;
        }
        if (!RANGE_CELSIUS.isValidIntValue(value.intValue())) {
            return Season.ERROR;
        } else if (value >= 30) {
            return Season.SUMMER;
        } else if (value >= 20) {
            return Season.SPRING;
        } else if (value >= 10) {
            return Season.AUTUMN;
        }
        return Season.WINTER;
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
