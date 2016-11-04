package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.maye.today.domain.User;
import com.maye.today.global.TodayApplication;
import com.maye.today.setting.SettingPresenterImpl;
import com.maye.today.setting.SettingView;
import com.maye.today.today.R;
import com.maye.today.util.InputUtil;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends Activity implements SettingView, View.OnClickListener {

    private CircleImageView civ_avatar;
    private MaterialDialog progressDialog;

    private static final int AVATAR_REQUEST = 0;
    private SettingPresenterImpl settingPresenter;
    private MaterialDialog messageDialog;
    private TextInputLayout til_setting_nickname;
    private TextInputLayout til_setting_email;
    private TextInputLayout til_setting_phone;

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

        til_setting_nickname = (TextInputLayout) findViewById(R.id.til_setting_nickname);
        til_setting_email = (TextInputLayout) findViewById(R.id.til_setting_email);
        til_setting_phone = (TextInputLayout) findViewById(R.id.til_setting_phone);

        progressDialog = new MaterialDialog.Builder(this).title("").content("").progress(true, 0).build();

        messageDialog = new MaterialDialog.Builder(this).title("").content("").positiveText("确定").
                onPositive(new MaterialDialog.SingleButtonCallback() {
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();
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

        til_setting_nickname.getEditText().setText(user.getNickname());
        til_setting_email.getEditText().setText(user.getEmail());
        til_setting_phone.getEditText().setText(user.getPhone());
    }

    @Override
    public void showMessageDialog(String title, String content) {
        messageDialog.setTitle(title);
        messageDialog.setContent(content);
        messageDialog.show();
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
                if (InputUtil.checkTextInputLayout(til_setting_nickname) && InputUtil.checkTextInputLayout(til_setting_email) &&
                        InputUtil.checkTextInputLayout(til_setting_phone)) {

                    String nickname = til_setting_nickname.getEditText().getText().toString();
                    String email = til_setting_email.getEditText().getText().toString();
                    String phone = til_setting_phone.getEditText().getText().toString();
                    User user = new User(nickname, email, phone);
                    settingPresenter.updateUserInfo(user);
                }
                break;

            case R.id.civ_avatar:
                startActivityForResult(new Intent(this, AlbumActivity.class), AVATAR_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AVATAR_REQUEST) {
            if (resultCode == 0) {
                String path = data.getStringExtra("path");
                if (!TextUtils.isEmpty(path)) {
                    civ_avatar.setTag(path);
                    Glide.with(this).load(new File(path)).centerCrop().crossFade().into(civ_avatar);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        settingPresenter.onViewDestroy();
    }
}
