package com.owino.whackamole.view
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.owino.whackamole.ApplicationContext
import com.owino.whackamole.R
import com.owino.whackamole.model.GameGrid
import com.owino.whackamole.model.MoleAnimations
import com.owino.whackamole.model.Score
import com.owino.whackamole.tasks.FetchScoreHistoryTask
import com.owino.whackamole.tasks.RecordNewScoreTask
import java.util.Date
import java.util.Locale
import kotlin.random.Random
class GamePlayView: AppCompatActivity(){
    private lateinit var gridAdapter: GameGridAdapter
    private lateinit var scoreHistoryAdapter: ScoreHistoryAdapter
    private var grid: MutableList<GameGrid> = mutableListOf()
    private var scoreHistory: MutableList<Score> = mutableListOf()
    private lateinit var scoreTextView: TextView
    private lateinit var whackedMoleView: View
    private var levelScore: Int = 0
    private lateinit var gameLoopHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_play_view_layout)
        val recyclerView: RecyclerView = findViewById(R.id.game_grid_recycler_view)
        scoreTextView = findViewById(R.id.score_text_view)
        whackedMoleView = findViewById(R.id.whacked_mole_view)
        whackedMoleView.visibility = View.GONE
        scoreTextView.text = String.format(Locale.getDefault(),"%s %d",
            getString(R.string.general_score),levelScore)
        recyclerView.layoutManager = GridLayoutManager(applicationContext,3)
        gridAdapter = GameGridAdapter(applicationContext,grid,object: GameGridAdapter.GridClickedListener {
            override fun onGridClicked(position: Int) {
                val selected = grid[position]
                if (selected.hasMole) {
                    ++levelScore
                    scoreTextView.text =
                        String.format(Locale.getDefault(), "Score %d", levelScore)
                    MoleAnimations.fadeIn(whackedMoleView,2_000)
                    MoleAnimations.fadeOut(whackedMoleView,2_000)
                }
                updateScore(selected.hasMole)
                grid.removeAt(position)
                grid.add(position,GameGrid(selected.hasMole, tapped = true))
                gridAdapter.notifyItemChanged(position)
                reloadGrid()
            }
        })
        recyclerView.adapter = gridAdapter
        val scoreHistoryRecyclerView: RecyclerView = findViewById(R.id.score_history_recycler_view)
        scoreHistoryRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        scoreHistoryAdapter = ScoreHistoryAdapter(applicationContext, scoreHistory)
        scoreHistoryRecyclerView.adapter = scoreHistoryAdapter
        gameLoopHandler = Handler(Looper.getMainLooper())
    }
    override fun onStart() {
        super.onStart()
        reloadGrid()
        gameLoopHandler.post(gameLoopTask)
    }
    private fun reloadGrid(){
        grid.clear()
        gridAdapter.notifyDataSetChanged()
        grid.add(GameGrid(hasMole = false, tapped = false))
        grid.add(GameGrid(hasMole = false, tapped = false))
        grid.add(GameGrid(hasMole = true, tapped = false))
        grid.add(GameGrid(hasMole = false, tapped = false))
        grid.add(GameGrid(hasMole = true, tapped = false))
        grid.add(GameGrid(hasMole = false, tapped = false))
        grid.shuffle()
        gridAdapter.notifyDataSetChanged()
        loadScoreHistory()
    }
    private fun loadScoreHistory() {
        (applicationContext as ApplicationContext)
            .executorService()
            .execute(FetchScoreHistoryTask(applicationContext,object: FetchScoreHistoryTask.ScoreLoadedCallback {
                override fun scoreLoaded(scoreList: List<Score>) {
                    runOnUiThread {
                        levelScore = scoreList
                            .map { it.score }
                            .reduce { acc, value -> acc + value  }
                            .toInt()
                        scoreTextView.text =
                            String.format(Locale.getDefault(), "Score %d", levelScore)
                        scoreHistory.clear()
                        scoreHistory.addAll(scoreList)
                        scoreHistoryAdapter.notifyDataSetChanged()
                    }
                }
            }))
    }
    private val gameLoopTask = object: Runnable {
        override fun run() {
            reloadGrid()
            gameLoopHandler.postDelayed(this, Random.nextLong(1_000,5_000))
        }
    }
    private fun updateScore(hasMole: Boolean) {
        val score: Int = if (hasMole) 1 else 0
        (applicationContext as ApplicationContext)
            .executorService()
            .execute(RecordNewScoreTask(
                applicationContext,Score(Date(),score.toFloat())
            ))
    }
}