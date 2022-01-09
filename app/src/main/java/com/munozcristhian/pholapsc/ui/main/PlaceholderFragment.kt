package com.munozcristhian.pholapsc.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import com.munozcristhian.pholapsc.AlbumFragment
import com.munozcristhian.pholapsc.PhotosFragment
import com.munozcristhian.pholapsc.R
import com.munozcristhian.pholapsc.SessionsFragment
import com.munozcristhian.pholapsc.databinding.FragmentCategoryBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): Fragment? {
            var fragment : Fragment?= null
            when (sectionNumber){
                0 -> fragment = PhotosFragment()
                1 -> fragment = AlbumFragment()
                2 -> fragment = SessionsFragment()
            }
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}