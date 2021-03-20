package com.tejsumeru.bankingsystem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransferAdapter(internal var context: Context, internal var transaction : ArrayList<HashMap<String,String>>) : RecyclerView.Adapter<TransferAdapter.TransferHolder>()
{
    var controller = SqliteController(context,"customer_db.db",null,1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferHolder {
        return TransferHolder(LayoutInflater.from(context).inflate(R.layout.transfer_detail,parent,false))
    }

    override fun getItemCount(): Int {
        return transaction.size
    }

    override fun onBindViewHolder(holder: TransferHolder, position: Int) {
        var current = transaction[position]

        var customer = controller.getAllCustomer()

        for (i in 0 until customer.size)
        {
            var map = customer[i]
            if (map["cust_id"]!!.toInt()==current["sender"]!!.toInt())
            {
                holder.sender.text = map["cust_name"]
            }
            if (map["cust_id"]!!.toInt()==current["receiver"]!!.toInt())
            {
                holder.receiver.text = map["cust_name"]
            }
        }

        holder.amount.text = current["amount"]
        holder.date.text = current["date"]
    }

    inner class TransferHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        var sender : TextView = itemView.findViewById(R.id.sender)
        var receiver : TextView = itemView.findViewById(R.id.receiver)
        var amount : TextView = itemView.findViewById(R.id.amount)
        var date : TextView = itemView.findViewById(R.id.date)
    }

}