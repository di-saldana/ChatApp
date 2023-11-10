package es.ua.eps.chatapp

// Objeto singleton para el cliente del chat
object ClientSingleton {
    lateinit var chatClient: ChatClient
        private set

    // Función para inicializar el cliente del chat
    fun initialize() {
        chatClient = ChatClient()
    }
}
