package com.example.kotlin_random_chat.domain.signin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_random_chat.R
import com.example.kotlin_random_chat.api.response.ApiResponse
import com.example.kotlin_random_chat.api.response.SigninResponse
import com.example.kotlin_random_chat.databinding.ActivitySigninBinding
import com.example.kotlin_random_chat.domain.auth.Auth
import com.example.kotlin_random_chat.domain.randomchat.RandomChatActivity
import splitties.activities.start
import java.lang.ref.WeakReference

class SigninActivity : AppCompatActivity(), SigninNavigator {
    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(SigninViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivitySigninBinding>(
            this,
            R.layout.activity_signin
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Auth.signout()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun startRandomChatActivity(
        response: ApiResponse<SigninResponse>
    ) {
        start<RandomChatActivity>()
        finish()
    }

}
