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
package com.tbruyelle.rxpermissions2.source;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by taochen on 18-12-21.
 * Mail：935612713@qq.com
 */
public class ContextSource extends Source {

    private Context mContext;

    public ContextSource(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            activity.startActivityForResult(intent, requestCode);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}
