package com.owino.whackamole.tasks
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.owino.whackamole.db.GamePlayDatabaseContract
import com.owino.whackamole.db.GamePlayDatabaseHelper
import com.owino.whackamole.model.Score
class RecordNewScoreTask(val context: Context, val score: Score): Runnable {
    companion object {
        const val TAG = "RecordNewScoreTask"
    }
    override fun run() {
        val dbHelper = GamePlayDatabaseHelper(context)
        val writeDb = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(GamePlayDatabaseContract.ScoreTableContract.TIMESTAMP_COLUMN,score.timestamp.time)
            put(GamePlayDatabaseContract.ScoreTableContract.SCORE_COLUMN,score.score)
        }
        if (writeDb.isDatabaseIntegrityOk &&
            !writeDb.isDbLockedByCurrentThread
            && writeDb.isOpen) {
            val rowId = writeDb.insert(
                GamePlayDatabaseContract.ScoreTableContract.TABLE_NAME,
                null,
                values
            )
            Log.e(TAG,"Inserted row $rowId")
        }
        writeDb.close()
    }
}