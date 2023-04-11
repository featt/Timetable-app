package ru.shiryaev.schedule.common.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.shiryaev.domain.utils.UtilsKeys

class NavigationActivity(
        private val context: Context
) {

    fun navigate(activityRoute: ActivityRoute, bundle: Bundle) {
        val intent = Intent().putExtra(UtilsKeys.BUNDLE.name, bundle)
        when (activityRoute) {
            is ActivityRouteCreateSchedule -> {
                intent.setClass(context, activityRoute.activityClass.activity)
            }
            is ActivityRouteEditSchedule -> {
                intent.setClass( context, activityRoute.activityClass.activity)
            }
            is ActivityRouteCreateNote -> {
                intent.setClass(context, activityRoute.activityClass.activity)
            }
            is ActivityRouteEditNote -> {
                intent.setClass(context, activityRoute.activityClass.activity)
            }
        }
        context.startActivity(intent)
    }
}