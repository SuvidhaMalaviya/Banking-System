package com.tejsumeru.bankingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_customer.*

class TransferHisActivity : AppCompatActivity() {
    var controller = SqliteController(this,"customer_db.db",null,1)
    var tranferDetail = ArrayList<HashMap<String,String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        fetchTransferData()
    }

    override fun onResume() {
        super.onResume()
        fetchTransferData()
    }

    private fun fetchTransferData() {
        tranferDetail = controller.getAllTransaction()
        Log.e("Customer",tranferDetail.toString())

        var layoutManager = LinearLayoutManager(this)
        rv_cust.layoutManager = layoutManager
        var adapter = TransferAdapter(this,tranferDetail)
        rv_cust.adapter = adapter
    }
}