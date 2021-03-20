package com.tejsumeru.bankingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView()
    {
        view_customer.setOnClickListener(this)
        transfer_money.setOnClickListener(this)
        transfer_history.setOnClickListener(this)
    }

    override fun onClick(p0: View?)
    {
        if (p0 == view_customer)
        {
            var bundle = Bundle()
            bundle.putString("operation","view")
            var intent = Intent(this,ViewCustomer::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        else if (p0 == transfer_money)
        {
            var bundle = Bundle()
            bundle.putString("operation","transfer")
            var intent = Intent(this,ViewCustomer::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        else if (p0 == transfer_history)
        {
            var intent = Intent(this,TransferHisActivity::class.java)
            startActivity(intent)
        }
    }
}