package com.company;

import java.awt.*;

public class KolorPrzeciwny {
    public KolorPrzeciwny(KolorRGB kolorRGB) {

        System.out.println("\nKolor przeciwny RGB\n");

        int invertR = 255 - kolorRGB.R;
        int invertG = 255 - kolorRGB.G;
        int invertB = 255 - kolorRGB.B;

        Color colorRGB = new Color(kolorRGB.R, kolorRGB.G, kolorRGB.B);
        Color invertColorRGB = new Color(invertR, invertG, invertB);

        System.out.println(colorRGB);
        System.out.println("Przeciwny: ");
        System.out.println(invertColorRGB);

    }

    public KolorPrzeciwny(KolorCMYK kolorCMYK) {

        System.out.println("\nKolor przeciwny CMYK\n");

        int R1 = 255 * (1 - kolorCMYK.C/100) * (1 - kolorCMYK.K/100);
        int G1 = 255 * (1 - kolorCMYK.M/100) * (1 - kolorCMYK.K/100);
        int B1 = 255 * (1 - kolorCMYK.Y/100) * (1 - kolorCMYK.K/100);

        int invertR = 255 - R1;
        int invertG = 255 - G1;
        int invertB = 255 - B1;

        Color colorCMYK = new Color(kolorCMYK.C, kolorCMYK.M, kolorCMYK.Y, kolorCMYK.K);
        Color invertColorRGB = new Color(invertR, invertG, invertB);

        int[] colorValues = rgbToCmyk(invertColorRGB.getRed(),invertColorRGB.getGreen(),invertColorRGB.getBlue());

        Color invertColorCMYK = new Color(colorValues[0], colorValues[1], colorValues[2], colorValues[3]);

        System.out.println(kolorCMYK.C + " " + kolorCMYK.M + " " + kolorCMYK.Y + " " + kolorCMYK.K);
        System.out.println("Przeciwny: ");
        System.out.println(colorValues[0] + " " + colorValues[1] + " " + colorValues[2] + " " + colorValues[3]);
    }

    private static int[] rgbToCmyk(int r, int g, int b) {
        double percentageR = r / 255.0 * 100;
        double percentageG = g / 255.0 * 100;
        double percentageB = b / 255.0 * 100;

        double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);

        if (k == 100) {
            return new int[]{ 0, 0, 0, 100 };
        }

        int c = (int)((100 - percentageR - k) / (100 - k) * 100);
        int m = (int)((100 - percentageG - k) / (100 - k) * 100);
        int y = (int)((100 - percentageB - k) / (100 - k) * 100);

        return new int[]{ c, m, y, (int)k };
    }
}
