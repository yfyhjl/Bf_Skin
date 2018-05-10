package me.lxw.dtl.skin.view.delegate.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.lxw.dtl.skin.ResourceInfo;
import me.lxw.dtl.skin.SkinManager;
import me.lxw.dtl.skin.SkinType;
import me.lxw.dtl.skin.view.delegate.ISkinViewDelegate;

/**
 * 换肤View代理 Created by din on 2017/11/28.
 * <p>
 * Email: godcok@163.com
 */
public class SkinViewDelegate implements ISkinViewDelegate {
    private View mSkinEnableView;

    private Map<SkinType, ResourceInfo> mSkinAttrs = null;

    public SkinViewDelegate(View view) {
        mSkinEnableView = view;
        mSkinAttrs = new HashMap<>();
    }

    public SkinViewDelegate(View view, Map<SkinType, ResourceInfo> skinAttrs) {
        mSkinEnableView = view;
        if (skinAttrs == null) {
            mSkinAttrs = new HashMap<>();
        } else {
            mSkinAttrs = skinAttrs;
        }
    }

    @Override
    public void setBackground(int resId) {
        ResourceInfo info = SkinManager.getInstance().getResourceInfo(resId);
        mSkinAttrs.put(SkinType.BG, info);
        SkinType.BG.apply(mSkinEnableView, info);
    }

    @Override
    public void setImageResource(int resId) {
        if (mSkinEnableView instanceof ImageView) {
            ResourceInfo info = SkinManager.getInstance().getResourceInfo(resId);
            mSkinAttrs.put(SkinType.SRC, info);
            SkinType.SRC.apply(mSkinEnableView, info);
        }
    }

    @Override
    public void setTextColor(int resId) {
        if (mSkinEnableView instanceof TextView) {
            ResourceInfo info = SkinManager.getInstance().getResourceInfo(resId);
            mSkinAttrs.put(SkinType.COLOR, info);
            SkinType.COLOR.apply(mSkinEnableView, info);

        }
    }

    @Override
    public void remove(SkinType skinType) {
        mSkinAttrs.remove(skinType);
    }

    @Override
    public boolean self(View view) {
        return mSkinEnableView == view;
    }

    @Override
    public void apply() {
        for (Map.Entry<SkinType, ResourceInfo> entry : mSkinAttrs.entrySet()) {
            entry.getKey().apply(mSkinEnableView, entry.getValue());
        }
    }

}
