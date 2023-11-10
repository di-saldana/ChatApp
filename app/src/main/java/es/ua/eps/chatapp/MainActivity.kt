package es.ua.eps.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var chatClient: ChatClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatClient = ClientSingleton.chatClient

        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter()
        recyclerView.adapter = messageAdapter

        thread {
            while (true) {
                val receivedMessage = chatClient.receiveMessage()
                if (receivedMessage != null) {
                    runOnUiThread {
                        messageAdapter.addMessage("$receivedMessage")
                        messageAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val message = messageEditText.text.toString()
        if (message.isNotEmpty()) {
            chatClient.sendMessage("$message")

            messageAdapter.addMessage("$message")
            messageAdapter.notifyDataSetChanged()

            messageEditText.text.clear()
        }
    }
}
