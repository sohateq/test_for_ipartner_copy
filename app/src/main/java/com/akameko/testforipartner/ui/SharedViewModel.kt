package com.akameko.testforipartner.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akameko.testforipartner.dagger.App
import com.akameko.testforipartner.eventbus.NetworkErrorEvent
import com.akameko.testforipartner.eventbus.UploadNoteEvent
import com.akameko.testforipartner.repository.pojos.addentry.AddEntry
import com.akameko.testforipartner.repository.pojos.getentries.GetEntries
import com.akameko.testforipartner.repository.pojos.getentries.Note
import com.akameko.testforipartner.repository.pojos.newsession.NewSession
import com.akameko.testforipartner.repository.retrofit.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * [ViewModel] used for [MainFragment], [DetailFragment] and [CreateNoteFragment].
 *
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "SharedViewModel"
        const val SETTINGS = "settings"
        const val SESSION = "session"
    }

    @Inject
    lateinit var repository: Repository

    private val preferences = application.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    var currentSessionId: String? = null

    var noteList = MutableLiveData<List<Note>>()
    var activeNote: Note? = null //Note chosen in MainFragment

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        App.component.injectSharedViewModel(this)

        //check sessionID, if empty get new
        currentSessionId = preferences.getString(SESSION, null)
        currentSessionId  ?: openSession()
    }

    /**
     * Used to get new sessionId from server asynchronously.
     *
     * In case of error posts [NetworkErrorEvent]
     */
    fun openSession() {
        val disposable = repository.openSession()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: NewSession ->
                    if (result.status != 1) {
                        Log.d(TAG, "Open session failed, status is not 1")
                        postOpenSessionErrorEvent()
                        return@subscribe
                    }

                    if (result.data.session == null) {
                        Log.d(TAG, "Open session failed, session id is null")
                        postOpenSessionErrorEvent()
                        return@subscribe
                    }

                    // set session id
                    preferences.edit().putString(SESSION, result.data.session).apply()
                    currentSessionId = result.data.session
                    Log.d(TAG, "New session started!!")
                    loadNotes()
                })
                {
                    throwable: Throwable? ->
                    Log.d(TAG, "Open session failed", throwable)
                    postOpenSessionErrorEvent()
                }

        compositeDisposable.add(disposable)
    }

    /**
     * Used to get notes list from server asynchronously.
     *
     * In case of error posts [NetworkErrorEvent]
     */
    fun loadNotes() {
        val disposable = repository.loadNotes(currentSessionId ?: return)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: GetEntries ->
                    if (result.status != 1) {
                        Log.d(TAG, "Load notes failed, status is not 1")
                        postLoadErrorEvent()
                        return@subscribe
                    }

                    noteList.value = result.data?.get(0)
                    Log.d(TAG, "Notes loaded!!")
                })
                {
                    throwable: Throwable? ->
                    Log.d(TAG, "Load notes failed", throwable)
                    postLoadErrorEvent()
                }

        compositeDisposable.add(disposable)
    }


    /**
     * Used to upload new note to server asynchronously.
     *
     * Posts [UploadNoteEvent] as a result.
     */
    fun uploadNote(body: String) {
        val disposable = repository.uploadNote(currentSessionId ?: return, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: AddEntry ->
                    if (result.status != 1) {
                        Log.d(TAG, "Upload note failed, status is not 1")
                        postUploadEvent(false)
                        return@subscribe
                    }

                    if (result.data?.id == null) {
                        Log.d(TAG, "Upload note failed, id is absent")
                        postUploadEvent(false)
                        return@subscribe
                    }

                    postUploadEvent(true)
                    Log.d(TAG, "Note uploaded!!")

                })
                { throwable: Throwable? ->
                    Log.d(TAG, "Upload notes failed", throwable)
                    postUploadEvent(false)
                }

        compositeDisposable.add(disposable)
    }

    /**
     * Prepares this [SharedViewModel] for [DetailFragment]. Should be used before navigate to [DetailFragment]
     *
     * @param noteToShow is [Note] to be shown
     */
    fun setDataForDetailFragment(noteToShow: Note) {
        activeNote = noteToShow
    }

    private fun postUploadEvent(status: Boolean) {
        EventBus.getDefault().post(UploadNoteEvent(status))
    }

    private fun postLoadErrorEvent() {
        EventBus.getDefault().post(NetworkErrorEvent(NetworkErrorEvent.LOAD_NOTES_ERROR))
    }

    private fun postOpenSessionErrorEvent() {
        EventBus.getDefault().post(NetworkErrorEvent(NetworkErrorEvent.OPEN_SESSION_ERROR))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}