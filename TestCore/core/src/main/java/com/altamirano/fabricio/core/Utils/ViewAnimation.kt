package com.altamirano.fabricio.core.Utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.Transformation

object ViewAnimation {

    @JvmOverloads
    fun fadeOut(view: View, animListener: AnimListener? = null) {
        view.alpha = 1.0f
        view.visibility = View.VISIBLE
        view.animate().setDuration(500).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animator: Animator) {
                animListener?.onFinish()
                super.onAnimationEnd(animator)
            }
        }).alpha(0f)
    }

    @JvmOverloads
    fun fadeIn(view: View, animListener: AnimListener? = null) {
        view.alpha = 0.0f
        view.visibility = View.VISIBLE
        view.animate().setDuration(500).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animator: Animator) {
                animListener?.onFinish()
                super.onAnimationEnd(animator)
            }
        }).alpha(1.0f)
    }

    fun expand(view: View) {
        view.startAnimation(expandAction(view))
    }

    fun collapse(view: View) {
        view.startAnimation(collapseAcction(view))
    }

    private fun expandAction(view: View): Animation {
        view.measure(-1, -2)
        val measuredHeight = view.measuredHeight
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE
        val c08302: Animation = object : Animation() {
            override fun willChangeBounds(): Boolean {
                return true
            }

            override fun applyTransformation(f: Float, transformation: Transformation) {
                view.layoutParams.height =
                    if (f == 1.0f) measuredHeight else (measuredHeight.toFloat() * f).toInt()
                view.requestLayout()
            }
        }
        c08302.duration =
            (measuredHeight.toFloat() / view.context.resources.displayMetrics.density).toInt()
                .toLong()
        // view.startAnimation(c08302);
        return c08302
    }

    private fun collapseAcction(view: View): Animation {
        val measuredHeight = view.measuredHeight
        val c08313: Animation = object : Animation() {
            override fun willChangeBounds(): Boolean {
                return true
            }

            override fun applyTransformation(f: Float, transformation: Transformation) {
                if (f == 1.0f) {
                    view.visibility = View.GONE
                    return
                }
                view.layoutParams.height = measuredHeight - (measuredHeight.toFloat() * f).toInt()
                view.requestLayout()
            }
        }
        c08313.duration =
            (measuredHeight.toFloat() / view.context.resources.displayMetrics.density).toInt()
                .toLong()
        //view.startAnimation(c08313);
        return c08313
    }

    fun showIn(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0f
        view.translationY = view.height.toFloat()
        view.animate().setDuration(200).translationY(0.0f).setListener(AnimatorAdp()).alpha(1.0f)
            .start()
    }

    fun showOut(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 1.0f
        view.translationY = 0.0f
        view.animate().setDuration(200).translationY(view.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animator: Animator) {
                    view.visibility = View.GONE
                    super.onAnimationEnd(animator)
                }
            }).alpha(0.0f).start()
    }

    fun expand(view: View, animListener: AnimListener) {
        val expandAction = expandAction(view)
        expandAction.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                animListener.onFinish()
            }
        })
        view.startAnimation(expandAction)
    }

    fun changeBackgroundColor(view: View, color: Int) {
        val animator = ValueAnimator()
        animator.duration = 300
        animator.setEvaluator(ArgbEvaluator())
        animator.setIntValues(Color.WHITE, color)
        animator.addUpdateListener { animation -> view.setBackgroundColor(animation.animatedValue as Int) }
        animator.start()
    }

    interface AnimListener {
        fun onFinish()
    }

    /* renamed from: com.material.components.utils.ViewAnimation$8 */
    internal class AnimatorAdp : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animator: Animator) {
            super.onAnimationEnd(animator)
        }
    }
}