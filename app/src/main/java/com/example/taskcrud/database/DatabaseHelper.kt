package com.example.taskcrud.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.taskcrud.MainActivity
import com.example.taskcrud.classes.*

class DatabaseHelper(context: MainActivity) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyTaskDatabase.db"
        private const val TABLE_NAME = "MyTaskTable"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME_TASK = "nameTask"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS MyTaskTable (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nameTask TEXT
            );
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS MyTaskTable")
        onCreate(db)
    }

    fun insertData(nameTask: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_TASK, nameTask)
        }
        val newRowId = db.insert(TABLE_NAME, null, values)
        db.close()
        return newRowId
    }

    @SuppressLint("Range")
    fun readAllData(): List<TaskData> {
        val dataList = mutableListOf<TaskData>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nameTask = cursor.getString(cursor.getColumnIndex("nameTask"))
                dataList.add(TaskData(id, nameTask))
            }
        } finally {
            cursor.close()
            db.close()
        }
        return dataList
    }

    fun updateData(id: Int, nameTask: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_TASK, nameTask)
        }
        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteTask(taskId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }
}
