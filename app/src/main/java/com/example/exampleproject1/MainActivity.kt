package com.example.exampleproject1

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var item : EditText
    lateinit var add : Button
    lateinit var listView : ListView

    var itemList = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.list)

        itemList = fileHelper.readData(this)


        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        listView.adapter = arrayAdapter


        add.setOnClickListener {

            val itemName : String = item.text.toString()
            itemList.add(itemName)
            item.setText("")
            fileHelper.writeData(itemList, applicationContext)
            arrayAdapter.notifyDataSetChanged()

        }

        listView.setOnItemClickListener { adapterView, view, position, l ->

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item from the list?")
            alert.setCancelable(false)

            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

                dialogInterface.cancel()

            })

            alert.setPositiveButton("yes", DialogInterface.OnClickListener { dialogInterface, i ->

                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList, applicationContext)

            })

            alert.create().show()

        }

    }
}