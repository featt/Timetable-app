package ru.shiryaev.schedule.common.navigation

sealed class ActivityRoute

data class ActivityRouteCreateSchedule(val activityClass: ActivityClass) : ActivityRoute()
data class ActivityRouteEditSchedule(val activityClass: ActivityClass) : ActivityRoute()

data class ActivityRouteCreateNote(val activityClass: ActivityClass) : ActivityRoute()
data class ActivityRouteEditNote(val activityClass: ActivityClass) : ActivityRoute()
