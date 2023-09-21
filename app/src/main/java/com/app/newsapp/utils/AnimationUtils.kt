package com.app.newsapp.utils

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

class AnimationUtils {

    companion object {
        fun showViewAnimation(view: View) {
            val valueAnimator = ValueAnimator.ofFloat(0f, 1.0f)
            valueAnimator.duration = 1000
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.start()
            valueAnimator.addUpdateListener {
                view.alpha = valueAnimator.animatedValue as Float
            }
        }
    }

}