package com.example.stravadiploma.data

import com.example.stravadiploma.utils.GlobalNavigationHandler

object GlobalListener {

    private var handler: GlobalNavigationHandler? = null

    fun registerHandler(handler: GlobalNavigationHandler) {
        this.handler = handler
    }

    fun unregisterHandler() {
        handler = null
    }

    fun logout() {
        handler?.logout()
    }
}