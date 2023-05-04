package hu.bme.aut.bethere.ui.view.network

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}