package com.aidfarh.DisableHideNavigationBar;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import android.view.View;

public class disableHideNavigationBar implements IXposedHookLoadPackage {

    public void handleLoadPackage (final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("android"))
            return;
        XposedHelpers.findAndHookMethod("com.android.server.wm.WindowState", lpparam.classLoader, "getSystemUiVisibility",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (!Preferences.immersive() && !Preferences.lean_back())
                            return;
                        int vis = (int) param.getResult();
                        if (Preferences.immersive() &&
                                ((vis & View.SYSTEM_UI_FLAG_IMMERSIVE) != 0 ||
                                (vis & View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) != 0)) {
                            vis &= ~(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                            vis |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                        }
                        else
                        {
                            if (Preferences.lean_back() && (vis & View.SYSTEM_UI_FLAG_IMMERSIVE) == 0 &&
                                    (vis & View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == 0 &&
                                    (vis & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0)
                            {
                                vis &= ~(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                                vis |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                            }
                        }
                param.setResult(vis);
            }
        });
    }
}
