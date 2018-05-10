package me.lxw.dtl.skin.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;

import me.lxw.dtl.skin.SkinTypeSupport;

/**
 * Created by dingchenghao on 2017/12/8.
 * Email: dingchenghao@baofeng.com
 */

public class BfButton extends Button {
    public BfButton(Context context) {
        super(context);
    }

    public BfButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BfButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SkinTypeSupport.setSkinView(context, this, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BfButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SkinTypeSupport.setSkinView(context, this, attrs);
    }


}
