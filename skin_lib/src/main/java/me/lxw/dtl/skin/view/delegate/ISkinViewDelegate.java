
package me.lxw.dtl.skin.view.delegate;

import android.view.View;

import me.lxw.dtl.skin.SkinType;

/**
 * Created by din on 2017/11/8.
 * <p>
 * Email: godcok@163.com
 */
public interface ISkinViewDelegate {
    /**
     * 设置背景
     *
     * @param resId
     */
    void setBackground(int resId);

    /**
     * 设置图片
     *
     * @param resId
     */
    void setImageResource(int resId);

    /**
     * 设置文字颜色
     *
     * @param resId
     */
    void setTextColor(int resId);

    /**
     * 是否是自己
     *
     * @param view
     * @return
     */
    boolean self(View view);

    /**
     * 移除皮肤属性
     * 
     * @param skinType
     */
    void remove(SkinType skinType);

    /**
     * 使皮肤生效
     */
    void apply();

}
