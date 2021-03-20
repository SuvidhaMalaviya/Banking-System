package com.tejsumeru.bankingsystem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter(internal var context: Context, internal var customer: ArrayList<HashMap<String, String>>, internal var operation: String?) : RecyclerView.Adapter<CustomerAdapter.CustomerHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerHolder {
        return CustomerHolder(LayoutInflater.from(context).inflate(R.layout.customer_main,parent,false))
    }

    override fun getItemCount(): Int {
        return customer.size
    }

    override fun onBindViewHolder(holder: CustomerHolder, position: Int) {

        if (operation.equals("view"))
            holder.btn_transfer.visibility = View.GONE
        else if (operation.equals("transfer"))
        {
            holder.btn_transfer.visibility = View.VISIBLE
        }

        var current = customer[position]
        holder.txt_name.text = "Name :: "+current["cust_name"]
        holder.txt_bal.text = "Balance :: "+current["balance"]

        holder.cust_main.setOnClickListener {

            var bundle = Bundle()
            bundle.putSerializable("current",current)
            var intent = Intent(context,DetailedCustActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

        holder.btn_transfer.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("current",current)
            var intent = Intent(context,DetailedCustActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    inner class CustomerHolder(itemView :View) : RecyclerView.ViewHolder(itemView)
    {
        var txt_name :TextView = itemView.findViewById(R.id.customer_name)
        var cust_main :LinearLayout= itemView.findViewById(R.id.cust_main)
        var txt_bal :TextView = itemView.findViewById(R.id.customer_bal)
        var btn_transfer : Button = itemView.findViewById(R.id.btn_transfer)
    }
}