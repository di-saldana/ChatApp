package es.ua.eps.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServerMessageAdapter : RecyclerView.Adapter<ServerMessageAdapter.ServerMessageViewHolder>() {
    private val messages = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerMessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_server_message, parent, false)
        return ServerMessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServerMessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessage(message: String) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    class ServerMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.serverMessageText)

        fun bind(message: String) {
            messageText.text = message
        }
    }
}
