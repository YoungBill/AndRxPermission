package baina.android.com.andrxpermissions.source;

import android.content.Context;
import android.content.Intent;

/**
 * Created by taochen on 18-12-21.
 * Mail：935612713@qq.com
 */
public abstract class Source {

    public abstract Context getContext();

    public abstract void startActivityForResult(Intent intent, int requestCode);

}