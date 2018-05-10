package me.lxw.dtl.skin;

/**
 * Created by din on 2017/11/22.
 * <p>
 * Email: godcok@163.com
 */
public class ResourceInfo {
    /**
     * 资源名
     */
    private String name;
    /**
     * 资源类型，drawable、color、mipmap等
     */
    private String type;

    public ResourceInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
