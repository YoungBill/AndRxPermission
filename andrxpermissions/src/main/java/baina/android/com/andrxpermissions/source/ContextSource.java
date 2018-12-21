package baina.android.com.andrxpermissions.source;

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
