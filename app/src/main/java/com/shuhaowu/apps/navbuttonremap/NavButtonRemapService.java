package com.shuhaowu.apps.navbuttonremap;

import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

public class NavButtonRemapService extends AccessibilityService {
    private static final String TAG = "NavButtonRemapService";
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

        String backButtonActionPrefKey;
        String switchAppButtonActionPrefKey;

        int rotation = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_90:
                // button on the right.
                backButtonActionPrefKey = "back_button_action_buttons_right";
                switchAppButtonActionPrefKey = "switch_app_button_action_buttons_right";
                Log.d(TAG, "Landscape 90 - buttons on the right");
            break;
            case Surface.ROTATION_270:
                // buttons on the left.
                backButtonActionPrefKey = "back_button_action_buttons_left";
                switchAppButtonActionPrefKey = "switch_app_button_action_buttons_left";
                Log.d(TAG, "Landscape 270 - buttons on the left");
            break;
            default:
                backButtonActionPrefKey = "back_button_action_portrait";
                switchAppButtonActionPrefKey = "switch_app_button_action_portrait";
        }

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
    }
}
