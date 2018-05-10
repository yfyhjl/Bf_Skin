package me.lxw.dtl.skin;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by din on 2017/11/8.
 * <p>
 * Email: godcok@163.com
 * 皮肤类型
 */
public enum SkinType {
    BG("View", "background") {
        @Override
        public void apply(View view, ResourceInfo info) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(SkinManager.getInstance().getDrawable(info.getName(), info
                        .getType()));
            } else {
                view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(info.getName
                        (), info.getType()));
            }
        }
    },
    SRC("ImageView", "src") {
        @Override
        public void apply(View view, ResourceInfo info) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(SkinManager.getInstance().getDrawable
                        (info.getName(), info.getType()));
            }
        }
    },
    COLOR("TextView", "textColor") {
        @Override
        public void apply(View view, ResourceInfo info) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(SkinManager.getInstance().getColorStateList
                        (info.getName(), info.getType()));
            }
        }
    };


    private String skinType;
    private String view;

    SkinType(String view, String skinType) {
        this.view = view;
        this.skinType = skinType;
    }

    public String getSkinType() {
        return skinType;
    }

    public String getView() {
        return view;
    }

    public abstract void apply(View view, ResourceInfo resName);

}