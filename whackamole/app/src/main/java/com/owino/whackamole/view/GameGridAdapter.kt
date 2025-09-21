package com.owino.whackamole.view
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.owino.whackamole.R
import com.owino.whackamole.model.GameGrid
import com.owino.whackamole.model.MoleAnimations
class GameGridAdapter(
    val context: Context,
    val grid: List<GameGrid>,
    val listener: GridClickedListener
): RecyclerView.Adapter<GameGridAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_grid_view,parent,false)
        return ViewHolder(view,listener)
    }
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val cell: GameGrid = grid[position]
        if (cell.hasMole && cell.tapped){
            holder.moleView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.whacked_mole))
            MoleAnimations.fadeIn(holder.moleView,500)
            holder.containerView.background = ContextCompat.getDrawable(context,R.drawable.game_grid_selected_bg)
        } else if (cell.hasMole){
            holder.moleView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.mole))
            MoleAnimations.fadeIn(holder.moleView,500)
            holder.containerView.background = ContextCompat.getDrawable(context,R.drawable.game_grid_selected_bg)
            MoleAnimations.fadeOut(holder.moleView,500)
            holder.containerView.background = ContextCompat.getDrawable(context,R.drawable.game_grid_bg)
        } else {
            MoleAnimations.fadeOut(holder.moleView,500)
            holder.containerView.background = ContextCompat.getDrawable(context,R.drawable.game_grid_bg)
        }
    }
    override fun getItemCount(): Int = grid.size
    inner class ViewHolder(itemView: View, val listener: GridClickedListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val containerView: View
        val moleView: AppCompatImageView
        init {
            containerView = itemView.findViewById(R.id.item_game_grid_container)
            moleView = itemView.findViewById(R.id.item_game_grid_mole_image_view)
            containerView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listener.onGridClicked(absoluteAdapterPosition)
        }
    }
    interface GridClickedListener {
        fun onGridClicked(position: Int)
    }
}