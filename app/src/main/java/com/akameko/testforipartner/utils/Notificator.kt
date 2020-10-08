package com.akameko.testforipartner.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akameko.testforipartner.R

/**
 * User notification helper
 */
object Notificator {

    /**
     * Inflates [R.layout.notification_layout] to given [rootView] and hides with animation from [Animator]
     *
     * @param rootView - ViewGroup to inflate notification at the bottom of.
     * @param message - text to be shown in notification
     */
    @JvmStatic
    fun showNotification(rootView: View, message: String? = "Notification") {
        rootView.context ?: return

        val cardViewNotification = LayoutInflater.from(rootView.context).inflate(R.layout.notification_layout, rootView as ViewGroup, false)
        cardViewNotification.findViewById<TextView>(R.id.card_view_notification_text_view).text = message
        rootView.addView(cardViewNotification)
        Animator.fadeInAndOut(cardViewNotification)
    }
}