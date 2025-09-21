package com.owino.whackamole.db
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteCursorDriver
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQuery
import android.util.Log
class DatabaseProfilingCursorFactory: SQLiteDatabase.CursorFactory {
    companion object {
        const val TAG = "DatabaseProfilingCursorFactory"
    }
    override fun newCursor(
        db: SQLiteDatabase?,
        masterQuery: SQLiteCursorDriver?,
        editTable: String?,
        query: SQLiteQuery?
    ): Cursor? {
        Log.e(TAG,"DB:Exec:${query.toString()}")
        return SQLiteCursor(masterQuery,editTable,query)
    }
}