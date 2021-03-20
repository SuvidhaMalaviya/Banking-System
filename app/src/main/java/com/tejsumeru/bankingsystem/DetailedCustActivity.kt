package com.tejsumeru.bankingsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_detailed_cust.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DetailedCustActivity : AppCompatActivity() , View.OnClickListener{

    var controller = SqliteController(this,"customer_db.db",null,1)
    var customer_list = ArrayList<HashMap<String,String>>()
    var cust_name = ArrayList<String>()
    var map :HashMap<String,String> = HashMap<String,String>()
    var sp_index = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_cust)

        var bundle = this.intent.extras
        map = bundle!!.getSerializable("current") as HashMap<String, String>
        txt_cust_name.text = map["cust_name"]
        txt_cust_email.text = map["email"]
        txt_cust_balance.text = map["balance"]

        btn_transfer.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (p0==btn_transfer)
        {
            if (sp_linear.isVisible)
            {
                var sender_map = HashMap<String,String>()
                var rec_map = HashMap<String,String>()

                var sender = map["cust_id"]
                var receiver= sp_index[sp_transfer_to.selectedItemPosition]
                //var receiver = sp_transfer_to.selectedItem.toString().split("\t")[0]
                var amount = edt_amount.text.toString()
                var date = Calendar.getInstance().time.toString()
                var possible = false

                var map = HashMap<String,String>()
                map["sender"] = sender.toString()
                map["receiver"] = receiver
                map["amount"] = amount
                map["date"] = date

                var customers = controller.getAllCustomer()

                for (i in 0 until customers.size)
                {
                    var map = customers[i]
                    if (map["cust_id"]==receiver)
                    {
                        rec_map = map
                    }
                    if (map["cust_id"]==sender)
                    {
                        sender_map=map
                        if (map["balance"]!!.toInt() >= (amount.toInt() + 1000))
                        {
                            possible=true
                        }
                    }
                    if (amount.toInt() <= 0)
                    {
                        possible = false
                        Toast.makeText(this,"Amount can't be nagative",Toast.LENGTH_LONG).show()
                    }
                }

                Log.e("Sender",sender_map.toString())
                Log.e("receiver",rec_map.toString())

                if (possible)
                {
                    sender_map["balance"] = (sender_map["balance"]!!.toInt() - amount.toInt()).toString()
                    rec_map["balance"] = (rec_map["balance"]!!.toInt() + amount.toInt()).toString()

                    var result = controller.update(sender_map)
                    result = controller.update(rec_map)

                    if (result == -1)
                    {
                        Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show()
                    }

                    var rowInserted:Long = controller.insertTransaction(map)

                    Log.e("Result",rowInserted.toString())
                   // Toast.makeText(this,rowInserted.toString(), Toast.LENGTH_LONG).show()
                    if(rowInserted.toInt() != -1)
                    {
                        Toast.makeText(this, "Transaction Successfully Completed", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this,TransferHisActivity::class.java)
                        startActivity(intent)

                        finish()
                    }
                    else
                        Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Not Sufficient Balance\nMinimum Rs.1000 Reqired in Account", Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                edt_amount.visibility = View.VISIBLE
                sp_linear.visibility = View.VISIBLE
                customer_list =  controller.getAllCustomer()

                for (i in 0 until customer_list.size)
                {
                    var current_map : HashMap<String,String> = customer_list[i]
                    var sp_name = current_map["cust_name"].toString()
                    if (current_map["cust_name"] != map["cust_name"])
                    {
                        cust_name.add(sp_name)
                        sp_index.add(current_map["cust_id"].toString())
                    }
                }

                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.sp_item, cust_name)
                adapter.setDropDownViewResource(R.layout.sp_item)
                sp_transfer_to.adapter = adapter

                if(sp_transfer_to.count == 0)
                    btn_transfer.isEnabled = false
            }


        }

    }

}
