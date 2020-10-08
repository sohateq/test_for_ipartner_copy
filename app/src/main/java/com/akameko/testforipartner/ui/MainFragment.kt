package com.akameko.testforipartner.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akameko.testforipartner.R
import com.akameko.testforipartner.eventbus.NetworkErrorEvent
import com.akameko.testforipartner.repository.pojos.getentries.Note
import com.akameko.testforipartner.utils.Navigator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * This is [MainFragment]. It show list of notes. It is main screen.
 */
class MainFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var textViewCounter: TextView

    private var noteList = arrayListOf<Note>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initViews(root)
        setHasOptionsMenu(true)
        return root
    }

    override fun onResume() {
        //update note list every time user returns to this screen
        sharedViewModel.loadNotes()
        super.onResume()
    }

    private fun initViews(rootView: View) {
        // init recyclerView
        recyclerView = rootView.findViewById(R.id.main_recycler_view)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        // init mainAdapter
        mainAdapter = MainAdapter(noteList)
        mainAdapter.apply {
            // click listener on item
            setOnItemClickListener { noteToShow: Note ->
                sharedViewModel.setDataForDetailFragment(noteToShow)
                Navigator.navigateToDetailsFragment(this@MainFragment)
            }

            recyclerView.adapter = this
        }
        textViewCounter = rootView.findViewById(R.id.text_view_main_count)

        sharedViewModel.noteList.observe(viewLifecycleOwner, { noteList: List<Note> -> updateFragmentView(noteList) })
    }

    /**
     * Updates [recyclerView] and [textViewCounter]
     *
     * Invokes every time [SharedViewModel.noteList] updates.
     *
     * @param noteList - actual List of [Note]'s from server
     */
    private fun updateFragmentView(noteList: List<Note>) {
        //update recyclerView
        this.noteList.clear()
        this.noteList.addAll(noteList)
        mainAdapter.notifyDataSetChanged()

        //resolve mood
        if (noteList.size % 10 == 1) textViewCounter.text = String.format(getString(R.string.text_counter_1), noteList.size)
        if ((2..4).contains(noteList.size % 10)) textViewCounter.text = String.format(getString(R.string.text_counter_2_to_4), noteList.size)
        if ((5..9).contains(noteList.size % 10) || noteList.size % 10 == 0) textViewCounter.text = String.format(getString(R.string.text_counter_5_to_10), noteList.size)
    }

    /**
     * Accept [NetworkErrorEvent] if [SharedViewModel.openSession] or [SharedViewModel.loadNotes] fails.
     *
     * Shows [AlertDialog] with retry option.
     */
    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onEvent(event: NetworkErrorEvent) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context ?: return)
        builder.setMessage(R.string.text_network_error_dialog)

        if (event.cause == NetworkErrorEvent.LOAD_NOTES_ERROR) {
            builder.setPositiveButton(R.string.text_update_data) { _, _ ->
                sharedViewModel.loadNotes()
            }
        } else if (event.cause == NetworkErrorEvent.OPEN_SESSION_ERROR) {
            builder.setPositiveButton(R.string.text_update_data) { _, _ ->
                sharedViewModel.openSession()
            }
        } else return

        builder.show()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_update -> {
                Navigator.navigateToCreateNoteFragment(this@MainFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}