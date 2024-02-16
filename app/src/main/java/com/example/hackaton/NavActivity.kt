package com.example.hackaton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.hackaton.fragment.AudioBookFragment
import com.example.hackaton.fragment.BooksFragment
import com.example.hackaton.fragment.GrammarFragment
import com.example.hackaton.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        fragmentContainer = findViewById(R.id.fragment_container)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.grammar -> {
                    replaceFragment(GrammarFragment())
                    true
                }
                R.id.audio_book -> {
                    replaceFragment(AudioBookFragment())
                    true
                }
                R.id.books -> {
                    replaceFragment(BooksFragment())
                    true
                }
                else -> false
            }
        }

        // По умолчанию отображаем фрагмент для элемента "Главная"
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}