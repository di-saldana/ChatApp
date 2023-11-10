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

    interface MessageListener {
        fun onMessageReceived(message: String)
    }

    fun setMessageListener(listener: MessageListener) {
        messageListener = listener
    }

    fun connect(server_ip: String, server_port: Int) {
        socket = Socket(server_ip, server_port)
        outputStream = socket?.getOutputStream()
        inputStream = BufferedReader(InputStreamReader(socket?.getInputStream()))

        // Start a thread to receive messages
        thread {
            while (true) {
                val message = inputStream?.readLine()
                message?.let {
                    messageListener?.onMessageReceived(it)
                }
            }
        }
    }

    fun sendMessage(message: String) {
        thread {
            val messageBytes = message.toByteArray(Charsets.UTF_8)
            outputStream?.write(messageBytes)
            outputStream?.flush()
        }
    }

    fun receiveMessage(): String? {
        try {
            val message = inputStream?.readLine()
            return message
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun disconnect() {
        socket?.close()
    }
}