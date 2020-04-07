package com.example.kotlin_random_chat.domain.randomchat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlin_random_chat.api.RandomChatApi
import com.example.kotlin_random_chat.common.Prefs
import com.example.kotlin_random_chat.common.clearTasksAndStartNewActivity
import com.example.kotlin_random_chat.domain.auth.Auth
import com.example.kotlin_random_chat.domain.randomchat.messagelistener.Message
import com.example.kotlin_random_chat.domain.randomchat.messagelistener.RandomChatMessageListener
import com.example.kotlin_random_chat.domain.randomchat.websocketclient.RandomChatWebSocketClient
import com.example.kotlin_random_chat.domain.signin.SigninActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.lang.ref.WeakReference

class RandomChatViewModel(app: Application)
    : AndroidViewModel(app), RandomChatMessageListener {

    private val client = RandomChatWebSocketClient(this)

    var navigatorRef: WeakReference<RandomChatNavigator>? = null

    private val navigator get() = navigatorRef?.get()

    val inputMessage = MutableLiveData("")

    val messages = mutableListOf<MessageModel>()

    private fun handleMessage(
        owner: MessageModel.Owner,
        message: Message
    ) {
        synchronized(messages) {
            val messageModel = MessageModel(
                owner,
                message.senderNickName,
                message.message
            )

            messages.lastOrNull()?.let { lastMessage ->
                messageModel.collapseName =
                    lastMessage.nickName == messageModel.nickName &&
                        lastMessage.owner == messageModel.owner
            }

            messages.add(messageModel)

            viewModelScope.launch(Dispatchers.Main) {
                navigator?.onMessage(messageModel)
            }
        }
    }

    fun sendMessage() = viewModelScope.launch {
        inputMessage.value?.let { content ->
            if(content.isEmpty() || content.isBlank())
                return@launch

            inputMessage.value = ""

            runCatching {
                RandomChatApi.sendMessage(content)

                Prefs.nickName?.let { nickName ->
                    val message = Message(nickName, content)
                    handleMessage(MessageModel.Owner.SENDER, message)
                }
            }.onFailure {
                onMessageError(it)
            }
        }
    }

    override fun onMessage(message: Message) {
        handleMessage(MessageModel.Owner.RECEIVER, message)
    }

    override fun onMessageError(t: Throwable) {
        Log.e(TAG, "메세지 오류 발생.", t)
        toast("메세지 오류가 발생했습니다.")
    }

    override fun onNetworkError(t: Throwable) {
        Log.e(TAG, "네트워크 오류 발생.", t)
        toast("네트워크 오류가 발생했습니다.")

        Auth.signout()
        clearTasksAndStartNewActivity<SigninActivity>()
    }

    override fun onStart() {
        Log.d(TAG, "채팅 시작.")
    }

    override fun onClosed() {
        Auth.signout()
        clearTasksAndStartNewActivity<SigninActivity>()
    }

    companion object {
        const val TAG = "RandomChatViewModel"
    }

}
