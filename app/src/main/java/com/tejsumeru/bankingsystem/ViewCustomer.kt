package com.tejsumeru.bankingsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_customer.*


class ViewCustomer : AppCompatActivity() ,View.OnClickListener
{
    var controller = SqliteController(this,"customer_db.db",null,1)
    var customer_list = ArrayList<HashMap<String,String>>()
    var operation=""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_customer)


        var bundle = this.intent.extras
        operation = bundle!!.getString("operation").toString()

        fetchCustData(operation)

        add_customer.setOnClickListener(this)
    }

    private fun fetchCustData(operation: String?) {
        customer_list = controller.getAllCustomer()
        Log.e("Customer",customer_list.toString())

        if  (operation.equals("transfer"))
        {
            add_customer.visibility = View.GONE
        }

        var layoutManager = LinearLayoutManager(this)
        rv_cust.layoutManager = layoutManager
        var adapter = CustomerAdapter(this,customer_list,operation)
        rv_cust.adapter = adapter
    }

    override fun onClick(p0: View?)
    {
        if(p0 == add_customer)
        {
            var intent = Intent(this,AddCustActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchCustData(operation)
    }
}