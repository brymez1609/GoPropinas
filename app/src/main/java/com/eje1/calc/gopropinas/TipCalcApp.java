package com.eje1.calc.gopropinas;

import android.app.Application;

/**
 * Created by Bryan G on 17/03/2017.
 */
public class TipCalcApp extends Application {
    private final static String ABOUT_URL = "https://www.edx.org";

    public String getAboutUrl(){
        return ABOUT_URL;
    }
}

