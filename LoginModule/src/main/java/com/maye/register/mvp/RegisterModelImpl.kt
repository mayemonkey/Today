package com.maye.register.mvp

import com.maye.base.bean.User
import com.maye.net.NetUtil
import com.maye.register.api.RegisterServer
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import java.util.*

/**
 * RegisterModel实现
 */
class RegisterModelImpl : RegisterModel {
    override fun register(user: User): Observable<ResponseBody> {
        val map: MutableMap<String, String> = HashMap()
        map["username"] = user.getUsername()
        map["nickname"] = user.getNickname()
        map["avater"] = user.getAvatar()
        map["password"] = user.getPassword()
        map["phone"] = user.getPhone()
        map["email"] = user.getEmail()
        return NetUtil.createApi(RegisterServer::class.java).register(map)
    }
}