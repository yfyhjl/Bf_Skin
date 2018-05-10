package me.lxw.dtl.skin;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.lxw.dtl.skin.view.delegate.impl.SkinViewDelegate;

/**
 * Created by din on 2017/11/8.
 * <p>
 * Email: godcok@163.com
 */
public class SkinTypeSupport {
    /**
     * 获取支持的换肤属性
     *
     * @param attrs
     * @return
     */
    public static boolean hasSkin(AttributeSet attrs) {
        if (attrs == null) {
            return false;
        }
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            if ("skinEnable".equals(attrName)) {
                return true;
            }
        }
        return false;

    }

    private static Map<SkinType, ResourceInfo> getSkinTypesfromStyle(Context context, AttributeSet attributeSet, List<SkinType> skinType) {
        Map<SkinType, ResourceInfo> skinTypes = new HashMap<>();
        TypedArray a;
        try {
            Class clasz = Class.forName("com.android.internal.R$styleable");
            TypedValue value = new TypedValue();
            for (SkinType skin : skinType) {
                Field field = clasz.getDeclaredField(skin.getView());
                field.setAccessible(true);
                int[] styleables = (int[]) field.get(null);

                field = clasz.getDeclaredField(skin.getView() + "_" + skin.getSkinType());
                field.setAccessible(true);
                int styleable = field.getInt(null);

                a = context.obtainStyledAttributes(attributeSet, styleables);
                boolean b = a.getValue(styleable, value);
                if (b) {
                    String entryName = context.getResources().getResourceEntryName(value.resourceId);
                    String type = context.getResources().getResourceTypeName(value.resourceId);
                    skinTypes.put(skin, new ResourceInfo(entryName, type));
                }
                if (a != null) {
                    a.recycle();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
        }

        return skinTypes;
    }


    /**
     * 由于效率问题，只针对有Style的控件生效
     *
     * @param context
     * @param attrs
     * @return
     */
    private static Map<SkinType, ResourceInfo> supportStyle(Context context, AttributeSet attrs, List<SkinType> typeList) {
        Map<SkinType, ResourceInfo> skinTypes = new HashMap<>();
        if (attrs == null) {
            return skinTypes;
        }
        skinTypes = getSkinTypesfromStyle(context, attrs, typeList);
        return skinTypes;
    }

    /**
     * 获取支持的换肤属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static Map<SkinType, ResourceInfo> getSkinTypes(Context context, View view, AttributeSet attrs) {
        Map<SkinType, ResourceInfo> skinTypes = new HashMap<>();
        if (attrs == null) {
            return skinTypes;
        }
        boolean hasStyle = false;
        ArrayList<SkinType> list = new ArrayList<>();
        if (view instanceof ImageView) {
            list.add(SkinType.SRC);
        } else if (view instanceof TextView) {
            list.add(SkinType.COLOR);
        }
        list.add(SkinType.BG);

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if ("style".equals(attrName)) {
                hasStyle = true;
            }
            SkinType skinType = getSupportType(attrName);
            if (skinType == null) continue;

            if (attrValue.startsWith("@")) {
                list.remove(skinType);
                int id = Integer.parseInt(attrValue.substring(1));
                String entryName = context.getResources().getResourceEntryName(id);
                String type = context.getResources().getResourceTypeName(id);
                skinTypes.put(skinType, new ResourceInfo(entryName, type));
            }
        }

        if (hasStyle && !list.isEmpty()) {
            skinTypes.putAll(supportStyle(context, attrs, list));
        }

        return skinTypes;

    }

    /**
     * 皮肤支持类型
     *
     * @param attrName
     * @return
     */
    private static SkinType getSupportType(String attrName) {
        for (SkinType skinType : SkinType.values()) {
            if (skinType.getSkinType().equals(attrName))
                return skinType;
        }
        return null;
    }

    /**
     * 设置皮肤View
     *
     * @param context
     * @param view
     * @param attrs
     */
    public static void setSkinView(Context context, View view, AttributeSet attrs) {
        Map<SkinType, ResourceInfo> skinAttr = SkinTypeSupport.getSkinTypes(context, view, attrs);
        SkinManager.getInstance().addSkinView(context, new SkinViewDelegate(view, skinAttr));
    }

}
