package com.owino.whackamole.db
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.owino.whackamole.BuildConfig
import java.lang.AssertionError
class GamePlayDatabaseHelper(context: Context):
    SQLiteOpenHelper(context,
    GamePlayDatabaseContract.DATABASE_NAME,
        if (BuildConfig.DEBUG) DatabaseProfilingCursorFactory() else null,
    GamePlayDatabaseContract.DB_VERSION) {
        companion object {
            const val TAG = "GamePlayDatabaseHelper"
        }
    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) throw AssertionError("Null database")
        db.execSQL(GamePlayDatabaseContract.CREATE_SCORES_TABLE_QUERY)
    }
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        Log.e(TAG,"Upgrading database from $oldVersion to $newVersion")
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }
}