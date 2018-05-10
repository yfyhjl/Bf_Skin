package me.lxw.dtl.skin.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import me.lxw.dtl.skin.SkinTypeSupport;

/**
 * Created by dingchenghao on 2017/12/8.
 * Email: dingchenghao@baofeng.com
 */

public class BfRelativeLayout extends RelativeLayout {
    public BfRelativeLayout(Context context) {
        super(context);
    }

    public BfRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BfRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SkinTypeSupport.setSkinView(context, this, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BfRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SkinTypeSupport.setSkinView(context, this, attrs);
    }
}
