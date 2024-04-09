package com.example.taskcrud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskcrud.database.DatabaseHelper
import android.widget.EditText
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcrud.adapter.*
import com.example.taskcrud.classes.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHelper(this)

        val editTextTask = findViewById<EditText>(R.id.editTextTask)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTask)
        val recyclerViewTasks = findViewById<RecyclerView>(R.id.recyclerViewTasks)

        recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        // Suponha que você tenha uma lista de TaskData chamada dataList
        var dataList = dbHelper.readAllData()

        // Crie uma instância do adaptador e defina-o na RecyclerView
        var adapter = MyAdapter(dataList, this)
        recyclerViewTasks.adapter = adapter

        buttonAddTask.setOnClickListener {
            val taskName = editTextTask.text.toString()
            if (taskName.isNotBlank()) {
                dbHelper.insertData(taskName)
                editTextTask.setText("")

                dataList = dbHelper.readAllData()
                adapter = MyAdapter(dataList, this)
                recyclerViewTasks.adapter = adapter
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // Do nothing
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}

