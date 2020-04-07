package com.example.kotlin_random_chat.common

import android.app.Activity
import android.content.Intent
import com.example.kotlin_random_chat.App
import splitties.activities.start

inline fun <reified A: Activity> clearTasksAndStartNewActivity() {
    App.instance.start<A> {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}