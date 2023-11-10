package es.ua.eps.chatapp

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

object ChatServer {
    private const val PORT = 8081
    private val clientSockets = mutableListOf<Socket>()

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val serverSocket = ServerSocket(PORT)
            println("El servidor está corriendo en el puerto: $PORT")

            while (true) {
                val clientSocket = serverSocket.accept()
                clientSockets.add(clientSocket)

                println("Cliente conectado: ${clientSocket.inetAddress.hostAddress}")

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
                    println("Recibido: $message")

                    broadcastMessage(message, socket)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                clientSockets.remove(socket)
                println("Cliente se desconectó: ${socket.inetAddress.hostAddress}")
            }
        }
    }

    private fun broadcastMessage(message: String, senderSocket: Socket) {
        try {
            for (clientSocket in clientSockets) {
                if (clientSocket != senderSocket) {
                    val clientOutputStream = clientSocket.getOutputStream()
                    clientOutputStream.write(message.toByteArray())
                    clientOutputStream.flush()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
