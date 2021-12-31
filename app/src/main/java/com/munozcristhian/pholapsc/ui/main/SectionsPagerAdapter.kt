package com.munozcristhian.pholapsc.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.munozcristhian.pholapsc.R

 val TAB_TITLES = arrayOf(
    R.string.tab_text_fotos,
    R.string.tab_text_albunes,
    R.string.tab_text_sesiones
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1)
    }

    //override fun getPageTitle(position: Int): CharSequence? {
        //return context.resources.getString(TAB_TITLES[position])
    //}

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}