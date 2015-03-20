package com.aidfarh.DisableHideNavigationBar;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class disableLightsOut implements IXposedHookLoadPackage {

    public void handleLoadPackage (final LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
            return;
            XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.phone.NavigationBarTransitions", lpparam.classLoader,
                "applyLightsOut", boolean.class, boolean.class, boolean.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            if (!Preferences.lights_out())
                                return;
                            param.args[0] = false;
                            param.args[1] = false;
                            param.args[2] = false;
                        }
                    });
    }
}