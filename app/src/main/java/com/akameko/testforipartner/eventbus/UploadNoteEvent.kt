package com.akameko.testforipartner.eventbus

/**
 * This class used to send event via EventBus.
 *
 * [UploadNoteEvent] is sent when app gets result of 'add_entry' post to Api
 *
 * @param isSuccessful is a result
 */
data class UploadNoteEvent (var isSuccessful: Boolean = false)