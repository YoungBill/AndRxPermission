package baina.android.com.andrxpermissions;

import android.os.Handler;
import android.os.Looper;

public class MainExecutor {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public MainExecutor() {
    }

    public void post(Runnable r) {
        HANDLER.post(r);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        HANDLER.postDelayed(r, delayMillis);
    }
}