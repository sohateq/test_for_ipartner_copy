package com.akameko.testforipartner.repository.retrofit

import com.akameko.testforipartner.repository.pojos.addentry.AddEntry
import com.akameko.testforipartner.repository.pojos.getentries.GetEntries
import com.akameko.testforipartner.repository.pojos.newsession.NewSession
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class provides access to [Api] methods. Use this class to interact with server. It is model from MVVM-pattern
 *
 * Api docs: https://bnet.i-partner.ru/testAPI/
 *
 * Read [Api] for further information
 */
class Repository {

    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY}

    private val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://bnet.i-partner.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

    private val api: Api

    init {
        api = retrofit.create(Api::class.java)
    }

    /**
     * Use to get [currentSessionId]. Save it locally. Read [Api.openSession] for further information
     */
    fun openSession(): Single<NewSession> {
        return api.openSession()
    }

    /**
     * Use to load notes list from server. Read [Api.loadEntries] for further information
     *
     * @param sessionId is your [currentSessionId] from [openSession]
     */
    fun loadNotes(sessionId: String): Single<GetEntries> {
        return api.loadEntries(session = sessionId)
    }

    /**
     * Use to upload new note to server. Read [Api.addEntry] for further information
     *
     * @param sessionId is your [currentSessionId] from [openSession]
     * @param noteBody is text to be saved on server
     */
    fun uploadNote(sessionId: String, noteBody: String): Single<AddEntry> {
        return api.addEntry(session = sessionId, body = noteBody)
    }
}