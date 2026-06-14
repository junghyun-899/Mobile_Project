package com.example.mobile_project.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobile_project.model.TravelRecord

class DBHelper(context: Context)
    : SQLiteOpenHelper(
    context,
    "travel.db",
    null,
    2
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE travel(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                place TEXT,
                date TEXT,
                memo TEXT,
                imagePath TEXT,
                latitude REAL,
                longitude REAL,
                rating REAL
            )
            """
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase, oldVersion: Int, newVersion: Int
    ) {
        db.execSQL(
            "ALTER TABLE travel ADD COLUMN rating REAL DEFAULT 0"
        )
    }
    fun insert(record: TravelRecord) {
        val values = ContentValues()
        values.put("place", record.place)
        values.put("date", record.date)
        values.put("memo", record.memo)
        values.put("imagePath", record.imagePath)
        values.put("latitude", record.latitude)
        values.put("longitude", record.longitude)
        values.put("rating", record.rating)

        writableDatabase.insert("travel", null, values)
    }

    fun getAll(): ArrayList<TravelRecord> {
        val list = ArrayList<TravelRecord>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM travel ORDER BY id DESC", null)
        while (cursor.moveToNext()) {
            list.add(
                TravelRecord(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    cursor.getDouble(6),
                    cursor.getFloat(7)
                )
            )
        }
        cursor.close()
        return list
    }
    fun delete(id: Int) {
        writableDatabase.delete(
            "travel",
            "id=?",
            arrayOf(id.toString())
        )
    }
    fun getById(id:Int): TravelRecord? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM travel WHERE id=?", arrayOf(id.toString()))
        var record: TravelRecord? = null
        if(cursor.moveToFirst()){
            record = TravelRecord(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                place = cursor.getString(cursor.getColumnIndexOrThrow("place")),
                date = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                memo = cursor.getString(cursor.getColumnIndexOrThrow("memo")),
                imagePath = cursor.getString(cursor.getColumnIndexOrThrow("imagePath")),
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
            )
        }
        cursor.close()
        return record
    }
    fun update(record: TravelRecord) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("place", record.place)
        values.put("date", record.date)
        values.put("memo", record.memo)
        values.put("imagePath", record.imagePath)
        values.put("rating", record.rating)
        db.update("travel", values, "id=?", arrayOf(record.id.toString()))
    }
    fun deleteAll() {
        writableDatabase.delete(
            "travel",
            null,
            null)
    }
}