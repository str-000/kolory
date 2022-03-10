package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class PorownanieJasnosci {

    Color red = new Color(255, 0, 0);       String redString = "czerwony";
    Color green = new Color(0, 255, 0);     String greenString = "zielony";
    Color blue = new Color(0, 0, 255);      String blueString = "niebieski";
    Color yellow = new Color(255, 255, 0);  String yellowString = "źółty";
    Color purple = new Color(255, 0, 255);  String purpleString = "purpurowy";
    Color grey = new Color(128, 128, 128);  String greyString = "szary";
    Color black = new Color(0, 0, 0);       String blackString = "czarny";
    Color white = new Color(255, 255, 255); String whiteString = "biały";

    //tablica obiektow Color
    Color[] colorList = {red, green, blue, yellow, purple, grey, black, white};
    //tablica zawierajaca nazwy kolorow
    String[] colorListNames = {redString, greenString, blueString, yellowString, purpleString, greyString, blackString, whiteString};

    // ============================================================================

    public PorownanieJasnosci(KolorRGB first, KolorRGB sec) throws IOException {
        System.out.println("\nPorownanie jasnosci RGB\n");

        // konwersja wszystkich wartości RGB na dziesiętne
        // https://en.wikipedia.org/wiki/SRGB
        float vR1 = convertToDecimal(first.R);
        float vG1 = convertToDecimal(first.G);
        float vB1 = convertToDecimal(first.B);

        float vR2 = convertToDecimal(sec.R);
        float vG2 = convertToDecimal(sec.G);
        float vB2 = convertToDecimal(sec.B);

        // WZGLĘDNE obliczenie luminancji koloru
        // jasność jest atrybutem percepcyjnym, nie ma „fizycznej” miary
        // https://en.wikipedia.org/wiki/Relative_luminance
        float Y1 = (float) (0.2126 * sRGBtoLin(vR1) + 0.7152 * sRGBtoLin(vG1) + 0.0722 * sRGBtoLin(vB1));
        float Y2 = (float) (0.2126 * sRGBtoLin(vR2) + 0.7152 * sRGBtoLin(vG2) + 0.0722 * sRGBtoLin(vB2));

        // -------------------------------------------
        Color firstColorRGB = new Color(first.R, first.G, first.B);
        Color secColorRGB = new Color(sec.R, sec.G, sec.B);

        String firstColorName = colorName(colorList,firstColorRGB,colorListNames);
        String secColorName = colorName(colorList,secColorRGB,colorListNames);

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

    // ============================================================================
    // ============================================================================

    public PorownanieJasnosci(KolorCMYK first, KolorCMYK sec) {
        System.out.println("\nPorownanie jasnosci CMYK\n");

        //konwersja CYMK na RGB w celu obliczenia luminancji koloru
        int R1 = 255 * (1 - first.C/100) * (1 - first.K/100);
        int G1 = 255 * (1 - first.M/100) * (1 - first.K/100);
        int B1 = 255 * (1 - first.Y/100) * (1 - first.K/100);

        int R2 = 255 * (1 - sec.C/100) * (1 - sec.K/100);
        int G2 = 255 * (1 - sec.M/100) * (1 - sec.K/100);
        int B2 = 255 * (1 - sec.Y/100) * (1 - sec.K/100);

        //analogiczna konwersja wszystkich wartości RGB na dziesiętne
        float vR1 = convertToDecimal((short) R1);
        float vG1 = convertToDecimal((short) G1);
        float vB1 = convertToDecimal((short) B1);

        float vR2 = convertToDecimal((short) R2);
        float vG2 = convertToDecimal((short) G2);
        float vB2 = convertToDecimal((short) B2);

        // WZGLĘDNE obliczenie luminancji koloru
        float Y1 = (float) (0.2126 * sRGBtoLin(vR1) + 0.7152 * sRGBtoLin(vG1) + 0.0722 * sRGBtoLin(vB1));
        float Y2 = (float) (0.2126 * sRGBtoLin(vR2) + 0.7152 * sRGBtoLin(vG2) + 0.0722 * sRGBtoLin(vB2));

        // -------------------------------------------
        Color firstColorRGB = new Color(R1, G1, B1);
        Color secColorRGB = new Color(R2, G2, B2);

        String firstColorName = colorName(colorList,firstColorRGB,colorListNames);
        String secColorName = colorName(colorList,secColorRGB,colorListNames);

        // -------------------------------------------
        System.out.printf("luminancja pierwszego koloru: [ %s ] [ %f ] luminancja drugiego koloru: [ %s ] [ %f ]\n", firstColorName, Y1, secColorName, Y2);

        if (Y1 > Y2) System.out.printf("jaśniejszy jest kolor [ %s ] pierwszy. róźnica wynosi: [ %f ]\n", firstColorName, Y1 - Y2);
        else if (Y1 < Y2) System.out.printf("jaśniejszy jest kolor [ %s ] drugi. róźnica wynosi: [ %f ] \n", secColorName, Y2 - Y1);
        else System.out.print("oba kolory są tak samo jasne\n");

    }

    // ============================================================================
    // ============================================================================

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
}
