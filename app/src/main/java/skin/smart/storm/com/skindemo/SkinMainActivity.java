package skin.smart.storm.com.skindemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import me.lxw.dtl.skin.ResourceInfo;
import me.lxw.dtl.skin.ResourceManager;
import me.lxw.dtl.skin.SkinManager;
import me.lxw.dtl.skin.view.delegate.ISkinViewDelegate;

public class SkinMainActivity extends BaseSkinActivity {

    public static final String TAG = "SkinMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.fragment_add_fragment, new SkinFragment())
//                .commitAllowingStateLoss();
////        ResourceInfo info= ResourceManager.getInstance().getResourceInfo(android.R.color.background_dark);
//        BaseDialog dialog = new BaseDialog(this);
//        dialog.show();


//        load();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putParcelable("android:support:fragments", null);
        }
    }

    public void load() {
        SkinManager.getInstance().loadSkin("/sdcard/skin_app-debug.apk");

    }

    public void Changed(View view) {
        ISkinViewDelegate skinView = SkinManager.getInstance().getSkinView(view);
        skinView.setImageResource(R.mipmap.abc);

//        mImageView.setImageDrawable(ResourceManager.getInstance().getDrawable(ResourceManager
//                .getInstance().getResourceInfo(R.mipmap.abc)));


    }


    public void Host(View view) {
        SkinManager.getInstance().resetDefault();
    }

    public void Skin(View view) {
        load();
    }
}
