package me.lxw.dtl.skin;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import me.lxw.dtl.skin.view.delegate.ISkinViewDelegate;
import me.lxw.dtl.skin.view.delegate.impl.SkinViewDelegate;

/**
 * Created by din on 2017/11/28.
 * <p>
 * Email: godcok@163.com
 */
public class SkinInflaterFactory implements LayoutInflaterFactory {
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class
    };

    private static final Map<String, Constructor<? extends View>> sConstructorMap = new HashMap<>();
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };
    private final Object[] mConstructorArgs = new Object[2];

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                Class<? extends View> clazz = context.getClassLoader()
                        .loadClass(prefix != null ? (prefix + name) : name).asSubclass(View.class);
                constructor = clazz.getConstructor(sConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            }
            final View view = constructor.newInstance(mConstructorArgs);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && view instanceof ViewStub) {
                // Use the same context when inflating ViewStub later.
                final ViewStub viewStub = (ViewStub) view;
                viewStub.setLayoutInflater(LayoutInflater.from(context).cloneInContext((Context) mConstructorArgs[0]));
            }

            return view;
        } catch (Exception e) {
            return null;
        }
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (name.equals("view")) {
                name = attrs.getAttributeValue(null, "class");
            }

            if (-1 == name.indexOf('.')) {
                View view = null;
                for (String prefix : sClassPrefixList) {
                    view = createView(context, name, prefix);
                }
                return view;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            return null;
        } finally {
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        boolean hasSkin = SkinTypeSupport.hasSkin(attrs);
        if (!hasSkin) {
            return null;
        }
//        long time = System.currentTimeMillis();
        View view = createViewFromTag(context, name, attrs);
        if (view != null) {
            Map<SkinType, ResourceInfo> skinAttr = SkinTypeSupport.getSkinTypes(context, view, attrs);
//        Log.d("SkinInflaterFactory", "createView time = " + (System.currentTimeMillis() - time));
            SkinViewDelegate skinView = new SkinViewDelegate(view, skinAttr);
            updateSkinViews(context, skinView);
        }
        return view;
    }

    /**
     * 更新皮肤存储
     *
     * @param context
     * @param view
     */
    private void updateSkinViews(Context context, ISkinViewDelegate view) {
        SkinManager.getInstance().addSkinView(context, view);
    }

}
