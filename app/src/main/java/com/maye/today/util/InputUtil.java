package com.maye.today.util;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


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
                    subscribe(new Action1<String>() {
                        @Override
                        public void call(String inputIndex) {
                            textInputLayout.setError("输入内容不可为空");
                        }
                    });
            return false;
        }


        return true;
    }

}
