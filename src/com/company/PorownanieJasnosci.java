package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PorownanieJasnosci {
    public PorownanieJasnosci(KolorRGB first, KolorRGB sec) throws IOException {

        // -------------------------------------------
        // konwersja wszystkich wartości RGB na dziesiętne
        // https://en.wikipedia.org/wiki/SRGB
        float vR1 = convertToDecimal(first.R);
        float vG1 = convertToDecimal(first.G);
        float vB1 = convertToDecimal(first.B);

        float vR2 = convertToDecimal(sec.R);
        float vG2 = convertToDecimal(sec.G);
        float vB2 = convertToDecimal(sec.B);

        // -------------------------------------------
        // WZGLĘDNE obliczenie luminancji koloru
        // jasność jest atrybutem percepcyjnym, nie ma „fizycznej” miary
        // https://en.wikipedia.org/wiki/Relative_luminance
        float Y1 = (float) (0.2126 * sRGBtoLin(vR1) + 0.7152 * sRGBtoLin(vG1) + 0.0722 * sRGBtoLin(vB1));
        float Y2 = (float) (0.2126 * sRGBtoLin(vR2) + 0.7152 * sRGBtoLin(vG2) + 0.0722 * sRGBtoLin(vB2));

        // -------------------------------------------
        Color firstColorRGB = new Color(first.R, first.G, first.B);
        Color secColorRGB = new Color(sec.R, sec.G, sec.B);

        Color red = new Color(255, 0, 0);       String redString = "czerwony";
        Color green = new Color(0, 255, 0);     String greenString = "zielony";
        Color blue = new Color(0, 0, 255);      String blueString = "niebieski";
        Color yellow = new Color(255, 255, 0);  String yellowString = "źółty";
        Color purple = new Color(255, 0, 255);  String purpleString = "purpurowy";
        Color grey = new Color(128, 128, 128);  String greyString = "szary";
        Color black = new Color(0, 0, 0);       String blackString = "czarny";
        Color white = new Color(255, 255, 255); String whiteString = "biały";


        Color[] colorList = {red, green, blue, yellow, purple, grey, black, white};
        String[] colorListNames = {redString, greenString, blueString, yellowString, purpleString, greyString, blackString, whiteString};

        String firstColorName = colorName(colorList,firstColorRGB,colorListNames);
        String secColorName = colorName(colorList,secColorRGB,colorListNames);

        /*Integer[] helpingArray = new Integer[colorList.length];
        for (int i = 0; i < colorList.length; i++) {
            int rgbDistance = Math.abs(firstColorRGB.getRed() - colorList[i].getRed()
                    + Math.abs(firstColorRGB.getGreen() - colorList[i].getGreen())
                    + Math.abs(firstColorRGB.getBlue() - colorList[i].getBlue()));
            helpingArray[i] = rgbDistance;
        }

        int smallestOfHelpingArray = java.util.Arrays.asList(helpingArray).indexOf(min(helpingArray));
        String firstColorName = String.valueOf(colorListNames[smallestOfHelpingArray]);*/

        // -------------------------------------------
        System.out.printf("luminancja pierwszego koloru: [ %s ] [ %f ] luminancja drugiego koloru: [ %s ] [ %f ]\n", firstColorName, Y1, secColorName, Y2);

        if (Y1 > Y2) System.out.printf("jaśniejszy jest kolor [ %s ] pierwszy. róźnica wynosi: [ %f ]\n", firstColorName, Y1 - Y2);
        else if (Y1 < Y2) System.out.printf("jaśniejszy jest kolor [ %s ] drugi. róźnica wynosi: [ %f ] \n", secColorName, Y2 - Y1);
        else System.out.print("oba kolory są tak samo jasne\n");

        // -------------------------------------------
        BufferedImage img1 = colorImage(ImageIO.read(new File("img/blank.png")), first.R, first.G, first.B);
        ImageIO.write(img1, "png", new File("img/firstImgColor.png"));

        BufferedImage img2 = colorImage(ImageIO.read(new File("img/blank.png")), sec.R, sec.G, sec.B);
        ImageIO.write(img2, "png", new File("img/secImgColor.png"));

    }

    public PorownanieJasnosci(KolorCMYK first, KolorCMYK sec) {



    }

    // -------------------------------------------
    // METODY
    // -------------------------------------------
    private float convertToDecimal(short rgbComponent) {
        return (float) rgbComponent / 255;
    }

    // -------------------------------------------
    private float sRGBtoLin(float colorChannel) {
        // przyjmuje wartość dziesiętną koloru zakodowaną w formacie gamma sRGB z zakresu od 0.0 do 1.0
        // zwraca wartość linearyzowaną
        // https://en.wikipedia.org/wiki/Luminance

        if ( colorChannel <= 0.04045 ) {
            return (float) (colorChannel / 12.92);
        } else {
            return (float) Math.pow((( colorChannel + 0.055)/1.055),2.4);
        }
    }

    // -------------------------------------------
    private static BufferedImage colorImage(BufferedImage image, short R, short G, short B) {
        // ustawia kolor zdjęcia na podaną w argumentach wartość RGB
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = R;
                pixels[1] = G;
                pixels[2] = B;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }

    public int min(Integer[] array) {
        int min = array[0];

        for(int i=0; i<array.length; i++ ) {
            if(array[i]<min) {
                min = array[i];
            }
        }
        return min;
    }

    public String colorName(Color[] colorList, Color firstColorRGB, String[] colorListNames) {
        String colorName = null;
        for (int i = 0; i < colorList.length; i++) {
            if ((firstColorRGB.getRed() == colorList[i].getRed()) &&
                    (firstColorRGB.getGreen() == colorList[i].getGreen()) &&
                    (firstColorRGB.getBlue() == colorList[i].getBlue())) {
                colorName = colorListNames[i];
                break;
            }
        }
        return colorName;
    }

}
