package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.maye.today.domain.User;
import com.maye.today.register.RegisterPresenter;
import com.maye.today.register.RegisterPresenterImpl;
import com.maye.today.register.RegisterView;
import com.maye.today.today.R;
import com.maye.today.util.InputUtil;
import com.maye.today.util.Md5Util;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.maye.today.today.R.id.civ_avatar;
import static com.maye.today.today.R.id.civ_reg_avatar;

/**
 * RegisterActivity
 */
public class RegisterActivity extends Activity implements RegisterView, View.OnClickListener {

    private RegisterPresenter registerPresenter;
    private TextInputLayout til_reg_username;
    private TextInputLayout til_reg_password;
    private TextInputLayout til_reg_email;
    private MaterialDialog progressDialog;

    private static final int AVATAR_REQUEST = 0;
    private CircleImageView civ_reg_avatar;
    private TextInputLayout til_reg_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponent();
    }

    /**
     * 初始化控件
     */
    private void initComponent() {
        ImageView iv_cancel = (ImageView) findViewById(R.id.iv_reg_cancel);
        iv_cancel.setOnClickListener(this);

        civ_reg_avatar = (CircleImageView) findViewById(R.id.civ_reg_avatar);
        civ_reg_avatar.setOnClickListener(this);

        til_reg_username = (TextInputLayout) findViewById(R.id.til_reg_username);
        til_reg_password = (TextInputLayout) findViewById(R.id.til_reg_password);
        til_reg_nickname = (TextInputLayout) findViewById(R.id.til_reg_nickname);
        til_reg_email = (TextInputLayout) findViewById(R.id.til_reg_email);

        Button iv_reg_commit = (Button) findViewById(R.id.iv_reg_commit);
        iv_reg_commit.setOnClickListener(this);

        progressDialog = new MaterialDialog.Builder(this).title("注册中").content("正在提交用户信息...").build();

        registerPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void inputError(String index, String error) {
        switch (index) {
            case "USERNAME":
                til_reg_username.setError(error);
                break;

            case "PASSWORD":
                til_reg_password.setError(error);
                break;

            case "EMAIL":
                til_reg_email.setError(error);
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void startActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_reg_cancel:
                finish();
                break;

            case R.id.civ_reg_avatar:
                startActivityForResult(new Intent(this, AlbumActivity.class), AVATAR_REQUEST);
                break;

            case R.id.iv_reg_commit:
                if (InputUtil.checkTextInputLayout(til_reg_username) && InputUtil.checkTextInputLayout(til_reg_password) &&
                        InputUtil.checkTextInputLayout(til_reg_nickname) && InputUtil.checkTextInputLayout(til_reg_email)) {

                    String username = til_reg_username.getEditText().getText().toString();
                    String password = til_reg_password.getEditText().getText().toString();
                    String nickname = til_reg_nickname.getEditText().getText().toString();
                    String email = til_reg_email.getEditText().getText().toString();
                    String avatar = (String) civ_reg_avatar.getTag();

                    User user = new User(username, Md5Util.textToMd5(password), nickname, email, avatar);
                    registerPresenter.checkRegister(user);
                }
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
                    civ_reg_avatar.setTag(path);
                    Glide.with(this).load(new File(path)).centerCrop().crossFade().into(civ_reg_avatar);
                }
            }
        }
    }
}
