package es.ua.eps.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var chatClient: ChatClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatClient = ChatClient()
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        // Initialize and configure the RecyclerView for displaying messages
        // Set up an adapter to display messages in the RecyclerView

        // Move the connection code to a background thread
        thread {
            chatClient.connect()
        }

        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val message = messageEditText.text.toString()
        if (message.isNotEmpty()) {
            chatClient.sendMessage(message)
            // Add the message to the local RecyclerView to display it in the user interface
            // Clear the EditText
        }
    }

    // Implement methods to receive messages from the server and update the RecyclerView
}

