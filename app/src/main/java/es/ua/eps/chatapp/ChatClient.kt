package es.ua.eps.chatapp

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import kotlin.concurrent.thread

class ChatClient {
    private var socket: Socket? = null

    private var outputStream: OutputStream? = null
    private var inputStream: BufferedReader? = null

    private var messageListener: MessageListener? = null

    // Interfaz para escuchar mensajes recibidos
    interface MessageListener {
        fun onMessageReceived(message: String)
    }

    // Establece el listener para recibir mensajes
    fun setMessageListener(listener: MessageListener) {
        messageListener = listener
    }

    // Conecta al servidor de chat
    fun connect(server_ip: String, server_port: Int) {
        socket = Socket(server_ip, server_port)
        outputStream = socket?.getOutputStream()
        inputStream = BufferedReader(InputStreamReader(socket?.getInputStream()))

        // Inicia un thread para recibir mensajes
        thread {
            while (true) {
                val message = inputStream?.readLine()
                message?.let {
                    messageListener?.onMessageReceived(it)
                }
            }
        }
    }

    // Envía un mensaje al servidor de chat
    fun sendMessage(message: String) {
        thread {
            val messageBytes = message.toByteArray(Charsets.UTF_8)
            outputStream?.write(messageBytes)
            outputStream?.flush()
        }
    }

    // Recibe un mensaje del servidor del chat
    fun receiveMessage(): String? {
        try {
            return inputStream?.readLine()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    // Desconecta del servidor del chat
    fun disconnect() {
        socket?.close()
    }
}
