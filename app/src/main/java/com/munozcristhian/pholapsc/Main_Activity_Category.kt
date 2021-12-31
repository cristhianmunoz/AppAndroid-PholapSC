package com.munozcristhian.pholapsc

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.munozcristhian.pholapsc.ui.main.SectionsPagerAdapter
import com.munozcristhian.pholapsc.databinding.ActivityMainCategoryBinding

class Main_Activity_Category : AppCompatActivity() {


    private lateinit var binding: ActivityMainCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayoutPrincipal
        tabs.setupWithViewPager(viewPager)
        //val fab: FloatingActionsMenu = binding.floatingActionsMenu

        //fab.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                ///.setAction("Action", null).show()

        //}

    }

}