package dev.figas.sw.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import wiki.kotlin.starwars.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}