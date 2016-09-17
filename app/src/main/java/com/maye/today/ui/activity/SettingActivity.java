package com.maye.today.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.maye.today.setting.SettingView;
import com.maye.today.today.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends Activity implements SettingView {

    private MaterialDialog dialog;
    private CircleImageView civ_avatar;
    private MaterialEditText met_nickname;
    private MaterialEditText met_password;
    private MaterialEditText met_email;
    private MaterialEditText met_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initComponent();
    }

    private void initComponent() {
        civ_avatar = (CircleImageView) findViewById(R.id.civ_avatar);

        met_nickname = (MaterialEditText) findViewById(R.id.met_nickname);
        met_password = (MaterialEditText) findViewById(R.id.met_password);
        met_email = (MaterialEditText) findViewById(R.id.met_email);
        met_phone = (MaterialEditText) findViewById(R.id.met_phone);

        dialog = new MaterialDialog.Builder(this).title("更新信息").content("用户信息修改中").progress(true, 0).show();
    }

    @Override
    public void showInputDialog(boolean visible, String title, String content) {

    }

    @Override
    public void showProgressDialog(boolean visible) {
        //判定MaterialDialog显示情况
        if (dialog != null) {
            if (visible) {
                dialog.show();
            } else {
                dialog.dismiss();
            }
        }
    }
}
