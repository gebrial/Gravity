package io.github.gebrial;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Gebri on 2017-02-23.
 */
public class ExtendedColour extends Color {
    /**
     * Assigns rgb values based on HSV.
     * Assumes S = V = 1.
     *
     * @param hue value from 0 to 360
     */
    public ExtendedColour(double hue) {
        this(hue, 1, 1);
    }

    /**
     * Assigns rgb values based on HSV.
     * http://www.had2know.com/technology/hsv-rgb-conversion-formula-calculator.html
     *
     * @param hue        value from 0 to 360
     * @param saturation value from 0 to 1
     * @param value      value from 0 to 1
     */
    public ExtendedColour(double hue, double saturation, double value) {
        super(0, 0, 0, 0);
        hue = Math.max(0, Math.min(360, hue)); // 0 <= hue <= 360
        saturation = Math.max(0, Math.min(1, saturation));
        value = Math.max(0, Math.min(1, value));

        double M = 255 * value;
        double m = M * (1 - saturation);
        double z = (M - m) * (1 - Math.abs((hue / 60) % 2 - 1));

        double r, g, b;
        if (hue < 60) {
            r = M;
            g = z + m;
            b = m;
        } else if (hue < 120) {
            r = z + m;
            g = M;
            b = m;
        } else if (hue < 180) {
            r = m;
            g = M;
            b = z + m;
        } else if (hue < 240) {
            r = m;
            g = z + m;
            b = M;
        } else if (hue < 300) {
            r = z + m;
            g = m;
            b = M;
        } else {
            r = M;
            g = m;
            b = z + m;
        }

        set((float) (r/255), (float) (g/255), (float) (b/255), 1);
    }
}
