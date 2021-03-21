package com.maye.today.util;


import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;


public class InputUtil {

    /**
     * 检查文本输入是否正确
     *
     * @param textInputLayout 输入框外布局
     * @return 检查通过：true  |   检查未通过：false
     */
    public static boolean checkTextInputLayout(final TextInputLayout textInputLayout) {
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
            return false;
        }

        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Observable.just("").
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String inputIndex) {
                            textInputLayout.setError("输入内容不可为空");
                        }
                    });
            return false;
        }


        return true;
    }

}
