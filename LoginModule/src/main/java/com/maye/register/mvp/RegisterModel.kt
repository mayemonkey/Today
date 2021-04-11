package com.maye.register.mvp

import com.maye.base.bean.User
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

/**
 * RegisterModel接口
 */
interface RegisterModel {
    fun register(user: User): Observable<ResponseBody>
}