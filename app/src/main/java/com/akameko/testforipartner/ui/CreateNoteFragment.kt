package com.akameko.testforipartner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akameko.testforipartner.R
import com.akameko.testforipartner.eventbus.UploadNoteEvent
import com.akameko.testforipartner.utils.Navigator
import com.akameko.testforipartner.utils.Notificator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * This fragment used for creating new notes and post it on server.
 * Uses [SharedViewModel].
 */
class CreateNoteFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var container: View //layout main viewGroup
    private lateinit var editTextNote: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_note, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initViews(root)
        return root
    }

    private fun initViews(rootView: View) {
        container = rootView.findViewById(R.id.fragment_create_note_container)
        editTextNote = rootView.findViewById(R.id.edit_text_create_note_text)

        //init cancel button
        rootView.findViewById<Button>(R.id.button_cancel).apply {
            setOnClickListener {
                Navigator.backPressed(it)
            }
        }

        //init create note button
        rootView.findViewById<Button>(R.id.button_create_note).apply {
            setOnClickListener {
                if (editTextNote.text.toString() == "") {
                    Notificator.showNotification(container, "Введите текст")
                    return@setOnClickListener
                }

                sharedViewModel.uploadNote(editTextNote.text.toString())
            }
        }
    }

    /**
     * Receiving result of [sharedViewModel.uploadNote]
     */
    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onEvent(event: UploadNoteEvent) {
        if (event.isSuccessful) {
            editTextNote.setText("")
            Notificator.showNotification(container, "Заметка создана успешно!")
        } else {
           Notificator.showNotification(container, "Ошибка!")
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}