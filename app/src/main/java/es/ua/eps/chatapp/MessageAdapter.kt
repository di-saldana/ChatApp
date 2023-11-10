package es.ua.eps.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private val messages = ArrayList<String>()

    // Método que se llama cuando se crea una nueva instancia de MessageViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // Inflar el diseño del elemento de mensaje
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    // Método que se llama cuando se vincula un mensaje a un MessageViewHolder existente
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    // Método que devuelve la cantidad de mensajes en la lista
    override fun getItemCount(): Int {
        return messages.size
    }

    // Método para agregar un nuevo mensaje a la lista
    fun addMessage(message: String) {
        messages.add(message)
        // Notificar al adaptador que se ha insertado un nuevo elemento en la última posición
        notifyItemInserted(messages.size - 1)
    }

    // Clase interna que representa un ViewHolder para un elemento de mensaje
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.messageText)

        // Método para vincular un mensaje al ViewHolder
        fun bind(message: String) {
            messageText.text = message
        }
    }
}
