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

        // Establecer el evento onClickListener para el botón de conexión
        buttonConnect.setOnClickListener {
            val server_ip = editTextAddress.text.toString()
            val server_port = editTextPort.text.toString().toInt()

            // Crear un hilo para realizar la conexión con el servidor en segundo plano
            thread {
                ClientSingleton.chatClient.connect(server_ip, server_port)
            }

            // Crear una instancia de la actividad principal y comenzarla
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Establecer el evento onClickListener para el botón de clear
        buttonClear.setOnClickListener {
            // Limpiar los campos de dirección y puerto
            editTextAddress.text.clear()
            editTextPort.text.clear()
        }
    }
}
