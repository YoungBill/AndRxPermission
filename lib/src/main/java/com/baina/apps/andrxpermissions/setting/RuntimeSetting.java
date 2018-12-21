/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baina.apps.andrxpermissions.setting;

import com.baina.apps.andrxpermissions.MainExecutor;
import com.baina.apps.andrxpermissions.RxPermissionActivity;
import com.baina.apps.andrxpermissions.source.Source;

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