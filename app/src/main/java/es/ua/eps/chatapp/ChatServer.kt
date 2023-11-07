package es.ua.eps.chatapp

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

object ChatServer {
    private const val PORT = 12345
    private val clientSockets = mutableListOf<Socket>()

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val serverSocket = ServerSocket(PORT)
            println("Server is running on port $PORT")

            while (true) {
                val clientSocket = serverSocket.accept()
                clientSockets.add(clientSocket)

                println("Client connected: ${clientSocket.inetAddress.hostAddress}")

                val clientThread = Thread { ClientHandler(clientSocket).run() }
                clientThread.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private class ClientHandler(private val socket: Socket) : Runnable {
        private var inputStream: InputStream? = null
        private var outputStream: OutputStream? = null

        init {
            try {
                inputStream = socket.getInputStream()
                outputStream = socket.getOutputStream()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun run() {
            val buffer = ByteArray(1024)
            var bytesRead: Int
            try {
                while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
                    val message = String(buffer, 0, bytesRead)
                    println("Received: $message")

                    // Reenviar el mensaje a todos los clientes conectados
                    broadcastMessage(message)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                // El cliente se desconectó, elimínalo de la lista de clientes
                clientSockets.remove(socket)
                println("Client disconnected: ${socket.inetAddress.hostAddress}")
            }
        }
    }

    private fun broadcastMessage(message: String) {
        for (clientSocket in clientSockets) {
            try {
                val clientOutputStream = clientSocket.getOutputStream()
                clientOutputStream.write(message.toByteArray())
                clientOutputStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
