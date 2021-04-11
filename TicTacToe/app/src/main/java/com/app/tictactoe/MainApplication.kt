package com.app.tictactoe

import android.app.Application
import com.app.tictactoe.utility.helper.ApiConstants
import com.app.tictactoe.utility.helper.Constants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import java.net.URISyntaxException

/*
* Root class of the application.
* Creates and initializes socket used in the application.
* */
class MainApplication : Application() {

    companion object {
        lateinit var application: MainApplication
        lateinit var socket: Socket
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        createSocket()
    }

    /*
    * Creates and initializes socket.
    * */
    private fun createSocket() {
        socket = try {
            val option = IO.Options()
            option.transports = arrayOf(WebSocket.NAME)
            option.upgrade = false
            option.timeout = Constants.SOCKET_TIMEOUT
            IO.socket(ApiConstants.BASE_URL, option)

        } catch (e: URISyntaxException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
}
