package es.ua.eps.chatapp

object ClientSingleton {
    lateinit var chatClient: ChatClient
        private set

    fun initialize() {
        chatClient = ChatClient()
    }
}
