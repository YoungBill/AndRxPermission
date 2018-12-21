package baina.android.com.andrxpermissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import baina.android.com.andrxpermissions.setting.RuntimeSettingPage;
import baina.android.com.andrxpermissions.source.ContextSource;

public final class RxPermissionActivity extends Activity {

    private static final String KEY_INPUT_OPERATION = "KEY_INPUT_OPERATION";
    private static final int VALUE_INPUT_PERMISSION_SETTING = 2;

    private static RequestListener sRequestListener;

    /**
     * Request for setting.
     */
    public static void permissionSetting(Context context, RequestListener requestListener) {
        RxPermissionActivity.sRequestListener = requestListener;

        Intent intent = new Intent(context, RxPermissionActivity.class);
        intent.putExtra(KEY_INPUT_OPERATION, VALUE_INPUT_PERMISSION_SETTING);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int operation = intent.getIntExtra(KEY_INPUT_OPERATION, 0);
        switch (operation) {
            case VALUE_INPUT_PERMISSION_SETTING: {
                if (sRequestListener != null) {
                    RuntimeSettingPage setting = new RuntimeSettingPage(new ContextSource(this));
                    setting.start(VALUE_INPUT_PERMISSION_SETTING);
                } else {
                    finish();
                }
                break;
            }
            default: {
                throw new AssertionError("This should not be the case.");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (sRequestListener != null) {
            sRequestListener.onRequestCallback();
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (sRequestListener != null) {
            sRequestListener.onRequestCallback();
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        sRequestListener = null;
        super.finish();
    }

    /**
     * permission callback.
     */
    public interface RequestListener {
        void onRequestCallback();
    }
}