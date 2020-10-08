package com.akameko.testforipartner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akameko.testforipartner.R
import com.akameko.testforipartner.utils.DateUtils.convertDigitsToDate

/**
 * This fragment used to show [Note] class. Gets [Note] from [SharedViewModel.activeNote]
 */
class DetailFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var textViewDateCreated: TextView
    private lateinit var textViewDateChanged: TextView
    private lateinit var textViewNoteBody: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        textViewDateCreated = root.findViewById(R.id.text_view_note_created_details)
        textViewDateChanged = root.findViewById(R.id.text_view_note_changed_details)
        textViewNoteBody = root.findViewById(R.id.text_view_note_body_details)

        init()

        return root
    }

    private fun init() {
        val noteToShow = sharedViewModel.activeNote ?: return

        textViewDateCreated.text = String.format(getString(R.string.text_date_created), convertDigitsToDate(noteToShow.da))
        textViewDateChanged.text = String.format(getString(R.string.text_date_changed), convertDigitsToDate(noteToShow.dm))
        textViewNoteBody.text = noteToShow.body
    }
}