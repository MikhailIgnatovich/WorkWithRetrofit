package com.bulich.misha.workwithretrofit

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bulich.misha.workwithretrofit.databinding.ActivityMainBinding
import com.bulich.misha.workwithretrofit.tabs.films.CatalogFilms
import com.bulich.misha.workwithretrofit.tabs.genres.CatalogGenres

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.content, Panel()).commit()

        binding?.bottomNav?.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.panelItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Panel()).commit()
                R.id.genreCatalogBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, CatalogGenres()).commit()
                R.id.filmsCatalogBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, CatalogFilms()).commit()
            }

            return@setOnItemSelectedListener true
        }

        binding?.bottomNav?.selectedItemId = R.id.panelItemBottomNav
    }
}