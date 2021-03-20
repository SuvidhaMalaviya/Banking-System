package com.tejsumeru.bankingsystem

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_cust.*


class AddCustActivity : AppCompatActivity() {
    var controller = SqliteController(this,"customer_db.db",null,1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cust)

        insert_cust.setOnClickListener {
            var name = edt_cust_name.text.toString().trim()
            var email = edt_cust_email.text.toString().trim()
            var balance = edt_cust_balance.text.toString().trim()

            val getResult = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

            var getEnd = email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com")

            if (name.isEmpty())
            {
                edt_cust_name.error = "Can't Be Empty"
            }
            else if(balance.isEmpty())
            {
                edt_cust_balance.error = "Required"
            }
            else if (!(getResult && getEnd))
            {
                edt_cust_email.error = "Invalid Email"
            }
            else
            {
                var map = HashMap<String,String>()
                map["cust_name"] = name
                map["email"] = email
                map["balance"] = balance

                var rowInserted:Long = controller.insertCustomer(map)

                Log.e("Result",rowInserted.toString())
                //Toast.makeText(this,rowInserted.toString(),Toast.LENGTH_LONG).show()
                if(rowInserted.toInt() != -1)
                {
                    Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                    Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}