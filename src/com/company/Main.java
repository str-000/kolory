package com.company;

public class Main {

    public static void main(String[] args) {
	    KolorRGB pierwszyRGB = new KolorRGB(1,255,255);
	    KolorRGB drugiRGB = new KolorRGB(0,255,0);

	    PorownanieJasnosci porownanieJasnosciRGB = new PorownanieJasnosci(pierwszyRGB,drugiRGB);
    }
}
