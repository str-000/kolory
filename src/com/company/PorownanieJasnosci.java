package com.company;

import java.awt.*;

public class PorownanieJasnosci {
    public PorownanieJasnosci(KolorRGB first, KolorRGB sec) {

        //-------------------------------------------
        float R1 = (float) (first.R*0.2126);
        float G1 = (float) (first.G*0.7152);
        float B1 = (float) (first.B*0.0722);
        //https://en.wikipedia.org/wiki/Relative_luminance (0.2126*R + 0.7152*G + 0.0722*B)
        float R2 = (float) (sec.R*0.2126);
        float G2 = (float) (sec.G*0.7152);
        float B2 = (float) (sec.B*0.0722);
        //-------------------------------------------

        //-------------------------------------------
        float first_brightness = R1 + G1 + B1;
        float sec_brightness = R2 + G2 + B2;
        //-------------------------------------------

        //-------------------------------------------
        Color firstColorRGB = new Color(first.R, first.G, first.B);
        Color secColorRGB = new Color(sec.R, sec.G, sec.B);
        //-------------------------------------------

        //-------------------------------------------
        if (first_brightness > sec_brightness) {
            System.out.println("Piewszy jaśniejszy\n");
            System.out.println(first_brightness - sec_brightness);
        } else if (first_brightness < sec_brightness) {
            System.out.println("Drugi jaśniejszy\n");
            System.out.println(sec_brightness - first_brightness);
        }
        else System.out.println("Są jednakowe\n");
        //-------------------------------------------

    }

    public PorownanieJasnosci(KolorCMYK first, KolorCMYK sec) {



    }
}
