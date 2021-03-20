package com.tejsumeru.bankingsystem

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SqliteController(var context: Context,var dbName:String,factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,dbName,null,1)
{

    override fun onCreate(p0: SQLiteDatabase?)
    {
        var query = "CREATE TABLE customer(cust_id INTEGER PRIMARY KEY ,cust_name TEXT,email TEXT, balance INTEGER)"
        p0!!.execSQL(query)

        query = "CREATE TABLE transaction_hist(sr_no INTEGER PRIMARY KEY ,sender INTEGER,receiver INTEGER,amount INTEGER, date TEXT)"
        p0.execSQL(query)
        Log.e("table","Table Created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        var query = "DROP TABLE IF EXISTS customer"
        p0!!.execSQL(query)
        onCreate(p0)
    }

    fun insertCustomer(queryValues:HashMap<String, String>): Long
    {
        var id = 1
        var database :SQLiteDatabase= this.writableDatabase

        var list = getAllCustomer()
        if (list.size>0)
        {
            Log.e("list ",list.size.toString())
            var map = list[list.size-1]
            Log.e("map ",map.toString())
            id = map["cust_id"]!!.toInt()
            id++
        }
        else
            id = 1

        Log.e("iddd ",id.toString())

        var values = ContentValues()
        values.put("cust_id",id)
        values.put("cust_name", queryValues["cust_name"])
        values.put("email", queryValues["email"])
        values.put("balance", queryValues["balance"]!!.toInt())
        var result:Long=database.insert("customer", null, values)
        database.close()

        return result
    }

    fun getAllCustomer():ArrayList<HashMap<String,String>>
    {
        var customers = ArrayList<HashMap<String,String>>()

        var query = "SELECT * FROM customer"

        var database = this.writableDatabase
        var cursor = database.rawQuery(query,null)

        if (cursor.moveToNext())
        {
            do
            {
                var map = HashMap<String,String>()
                map["cust_id"] = cursor.getString(0)
                map["cust_name"] = cursor.getString(1)
                map["email"] = cursor.getString(2)
                map["balance"] = cursor.getString(3)
                customers.add(map)
            }while (cursor.moveToNext())
        }
        return customers
    }

    fun getAllTransaction():ArrayList<HashMap<String,String>>
    {
        var transaction = ArrayList<HashMap<String,String>>()

        var query = "SELECT * FROM transaction_hist"

        var database = this.writableDatabase
        var cursor = database.rawQuery(query,null)

        if (cursor.moveToNext())
        {
            do
            {
                var map = HashMap<String,String>()
                map["sr_no"] = cursor.getString(0)
                map["sender"] = cursor.getString(1)
                map["receiver"] = cursor.getString(2)
                map["amount"] = cursor.getString(3)
                map["date"] = cursor.getString(4)
                transaction.add(map)
            }while (cursor.moveToNext())
        }
        return transaction
    }

    fun update(queryValues:HashMap<String, String>): Int
    {
        val db = this.writableDatabase

        var values = ContentValues()
        values.put("cust_name", queryValues["cust_name"])
        values.put("email", queryValues["email"])
        values.put("balance", queryValues["balance"]!!.toInt())

        var result: Int =db.update("customer",values, "cust_id="+queryValues["cust_id"],null)
        return result
    }

    fun insertTransaction(queryValues:HashMap<String, String>): Long
    {
        var id = 1
        var database :SQLiteDatabase= this.writableDatabase

        var list = getAllTransaction()
        if (list.size>0)
        {
           // Log.e("list ",list.size.toString())
            var map = list[list.size-1]
            //Log.e("map ",map.toString())
            id = map["sr_no"]!!.toInt()
            id++
        }
        else
            id = 1


        var values = ContentValues()
        values.put("sr_no",id)
        values.put("sender", queryValues["sender"]!!.toInt())
        values.put("receiver", queryValues["receiver"]!!.toInt())
        values.put("amount", queryValues["amount"]!!.toInt())
        values.put("date", queryValues["date"])
        var result:Long=database.insert("transaction_hist", null, values)
        database.close()

        return result
    }


}