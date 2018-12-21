package baina.android.com.andrxpermissions.setting;

import baina.android.com.andrxpermissions.MainExecutor;
import baina.android.com.andrxpermissions.RxPermissionActivity;
import baina.android.com.andrxpermissions.source.Source;

/**
 * Created by taochen on 18-12-21.
 * Mail：935612713@qq.com
 */
public class RuntimeSetting implements Setting, RxPermissionActivity.RequestListener {

    private static final MainExecutor EXECUTOR = new MainExecutor();

    private Source mSource;
    private Setting.Action mComeback;

    public RuntimeSetting(Source source) {
        this.mSource = source;
    }

    @Override
    public Setting onComeback(Setting.Action comeback) {
        this.mComeback = comeback;
        return this;
    }

    @Override
    public void start() {
        RxPermissionActivity.permissionSetting(mSource.getContext(), this);
    }

    @Override
    public void onRequestCallback() {
        EXECUTOR.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mComeback != null) {
                    mComeback.onAction();
                }
            }
        }, 100);
    }
}