package com.example.googlefitsteps.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.googlefitsteps.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StepsFragment.newInstance())
                .commitNow()
        }
    }
}