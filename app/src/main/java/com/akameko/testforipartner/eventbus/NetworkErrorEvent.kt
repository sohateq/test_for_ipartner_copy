package com.akameko.testforipartner.eventbus


/**
 * This class used to send event via EventBus
 *
 * [NetworkErrorEvent] is sent when app has problems while 'open_session' or 'get_entries' post to Api
 *
 * @param cause is used to describe reason. Choose reason from companion
 */
data class NetworkErrorEvent (val cause: String) {
    companion object {
        const val OPEN_SESSION_ERROR = "session"
        const val LOAD_NOTES_ERROR = "load_notes"
    }
}