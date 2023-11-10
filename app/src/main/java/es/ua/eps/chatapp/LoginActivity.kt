package es.ua.eps.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread
class LoginActivity : AppCompatActivity() {
    private lateinit var editTextAddress: EditText
    private lateinit var editTextPort: EditText
    private lateinit var buttonConnect: Button
    private lateinit var buttonClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ClientSingleton.initialize()

        editTextAddress = findViewById(R.id.addressEditText)
        editTextPort = findViewById(R.id.portEditText)
        buttonConnect = findViewById(R.id.connectButton)
        buttonClear = findViewById(R.id.clearButton)

        buttonConnect.setOnClickListener {
            val server_ip = editTextAddress.text.toString()
            val server_port = editTextPort.text.toString().toInt()

            thread {
                ClientSingleton.chatClient.connect(server_ip, server_port)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonClear.setOnClickListener {
            editTextAddress.text.clear()
            editTextPort.text.clear()
        }
    }
}
