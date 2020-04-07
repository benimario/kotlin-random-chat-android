package com.example.kotlin_random_chat.domain.randomchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_random_chat.R
import com.example.kotlin_random_chat.databinding.ActivityRandomChatBinding
import java.lang.ref.WeakReference

class RandomChatActivity : AppCompatActivity(), RandomChatNavigator {

    val viewModel by lazy {
        ViewModelProvider(this)
            .get(RandomChatViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    val binding by lazy {
        DataBindingUtil
            .setContentView<ActivityRandomChatBinding>(
                this,
                R.layout.activity_random_chat
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupConversationAdapter()
    }

    private fun setupConversationAdapter() {
        binding.conversation.layoutManager = LinearLayoutManager(this)
        binding.conversation.adapter =
            ConversationAdapter(this).also {
                it.addMessages(viewModel.messages)
            }
    }

    override fun onMessage(messageModel: MessageModel) {
        val adapter = binding.conversation.adapter as? ConversationAdapter

        adapter?.let {
            adapter.addMessage(messageModel)

            val lastPosition = adapter.itemCount - 1
            binding.conversation.smoothScrollToPosition(lastPosition)
        }
    }

}
