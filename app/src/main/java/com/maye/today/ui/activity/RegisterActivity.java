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
import com.maye.today.domain.User;
import com.maye.today.register.RegisterPresenter;
import com.maye.today.register.RegisterPresenterImpl;
import com.maye.today.register.RegisterView;
import com.maye.today.today.R;

/**
 * RegisterActivity
 */
public class RegisterActivity extends Activity implements RegisterView, View.OnClickListener {

    private RegisterPresenter registerPresenter;
    private TextInputLayout til_reg_username;
    private TextInputLayout til_reg_password;
    private TextInputLayout til_reg_email;
    private MaterialDialog progressDialog;

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
        iv_cancel.requestFocus();
        iv_cancel.setOnClickListener(this);

        til_reg_username = (TextInputLayout) findViewById(R.id.til_reg_username);
        til_reg_password = (TextInputLayout) findViewById(R.id.til_reg_password);
        til_reg_email = (TextInputLayout) findViewById(R.id.til_reg_email);

        Button iv_reg_commit = (Button) findViewById(R.id.iv_reg_commit);
        iv_reg_commit.setOnClickListener(this);

        progressDialog = new MaterialDialog.Builder(this).title("注册").content("提交信息中...").build();

        registerPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void inputError(String index, String error) {
        switch (index){
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
        if (show){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void startActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_reg_cancel:
                finish();
                break;

            case R.id.iv_reg_commit:
                if (checkInputLayout(til_reg_username) && checkInputLayout(til_reg_password) && checkInputLayout(til_reg_email)){
                    User user = new User();
                    user.setUsername(til_reg_username.getEditText().getText().toString());
                    user.setPassword(til_reg_password.getEditText().getText().toString());
                    user.setEmail(til_reg_email.getEditText().getText().toString());
                    registerPresenter.checkRegister(user);
                }

                break;
        }
    }

    private boolean checkInputLayout(TextInputLayout textInputLayout){
        EditText editText = textInputLayout.getEditText();
        if (editText == null){
            return false;
        }

        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)){
            textInputLayout.setError("内容不可为空");
        }
        return true;
    }

}
