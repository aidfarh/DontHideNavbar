package com.aidfarh.DisableHideNavigationBar;

import de.robv.android.xposed.XSharedPreferences;

public class Preferences {
//    This current package.
    private static final String PACKAGE_NAME = "com.aidfarh.DisableHideNavigationBar";

//    Our single instance of an XSharedPreferences.
    private static XSharedPreferences instance = null;

//    Load the preferences from our shared preference file.
    private static XSharedPreferences getInstance() {
        if (instance == null) {
            instance = new XSharedPreferences(PACKAGE_NAME);
            instance.makeWorldReadable();
        } else {
            instance.reload();
        }
        return instance;
    }

    public static boolean immersive() {
        return getInstance().getBoolean("pref_immersive", false);
    }

    public static boolean lean_back() {
        return getInstance().getBoolean("pref_lean_back", false);
    }

    public static boolean lights_out() {
        return getInstance().getBoolean("pref_lights_out", false);
    }
}