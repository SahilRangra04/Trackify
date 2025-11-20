package com.example.loginapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.Course
import com.example.loginapp.model.Department

class CourseListActivity : AppCompatActivity() {

    private lateinit var rvCourses: RecyclerView
    private lateinit var courseAdapter: CourseAdapter

    private val departments = listOf(
        Department(1, "Department of Computer Science & IT", R.drawable.ic_person, "#FF6F61"),
        Department(2, "Department of Biotechnology", R.drawable.ic_person, "#6B5B95"),
        Department(3, "Department of Civil Engineering", R.drawable.ic_person, "#88B04B"),
        Department(4, "Department of Design", R.drawable.ic_person, "#F7CAC9"),
        Department(5, "Department of Commerce", R.drawable.ic_person, "#92A8D1")
    )

    private val coursesByDepartment = mapOf(
        1 to listOf(
            Course(1, "B.Tech", 1),
            Course(2, "M.Tech", 1),
            Course(3, "BCA", 1),
            Course(4, "MCA", 1)
        ),
        2 to listOf(
            Course(5, "B.Sc in Genetics", 2),
            Course(6, "M.Sc in Genetics", 2)
        ),
        3 to listOf(
            Course(7, "B.Tech in Civil Engineering", 3),
            Course(8, "M.Tech in Civil Engineering", 3)
        ),
        4 to listOf(
            Course(9, "B.Des Product", 4),
            Course(10, "BA in Fashion Design", 4)
        ),
        5 to listOf(
            Course(11, "B.Com", 5),
            Course(12, "M.Com", 5)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Get selected department ID
        val departmentId = intent.getIntExtra("departmentId", -1)
        val department = departments.find { it.id == departmentId }
        toolbar.title = "${department?.name ?: "Courses"}"

        rvCourses = findViewById(R.id.rvCourses)

        val courses = coursesByDepartment[departmentId] ?: emptyList()

        courseAdapter = CourseAdapter(courses) { course ->
            // Navigate to FacultyListActivity with course ID
            val facultyIntent = Intent(this, FacultyListActivity::class.java)
            facultyIntent.putExtra("courseId", course.id)
            facultyIntent.putExtra("departmentId", departmentId)
            startActivity(facultyIntent)
        }
        rvCourses.layoutManager = LinearLayoutManager(this)
        rvCourses.adapter = courseAdapter
    }
}






