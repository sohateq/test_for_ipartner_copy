package com.akameko.testforipartner.utils

import android.animation.ObjectAnimator
import android.view.View

/**
 * Animation utils
 */
object Animator {

    private const val ANIMATION_DURATION = 500L

    /**
     * Smoothly shows [view] and hides it
     *
     * @param view to be shown and hide
     */
    @JvmStatic
    fun fadeInAndOut(view: View) {
        // fades view in
        ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
            duration = ANIMATION_DURATION
            start()
        }
        //fades view out after fade in complete
        ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
            duration = ANIMATION_DURATION
            startDelay = ANIMATION_DURATION * 3
            start()
        }
    }
}