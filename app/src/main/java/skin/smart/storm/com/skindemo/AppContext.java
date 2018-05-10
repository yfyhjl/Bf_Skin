package skin.smart.storm.com.skindemo;

import android.app.Application;

import me.lxw.dtl.skin.ResourceManager;
import me.lxw.dtl.skin.SkinManager;

/**
 * Created by din on 2017/11/22.
 * <p>
 * Email: godcok@163.com
 */
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(getApplicationContext());
    }
}
