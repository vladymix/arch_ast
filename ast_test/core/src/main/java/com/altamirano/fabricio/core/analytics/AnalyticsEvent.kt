package com.altamirano.fabricio.core.analytics

data class AnalyticsEvent(val mPackage:String, val event: String, val name:String, var isSending:Boolean)
