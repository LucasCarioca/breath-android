package net.lucasdesouza.countmybreaths.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object BreathRecordContract {
    object BreathsRecord : BaseColumns {
        const val TABLE_NAME = "breathsRecord"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
        const val COLUMN_NAME_BREATHS = "breaths"
    }

    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${BreathsRecord.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${BreathsRecord.COLUMN_NAME_TIMESTAMP} INTEGER," +
                "${BreathsRecord.COLUMN_NAME_BREATHS} INTEGER)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${BreathsRecord.TABLE_NAME}"

    class BreathRecordDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }
        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }
        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "BreathRecord.db"
        }
    }

    fun getHelper(context: Context): BreathRecordDbHelper{
        return BreathRecordDbHelper(context)
    }

    fun helperClose(dbHelper: BreathRecordDbHelper) {
        dbHelper.close()
    }

    fun deleteAll(dbHelper: BreathRecordDbHelper) {
        val db = dbHelper.writableDatabase
        val selection = "${BreathsRecord.COLUMN_NAME_TIMESTAMP} >= ?"
        val selectionArgs = arrayOf("0")
        db.delete(BreathsRecord.TABLE_NAME, selection, selectionArgs)
    }

    fun deleteById(dbHelper: BreathRecordDbHelper, id: Long) {
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        db.delete(BreathsRecord.TABLE_NAME, selection, selectionArgs)
    }


    fun addRecord(dbHelper: BreathRecordDbHelper, timestamp: Long, breaths: Int) {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(BreathsRecord.COLUMN_NAME_TIMESTAMP, timestamp)
            put(BreathsRecord.COLUMN_NAME_BREATHS, breaths)
        }

        db?.insert(BreathsRecord.TABLE_NAME, null, values)
    }

    fun fetchAll(dbHelper: BreathRecordDbHelper): Cursor? {

        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, BreathsRecord.COLUMN_NAME_TIMESTAMP, BreathsRecord.COLUMN_NAME_BREATHS)

//        val selection = "${BreathsRecord.COLUMN_NAME_TIMESTAMP} = ?"
//        val selectionArgs = arrayOf(123124125)

// How you want the results sorted in the resulting Cursor
        val sortOrder = "${BreathsRecord.COLUMN_NAME_TIMESTAMP} DESC"

        val cursor = db.query(
            BreathsRecord.TABLE_NAME,
            projection,
            null, //selection,
            null, //selectionArgs,
            null,
            null,
            sortOrder
        )

       return cursor
    }
}