package skin.smart.storm.com.skindemo;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.lxw.dtl.skin.SkinManager;

/**
 * Created by din on 17-12-20.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context);
        SkinManager.getInstance().addListener(getContext());
        setContentView(R.layout.dialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        SkinManager.getInstance().addListener(getContext());
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        SkinManager.getInstance().addListener(getContext());
    }


    @Override
    public void dismiss() {
        super.dismiss();

        SkinManager.getInstance().removeListener(getContext());
    }
}
