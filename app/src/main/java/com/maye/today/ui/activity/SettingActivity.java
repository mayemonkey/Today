package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.maye.today.domain.User;
import com.maye.today.global.TodayApplication;
import com.maye.today.setting.SettingPresenterImpl;
import com.maye.today.setting.SettingView;
import com.maye.today.today.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends Activity implements SettingView, View.OnClickListener {

    private CircleImageView civ_avatar;
    private MaterialEditText met_nickname;
    private MaterialEditText met_email;
    private MaterialEditText met_phone;
    private MaterialDialog progressDialog;

    private static final int AVATAR_REQUEST = 0;
    private SettingPresenterImpl settingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initComponent();
    }

    private void initComponent() {
        ImageView iv_setting_back = (ImageView) findViewById(R.id.iv_setting_back);
        iv_setting_back.setOnClickListener(this);
        ImageView iv_setting_update = (ImageView) findViewById(R.id.iv_setting_update);
        iv_setting_update.setOnClickListener(this);

        civ_avatar = (CircleImageView) findViewById(R.id.civ_avatar);
        civ_avatar.setOnClickListener(this);

        met_nickname = (MaterialEditText) findViewById(R.id.met_nickname);
        met_email = (MaterialEditText) findViewById(R.id.met_email);
        met_phone = (MaterialEditText) findViewById(R.id.met_phone);

        progressDialog = new MaterialDialog.Builder(this).title("").content("").progress(true, 0).build();

        settingPresenter = new SettingPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingPresenter.getUserInfo(TodayApplication.getSessionId());
    }

    @Override
    public void setUserData(User user) {
        Glide.with(this).load(user.getAvatar()).centerCrop().crossFade().into(civ_avatar);

        met_nickname.setText(user.getNickname());
        met_email.setText(user.getEmail());
        met_phone.setText(user.getPhone());

    }

    @Override
    public void refreshUserData(User user) {
        met_nickname.setText(user.getNickname());
        met_email.setText(user.getEmail());
        met_phone.setText(user.getPhone());
    }

    @Override
    public void showProgressDialog(boolean visible, String title, String content) {
        if (visible) {
            progressDialog.setTitle(title);
            progressDialog.setContent(content);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;

            case R.id.iv_setting_update:
                String nickname = met_nickname.getText().toString();
                String email = met_email.getText().toString();
                String phone = met_phone.getText().toString();

                if (checkEmpty(met_nickname) && checkEmpty(met_email) && checkEmpty(met_phone)) {
                    User user = new User(nickname, email, phone);
                    settingPresenter.updateUserInfo(user);
                }

                break;

            case R.id.civ_avatar:
                startActivityForResult(new Intent(this, AlbumActivity.class), AVATAR_REQUEST);
                break;
        }
    }

    private boolean checkEmpty(MaterialEditText materialEditText) {
        if (TextUtils.isEmpty(materialEditText.getText().toString())) {
            materialEditText.setError("内容不可为空");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AVATAR_REQUEST) {
            if (resultCode == 0) {
                String path = data.getStringExtra("path");
                if (!TextUtils.isEmpty(path)) {
                    Glide.with(this).load(new File(path)).centerCrop().crossFade().into(civ_avatar);
                }
            }
        }
    }
}
