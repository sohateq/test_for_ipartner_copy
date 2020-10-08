package com.akameko.testforipartner.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date utils
 */
object DateUtils {

    /**
     * Converts 9-digit date to readable String format.
     *
     * @param digits - digits-date to be encoded
     */
    @JvmStatic
    fun convertDigitsToDate(digits: String): String {
        return SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
                .format(Date(digits.toInt() * 1000L))
    }
}