package com.maye.base.expand

import android.view.View

/** View拓展方法 **/

private val viewClickMap = hashMapOf<Int, Long>()

/**
 * View点击方法
 */
fun View.click(clickAction: () -> Unit) {
    clickAction()
}

/**
 * View点击方法（规避快速误触）
 */
fun View.safeClick(clickAction: () -> Unit) {
    val lastClickTime = viewClickMap[id] ?: 0
    if (System.currentTimeMillis() - lastClickTime > 300) {
        // 超出误触限制，执行点击逻辑
        clickAction()
        viewClickMap.remove(id)
    } else {
        viewClickMap[id] = System.currentTimeMillis()
    }
}