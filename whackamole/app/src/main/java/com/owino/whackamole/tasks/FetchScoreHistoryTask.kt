package com.owino.whackamole.tasks
import android.content.Context
import com.owino.whackamole.db.GamePlayDatabaseContract
import com.owino.whackamole.db.GamePlayDatabaseHelper
import com.owino.whackamole.model.Score
import java.util.Date
class FetchScoreHistoryTask(
    val context: Context,
    val callback: ScoreLoadedCallback
): Runnable {
    override fun run() {
        val scoreList: MutableList<Score> = mutableListOf()
        val dbHelper = GamePlayDatabaseHelper(context)
        val readDb = dbHelper.readableDatabase
        val columns = arrayOf(
            GamePlayDatabaseContract.ScoreTableContract.SCORE_COLUMN,
            GamePlayDatabaseContract.ScoreTableContract.TIMESTAMP_COLUMN
        )
        val tableName = GamePlayDatabaseContract.ScoreTableContract.TABLE_NAME
        val sortOrder = "${GamePlayDatabaseContract.ScoreTableContract.TIMESTAMP_COLUMN} DESC"
        val cursor = readDb.query(
            tableName,
            columns,
            null,
            null,
            null,
            null,
            sortOrder
        )
        while (cursor.moveToNext()){
            val scoreColumn = cursor.getColumnIndex(GamePlayDatabaseContract.ScoreTableContract.SCORE_COLUMN)
            val timestampColumn = cursor.getColumnIndex(GamePlayDatabaseContract.ScoreTableContract.TIMESTAMP_COLUMN)
            val score = cursor.getFloat(scoreColumn)
            val timestamp = cursor.getLong(timestampColumn)
            val date = Date()
            date.time = timestamp
            val scoreModel = Score(date,score)
            scoreList.add(scoreModel)
        }
        readDb.close()
        callback.scoreLoaded(scoreList)
    }
    interface ScoreLoadedCallback {
        fun scoreLoaded(scoreList:List<Score>)
    }
}