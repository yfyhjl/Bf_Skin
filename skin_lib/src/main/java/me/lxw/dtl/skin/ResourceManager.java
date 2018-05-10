package me.lxw.dtl.skin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ResourceManager {

    private Context mContext;

    private Resources mHostRes;

    private List<SkinInfo> mSkinInfos = new ArrayList<>();

    private ResourceManager() {
    }

    static ResourceManager getInstance() {
        return I.sInstance;
    }

    /**
     * create AssetManager for plugin according apkPath
     *
     * @param apkPath
     * @return
     */
    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void init(Context context) {
        mContext = context.getApplicationContext();
        mHostRes = mContext.getResources();
        resetDefault();
    }

    /**
     * 重置皮肤
     */
    void resetDefault() {
        mSkinInfos.clear();
        addDefaultSkin();
    }

    /**
     *
     * @param paths 根据顺序优先加载
     */
     boolean loadSkins(String... paths) {
        if(paths == null) {
            return false;
        }
        mSkinInfos.clear();
        for (String path : paths) {
            SkinInfo skinInfo = loadSkin(path);
            if (skinInfo != null) {
                mSkinInfos.add(skinInfo);
            }
        }
        addDefaultSkin();
        if (mSkinInfos.size() > 1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 加载主app资源皮肤、系统资源
     */
    private void addDefaultSkin() {
        SkinInfo info = new SkinInfo();
        info.packageName = mContext.getPackageName();
        info.resources = mContext.getResources();
        mSkinInfos.add(info);

        info = new SkinInfo();
        info.packageName = "android";
        info.resources = mContext.getResources();
        mSkinInfos.add(info);
    }

    /**
     * 加载皮肤
     * @param path
     * @retur
     */
    private SkinInfo loadSkin(String path) {
        SkinInfo info = null;
        try {
            PackageInfo pkgInfo = mContext.getPackageManager().getPackageArchiveInfo(path, 0);
            String mPackageName = pkgInfo.packageName;
            AssetManager mAssetMgr = createAssetManager(path);
            Resources mRes = createResources(mAssetMgr);
            info = new SkinInfo();
            info.packageName = mPackageName;
            info.resources = mRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * use assetManager to create resource
     *
     * @param assetManager
     * @return
     */
    private Resources createResources(AssetManager assetManager) {
        Resources superRes = mContext.getResources();
        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(),
                superRes.getConfiguration());
        return resources;
    }

    /**
     * 根据资源id获取主app资源名及类型
     * @param resId
     * @return
     */
    ResourceInfo getResourceInfo(int resId) {
        return new ResourceInfo(mHostRes.getResourceEntryName(resId),
                mHostRes.getResourceTypeName(resId));
    }


    /**
     * 获取
     * @param resourceInfo
     * @return
     */
    Drawable getDrawable(ResourceInfo resourceInfo) {
        return getDrawable(resourceInfo.getName(), resourceInfo.getType());
    }

    /**
     * 获取资源文件drawable
     *
     * @param resName
     * @return
     */
    Drawable getDrawable(String resName, String resType) {
//        long time = System.currentTimeMillis();
        for (SkinInfo skinInfo : mSkinInfos) {
            if (skinInfo == null || skinInfo.resources == null || skinInfo.packageName == null) {
                continue;
            }
            try {
                int resId = getResId(skinInfo.resources, skinInfo.packageName, resName, resType);
                if (resId == 0) {
                    continue;
                }
                Drawable drawable = skinInfo.resources.getDrawable(resId);
                if (drawable != null) {
//                    Log.d("getResId", "getResId time =" + (System.currentTimeMillis() - time));
                    return drawable;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }


    /**
     * 获取资源文件color的id
     *
     * @param info
     * @return
     */
    ColorStateList getColorStateList(ResourceInfo info) {
        return getColorStateList(info.getName(), info.getType());
    }

    /**
     * 获取资源文件color的id
     *
     * @param resName
     * @return
     */
    ColorStateList getColorStateList(String resName, String resType) {
//        long time = System.currentTimeMillis();
        for (SkinInfo skinInfo : mSkinInfos) {
            if (skinInfo == null || skinInfo.resources == null || skinInfo.packageName == null) {
                continue;
            }
            try {
                int resId = getResId(skinInfo.resources, skinInfo.packageName, resName, resType);
                if (resId == 0) {
                    continue;
                }
                ColorStateList stateList = skinInfo.resources.getColorStateList(resId);
                if (stateList != null) {
//                    Log.d("getResId", "getResId time =" + (System.currentTimeMillis() - time));
                    return stateList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取资源文件ID
     *
     * @param resName
     * @param defType
     * @return
     */
    private int getResId(Resources res, String packageName, String resName, String defType) {
        int resId = res.getIdentifier(resName, defType, packageName);
        return resId;
    }

    boolean isNeedCreateNotify() {
        return mSkinInfos.size() > 2;
    }

    private static class SkinInfo {
        private String    packageName;
        private Resources resources;
    }

    private static class I {
        static ResourceManager sInstance = new ResourceManager();
    }
}
