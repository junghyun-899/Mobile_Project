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
    1
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
                longitude REAL
            )
            """
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }

    fun insert(record: TravelRecord) {

        val values = ContentValues()

        values.put("place", record.place)
        values.put("date", record.date)
        values.put("memo", record.memo)
        values.put("imagePath", record.imagePath)
        values.put("latitude", record.latitude)
        values.put("longitude", record.longitude)

        writableDatabase.insert(
            "travel",
            null,
            values
        )
    }

    fun getAll(): ArrayList<TravelRecord> {

        val list = ArrayList<TravelRecord>()

        val cursor =
            readableDatabase.rawQuery(
                "SELECT * FROM travel ORDER BY id DESC",
                null
            )

        while (cursor.moveToNext()) {

            list.add(
                TravelRecord(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    cursor.getDouble(6)
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
}