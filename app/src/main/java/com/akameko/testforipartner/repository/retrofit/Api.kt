package com.akameko.testforipartner.repository.retrofit

import com.akameko.testforipartner.repository.pojos.addentry.AddEntry
import com.akameko.testforipartner.repository.pojos.getentries.GetEntries
import com.akameko.testforipartner.repository.pojos.newsession.NewSession
import com.akameko.testforipartner.repository.pojos.newsession.NewSessionData
import io.reactivex.Single
import retrofit2.http.*

/**
 * This class provides methods for [Repository]. Do not use this methods anywhere else, except [Repository].
 *
 * Api docs: https://bnet.i-partner.ru/testAPI/
 */
interface Api {
    companion object {
        const val TOKEN = "token: sW7q3ax-cN-OJw51kF"
        const val NEW_SESSION = "new_session"
        const val GET_ENTRIES = "get_entries"
        const val ADD_ENTRY = "add_entry"
    }

    /**
     * new_session from Api. Used once after first app launch.
     * Save [NewSessionData.session] from [NewSession.data] in [SharedPreferences] e.g. and use it in following methods.
     *
     * @param newSession - only [NEW_SESSION] should be passed.
     */
    @FormUrlEncoded
    @Headers(TOKEN)
    @POST("testAPI/")
    fun openSession(@Field("a") newSession: String = NEW_SESSION): Single<NewSession>

    /**
     * get_entries from Api. Use to load Notes from server
     *
     * @param getEntries - only [GET_ENTRIES] should be passed
     * @param session - use [NewSessionData.session] only. Get it from [openSession]
     */
    @FormUrlEncoded
    @Headers(TOKEN)
    @POST("testAPI/")
    fun loadEntries(@Field("a") getEntries: String = GET_ENTRIES,
                    @Field("session") session: String): Single<GetEntries>

    /**
     * add_entry from Api. Use to upload new Note to server
     *
     * @param addEntry - only [ADD_ENTRY] should be passed
     * @param session - use [NewSessionData.session] only. Get it from [openSession]
     * @param body - text of the Note to be stored
     */
    @FormUrlEncoded
    @Headers(TOKEN)
    @POST("testAPI/")
    fun addEntry(@Field("a") addEntry: String = ADD_ENTRY,
                 @Field("session") session: String,
                 @Field("body") body: String): Single<AddEntry>
}