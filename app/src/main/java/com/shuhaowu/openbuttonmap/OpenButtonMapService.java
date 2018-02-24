package com.shuhaowu.openbuttonmap;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class OpenButtonMapService extends AccessibilityService {
    private static final String TAG = "OpenButtonMapService";
    private static final String BACK = "0";
    private static final String SWITCH_APP = "1";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP || event.isCanceled()) {
            return false;
        }

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String backButtonActionPrefKey = "back_button_action_portrait";
        String switchAppButtonActionPrefKey = "switch_app_button_action_portrait";

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (pref.getString(backButtonActionPrefKey, BACK).equals(SWITCH_APP)) {
                    performGlobalAction(GLOBAL_ACTION_RECENTS);
                    return true;
                }
            break;
            case KeyEvent.KEYCODE_APP_SWITCH:
                if (pref.getString(switchAppButtonActionPrefKey, SWITCH_APP).equals(BACK)) {
                    performGlobalAction(GLOBAL_ACTION_BACK);
                    return true;
                }
            break;
        }

        return false;
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }
}
