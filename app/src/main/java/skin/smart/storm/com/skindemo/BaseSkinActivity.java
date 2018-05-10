package skin.smart.storm.com.skindemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.LayoutInflaterCompat;

import me.lxw.dtl.skin.SkinInflaterFactory;
import me.lxw.dtl.skin.SkinManager;

/**
 * Created by din on 2017/11/7.
 * <p>
 * Email: godcok@163.com
 */
public class BaseSkinActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        LayoutInflaterCompat.setFactory(getLayoutInflater(), new SkinInflaterFactory());
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().removeListener(this);
    }

}
