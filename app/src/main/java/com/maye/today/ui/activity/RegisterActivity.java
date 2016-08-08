package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.maye.today.register.RegisterPresenter;
import com.maye.today.register.RegisterPresenterImpl;
import com.maye.today.register.RegisterView;
import com.maye.today.today.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * RegisterActivity
 */
public class RegisterActivity extends Activity implements RegisterView, View.OnClickListener {

    private RegisterPresenter registerPresenter;
    private MaterialEditText met_reg_username;
    private MaterialEditText met_reg_password;
    private MaterialEditText met_reg_email;

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
        met_reg_username = (MaterialEditText) findViewById(R.id.met_reg_username);
        met_reg_username.setFocusFraction(0.4f);
        met_reg_password = (MaterialEditText) findViewById(R.id.met_reg_password);
        met_reg_password.setFocusFraction(0.4f);
        met_reg_email = (MaterialEditText) findViewById(R.id.met_reg_email);
        met_reg_email.setFocusFraction(0.4f);

        Button iv_reg_commit = (Button) findViewById(R.id.iv_reg_commit);
        iv_reg_commit.setOnClickListener(this);

        registerPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void inputError(String index, String error) {
        switch (index){
            case "USERNAME":
                met_reg_username.setError(error);
                break;

            case "PASSWORD":
                met_reg_password.setError(error);
                break;

            case "EMAIL":
                met_reg_email.setError(error);
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {

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

                break;
        }
    }
}
