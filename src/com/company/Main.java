package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	    KolorRGB pierwszyRGB = new KolorRGB(255,255,0);
	    KolorRGB drugiRGB = new KolorRGB(255,255,255);

	    PorownanieJasnosci porownanieJasnosciRGB = new PorownanieJasnosci(pierwszyRGB,drugiRGB);
    }
}
