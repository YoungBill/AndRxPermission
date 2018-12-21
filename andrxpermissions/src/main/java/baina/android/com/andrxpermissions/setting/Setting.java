package baina.android.com.andrxpermissions.setting;

/**
 * Created by taochen on 18-12-21.
 * Mail：935612713@qq.com
 */
public interface Setting {
    /**
     * The action when the user comebacks.
     */
    Setting onComeback(Setting.Action comeback);

    /**
     * SettingPage setting.
     */
    void start();

    /**
     * An action.
     */
    interface Action {
        void onAction();
    }
}