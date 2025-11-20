package com.example.loginapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.loginapp.R

class FacultyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val name = intent.getStringExtra("name") ?: "Faculty"
        val room = intent.getStringExtra("room") ?: "-"
        val floor = intent.getStringExtra("floor") ?: "-"
        val building = intent.getStringExtra("building") ?: "-"

        supportActionBar?.title = name

        findViewById<TextView>(R.id.tvRoom).text = "Room No.: $room"
        findViewById<TextView>(R.id.tvFloor).text = "Floor No.: $floor"
        findViewById<TextView>(R.id.tvBuilding).text = "Building: $building"
    }
}


