package me.lxw.dtl.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.lxw.dtl.skin.view.delegate.ISkinViewDelegate;
import me.lxw.dtl.skin.view.delegate.impl.SkinViewDelegate;

/**
 * Created by din on 2017/11/8.
 * <p>
 * Email: godcok@163.com 皮肤管理
 */
public class SkinManager {
    /**
     * 页面的所有皮肤View
     */
    private Map<Context, List<ISkinViewDelegate>> mSkinViewsMap = new HashMap<>();

    /**
     * 要通知的页面
     */
    private List<Context> mListSkinChanged = new ArrayList<>();
    private Context context;

    private SkinManager() {

    }

    public static SkinManager getInstance() {
        return I.sInstance;
    }

    /**
     * 设置图片
     *
     * @param view
     * @param resId
     */
    public static void setImageResource(View view, @DrawableRes int resId) {
        if (view == null) {
            return;
        }
        ISkinViewDelegate delegate = getInstance().getSkinView(view);
        delegate.setImageResource(resId);
    }

    /**
     * 设置文字颜色
     *
     * @param view
     * @param resId
     */
    public static void setTextColor(View view, @ColorRes int resId) {
        if (view == null) {
            return;
        }
        ISkinViewDelegate delegate = getInstance().getSkinView(view);
        delegate.setTextColor(resId);
    }

    /**
     * 设置背景
     *
     * @param view
     * @param resId
     */
    public static void setBackground(View view, @DrawableRes int resId) {
        if (view == null) {
            return;
        }
        ISkinViewDelegate delegate = getInstance().getSkinView(view);
        delegate.setBackground(resId);
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Context not be null");
        }
        this.context = context.getApplicationContext();
        ResourceManager.getInstance().init(context);
    }

    /**
     * 更新所有皮肤View
     *
     * @param context
     */
    public void apply(Context context) {
        List<ISkinViewDelegate> skinViews = mSkinViewsMap.get(context);
        if (skinViews == null) {
            return;
        }
        for (ISkinViewDelegate skinView : skinViews) {
            if (skinView == null)
                continue;
            skinView.apply();
        }
    }

    /**
     * 加载皮肤
     * 根据顺序优先加载
     *
     * @param paths
     */
    public void loadSkin(String... paths) {
        boolean isSuccess = ResourceManager.getInstance().loadSkins(paths);
        if (isSuccess) {
            notifySkinChanged();
        }
    }

    /**
     * 找到View自己的代理，没有的话创建一个
     *
     * @param view
     * @return
     */
    public ISkinViewDelegate getSkinView(View view) {
        if (view == null || view.getContext() == null) {
            Log.d("SkinManager", "view == null or not view.getContext()");
            return null;
        }
        Context context = view.getContext();
        List<ISkinViewDelegate> skinViewDelegates = mSkinViewsMap.get(context);
        if (skinViewDelegates == null) {
            skinViewDelegates = new ArrayList<>();
            mSkinViewsMap.put(context, skinViewDelegates);
        }
        for (ISkinViewDelegate skinViewDelegate : skinViewDelegates) {
            if (skinViewDelegate == null) {
                continue;
            }
            if (skinViewDelegate.self(view)) {
                return skinViewDelegate;
            }
        }
        ISkinViewDelegate skinViewDelegate = new SkinViewDelegate(view);
        skinViewDelegates.add(skinViewDelegate);
        return skinViewDelegate;
    }

    /**
     * 移除某个View不再换肤
     *
     * @param view
     */
    public void removeSkinView(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        List<ISkinViewDelegate> skinViewDelegates = mSkinViewsMap.get(view.getContext());
        if (skinViewDelegates == null) {
            return;
        }
        for (ISkinViewDelegate skinViewDelegate : skinViewDelegates) {
            if (skinViewDelegate == null) {
                continue;
            }
            if (skinViewDelegate.self(view)) {
                skinViewDelegates.remove(skinViewDelegate);
                break;
            }
        }
    }

    /**
     * 添加皮肤View
     *
     * @param context
     * @param skinView
     */
    public void addSkinView(Context context, ISkinViewDelegate skinView) {
        List<ISkinViewDelegate> skinViews = mSkinViewsMap.get(context);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
        }
        if (skinViews.contains(skinView)) {
            return;
        }
        skinViews.add(skinView);
        if (isNeedCreateNotify()) {
            skinView.apply();
        }
        mSkinViewsMap.put(context, skinViews);
    }

    /**
     * 一定要与{@link SkinManager#removeListener(Context)}配对使用
     *
     * @param context
     */
    public void addListener(Context context) {
        if (mListSkinChanged.contains(context)) {
            return;
        }
        mListSkinChanged.add(context);
    }

    /**
     * 移除View、通知的页面
     *
     * @param context
     */
    public void removeListener(Context context) {
        mListSkinChanged.remove(context);
        mSkinViewsMap.remove(context);
    }

    /**
     * 通知所有需要通知的UI
     */
    public void notifySkinChanged() {
        for (Context skinChanged : mListSkinChanged) {
            if (skinChanged == null) {
                continue;
            }
            apply(skinChanged);
        }
    }

    /**
     * 恢复默认皮肤
     */
    public void resetDefault() {
        if (context == null) {
            throw new NullPointerException("Context not be null");
        }
        ResourceManager.getInstance().resetDefault();
        notifySkinChanged();
    }


    /**
     * 根据资源id获取主app资源名及类型
     *
     * @param resId
     * @return
     */
    public ResourceInfo getResourceInfo(int resId) {
        if (context == null) {
            throw new NullPointerException("Context not be null");
        }
        return ResourceManager.getInstance().getResourceInfo(resId);
    }

    /**
     * 获取资源文件drawable
     *
     * @param resName
     * @return
     */
    public Drawable getDrawable(String resName, String resType) {
        if (context == null) {
            throw new NullPointerException("Context not be null");
        }
        return ResourceManager.getInstance().getDrawable(resName, resType);
    }


    /**
     * 获取资源文件color的id
     *
     * @param resName
     * @return
     */
    public ColorStateList getColorStateList(String resName, String resType) {
        if (context == null) {
            throw new NullPointerException("Context not be null");
        }
        return ResourceManager.getInstance().getColorStateList(resName, resType);
    }


    /**
     * 是否已经加载了皮肤 创建View时即更新皮肤
     *
     * @return
     */
    public boolean isNeedCreateNotify() {
        return ResourceManager.getInstance().isNeedCreateNotify();
    }


    private static class I {
        static SkinManager sInstance = new SkinManager();
    }

}
