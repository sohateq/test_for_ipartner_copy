package com.akameko.testforipartner.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.akameko.testforipartner.R

/**
 * Screen navigation helper
 */
object Navigator {

    /**
     * Navigates to [DetailFragment] from [MainFragment]
     *
     * @param fragment to get havController from
     */
    fun navigateToDetailsFragment(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }

    /**
     * Navigates to [CreateNoteFragment] from [MainFragment]
     *
     * @param fragment to get havController from
     */
    fun navigateToCreateNoteFragment(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.action_mainFragment_to_createNoteFragment)
    }

    /**
     * PopBackStack from view
     *
     * @param view to popBackStack from
     */
    fun backPressed(view: View) {
        view.findNavController().popBackStack()
    }
}