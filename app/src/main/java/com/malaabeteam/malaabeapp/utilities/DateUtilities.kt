package com.malaabeteam.malaabeapp.utilities

import android.content.Context
import com.malaabeteam.malaabeapp.R
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

object DateUtilities {
  fun getTimeAgoString(date: LocalDateTime, context: Context): String {
    val now = LocalDateTime.now(ZoneId.of("UTC"))
    check(date.isBefore(now)) { "Given date must be in the past." }

    val duration = Duration.between(date, now)
    val days = duration.toDays().toInt()
    val months = days / 30

    return when {
      months >= 12 -> context.getString(R.string.textTimeOneYearAgo)
      months >= 1 -> context.resources.getQuantityString(R.plurals.textTimeMonthsAgo, months, months)
      days >= 7 -> {
        val weeks = days / 7
        context.resources.getQuantityString(R.plurals.textTimeWeeksAgo, weeks, weeks)
      }
      days >= 1 -> context.resources.getQuantityString(R.plurals.textTimeDaysAgo, days, days)
      else -> {
        val hours = duration.toHours().toInt()
        when {
          hours > 0 -> context.resources.getQuantityString(R.plurals.textTimeHoursAgo, hours, hours)
          else -> {
            val minutes = duration.toMinutes().toInt()
            when {
              minutes > 0 -> context.resources.getQuantityString(R.plurals.textTimeMinutesAgo, minutes, minutes)
              else -> {
                val seconds = duration.seconds.toInt()
                context.resources.getQuantityString(R.plurals.textTimeSecondsAgo, seconds, seconds)
              }
            }
          }
        }
      }
    }
  }
}
