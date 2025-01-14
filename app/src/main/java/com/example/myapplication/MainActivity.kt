package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    private val tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, CardSearchFragment())
                .commit()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, CardSearchFragment()).commit()

                    1 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, DeckFragment()).commit()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}