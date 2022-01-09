package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.munozcristhian.pholapsc.ui.main.SectionsPagerAdapter
import com.munozcristhian.pholapsc.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        binding.floatingActionButtonConfiguraciones.setOnClickListener(){
            val intent = Intent(this, ConfiguracionesActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.floatingActionButtonAgendarSesion.setOnClickListener(){
            val intent = Intent(this, SessionActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.floatingActionButtonCerrarSesion.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.floatingActionButtonImprimir.setOnClickListener(){
            val intent = Intent(this, SeleccionActivity::class.java).also {
                startActivity(it)
            }
        }



    }
}