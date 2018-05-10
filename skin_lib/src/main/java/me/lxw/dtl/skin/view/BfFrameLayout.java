package me.lxw.dtl.skin.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import me.lxw.dtl.skin.SkinTypeSupport;

/**
 * Created by dingchenghao on 2017/12/8.
 * Email: dingchenghao@baofeng.com
 */
public class BfFrameLayout extends FrameLayout {
    public BfFrameLayout(Context context) {
        super(context);
    }

    public BfFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BfFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SkinTypeSupport.setSkinView(context, this, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BfFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SkinTypeSupport.setSkinView(context, this, attrs);
    }
}
