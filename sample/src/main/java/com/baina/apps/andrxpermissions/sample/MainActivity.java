package com.baina.apps.andrxpermissions.sample;

import android.Manifest.permission;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import java.io.IOException;

import baina.android.com.andrxpermissions.Permission;
import baina.android.com.andrxpermissions.AndRxPermissions;
import baina.android.com.andrxpermissions.setting.RuntimeSetting;
import baina.android.com.andrxpermissions.setting.Setting;
import baina.android.com.andrxpermissions.source.ContextSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AndRxPermissionsSample";

    private Camera camera;
    private SurfaceView surfaceView;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndRxPermissions andRxPermissions = new AndRxPermissions(this);
        andRxPermissions.setLogging(true);

        setContentView(R.layout.act_main);
        surfaceView = findViewById(R.id.surfaceView);

        disposable = RxView.clicks(findViewById(R.id.enableCamera))
                // Ask for permissions when button is clicked
                .compose(andRxPermissions.ensureEach(permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) {
                                   Log.i(TAG, "Permission result " + permission);
                                   if (permission.granted) {
                                       releaseCamera();
                                       camera = Camera.open(0);
                                       try {
                                           camera.setPreviewDisplay(surfaceView.getHolder());
                                           camera.startPreview();
                                       } catch (IOException e) {
                                           Log.e(TAG, "Error while trying to display the camera preview", e);
                                       }
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // Denied permission without ask never again
                                       Toast.makeText(MainActivity.this,
                                               "Denied permission without ask never again",
                                               Toast.LENGTH_SHORT).show();
                                   } else {
                                       // Denied permission with ask never again
                                       // Need to go to the settings
                                       Toast.makeText(MainActivity.this,
                                               "Permission denied, can't enable the camera",
                                               Toast.LENGTH_SHORT).show();
                                       new RuntimeSetting(new ContextSource(MainActivity.this)).onComeback(new Setting.Action() {
                                           @Override
                                           public void onAction() {
                                               Toast.makeText(MainActivity.this,
                                                       "setting, onComeback",
                                                       Toast.LENGTH_SHORT).show();
                                           }
                                       }).start();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable t) {
                                Log.e(TAG, "onError", t);
                            }
                        },
                        new Action() {
                            @Override
                            public void run() {
                                Log.i(TAG, "OnComplete");
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}