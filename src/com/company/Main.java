package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

		KolorRGB pierwszyRGB = new KolorRGB(0,0,255);
	    KolorRGB drugiRGB = new KolorRGB(255,255,255);

		KolorCMYK pierwszyCMYK = new KolorCMYK(0,100,100,0);
		KolorCMYK drugiCMYK = new KolorCMYK(0,0,0,0);

	    PorownanieJasnosci porownanieJasnosciRGB = new PorownanieJasnosci(pierwszyRGB,drugiRGB);
		PorownanieJasnosci porownanieJasnosciCMYK = new PorownanieJasnosci(pierwszyCMYK,drugiCMYK);

		KolorPrzeciwny kolorPrzeciwnyRGB = new KolorPrzeciwny(pierwszyRGB);
		KolorPrzeciwny kolorPrzeciwnyCMYK = new KolorPrzeciwny(pierwszyCMYK);
    }
}











