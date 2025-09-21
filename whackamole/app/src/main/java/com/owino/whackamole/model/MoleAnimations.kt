package com.owino.whackamole.model
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.owino.whackamole.tasks.ListenableFuture
import com.owino.whackamole.tasks.SettableFuture
object MoleAnimations {
    fun fadeIn(view: View, duration: Int) {
        animateIn(view, getAlphaAnimation(0f, 1f, duration))
    }
    fun fadeOut(view: View, duration: Int): ListenableFuture<Boolean> {
        return fadeOut(view, duration, View.GONE)
    }
    fun fadeOut(view: View, duration: Int, visibility: Int): ListenableFuture<Boolean> {
        return animateOut(view, getAlphaAnimation(1f, 0f, duration), visibility)
    }
    fun animateOut(
        view: View,
        animation: Animation,
        visibility: Int
    ): ListenableFuture<Boolean> {
        val future = SettableFuture<Boolean>()
        if (view.getVisibility() == visibility) {
            future.set(true)
        } else {
            view.clearAnimation()
            animation.reset()
            animation.setStartTime(0)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view.setVisibility(visibility)
                    future.set(true)
                }
            })
            view.startAnimation(animation)
        }
        return future
    }
    fun animateIn(view: View, animation: Animation) {
        if (view.getVisibility() == View.VISIBLE) return
        view.clearAnimation()
        animation.reset()
        animation.setStartTime(0)
        view.setVisibility(View.VISIBLE)
        view.startAnimation(animation)
    }
    private fun getAlphaAnimation(from: Float, to: Float, duration: Int): Animation {
        val anim: Animation = AlphaAnimation(from, to)
        anim.setInterpolator(FastOutSlowInInterpolator())
        anim.setDuration(duration.toLong())
        return anim
    }
}