package com.owino.whackamole.db
import android.provider.BaseColumns
class GamePlayDatabaseContract {
    object ScoreTableContract: BaseColumns {
        const val TABLE_NAME = "scores"
        const val TIMESTAMP_COLUMN: String = "Score_time"
        const val SCORE_COLUMN: String = "Score"
    }
    companion object QUERIES {
        const val DATABASE_NAME = "game_play_db"
        const val DB_VERSION = 1
        const val CREATE_SCORES_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS ${ScoreTableContract.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${ScoreTableContract.TIMESTAMP_COLUMN} FLOAT NOT NULL,
                ${ScoreTableContract.SCORE_COLUMN} INTEGER
            )
        """
    }
}