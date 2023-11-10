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

        // Inicializar el cliente de chat
        chatClient = ClientSingleton.chatClient

        // Obtener referencias a los elementos de la interfaz de usuario
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter()
        recyclerView.adapter = messageAdapter

        // Iniciar un hilo para recibir mensajes continuamente
        thread {
            while (true) {
                // Recibir un mensaje del cliente de chat
                val receivedMessage = chatClient.receiveMessage()
                if (receivedMessage != null) {
                    runOnUiThread {
                        // Agregar el mensaje recibido al adaptador de mensajes
                        messageAdapter.addMessage("${receivedMessage.toString()}")
                        messageAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        // Configurar el botón de enviar mensaje
        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    // Función para enviar un mensaje
    private fun sendMessage() {
        val message = messageEditText.text.toString()

        if (message.isNotEmpty()) {
            // Enviar el mensaje al cliente de chat
            chatClient.sendMessage("$message")

            // Agregar el mensaje al adaptador de mensajes
            messageAdapter.addMessage("$message")
            messageAdapter.notifyDataSetChanged()

            // Limpiar el campo de texto del mensaje
            messageEditText.text.clear()
        }
    }
}
