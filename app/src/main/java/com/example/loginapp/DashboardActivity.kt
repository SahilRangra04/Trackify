package com.example.loginapp

import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import android.widget.EditText
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.model.Department
import com.example.loginapp.ui.CourseListActivity
import com.example.loginapp.ui.DepartmentAdapter
import com.example.loginapp.ui.GridSpacingItemDecoration
import com.example.loginapp.ui.FacultyDetailActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvDepartmentsHeader: TextView
    private lateinit var rvDepartments: RecyclerView
    private lateinit var etFacultySearch: EditText

    private lateinit var departmentAdapter: DepartmentAdapter

    private lateinit var departments: List<Department>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_24)
        toolbar.setNavigationOnClickListener { /* todo: open drawer */ }
        toolbar.inflateMenu(R.menu.menu_dashboard)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    // placeholder for search action
                    true
                }
                else -> false
            }
        }

        // Views
        tvDepartmentsHeader = findViewById(R.id.tvDepartmentsHeader)
        rvDepartments = findViewById(R.id.rvDepartments)
        etFacultySearch = findViewById(R.id.etFacultySearch)

        // Data
        seedDummyData()

        // Handle search action to open faculty detail directly
        etFacultySearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString().trim()
                performFacultySearchAndOpen(query)
                true
            } else false
        }

        // Departments grid
        departmentAdapter = DepartmentAdapter(departments) { departmentId ->
            val intent = Intent(this, CourseListActivity::class.java)
            intent.putExtra("departmentId", departmentId)
            startActivity(intent)
        }
        val spanCount = 2
        rvDepartments.layoutManager = GridLayoutManager(this, spanCount)
        val spacing = resources.displayMetrics.density * 8
        rvDepartments.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing.toInt(), true))
        rvDepartments.adapter = departmentAdapter
    }

    private fun seedDummyData() {
        departments = listOf(
            Department(1, "Department of Computer Science & IT", R.drawable.ic_dept_cs, "#FF6F61"),
            Department(2, "Department of Biotechnology", R.drawable.ic_dept_biotech, "#6B5B95"),
            Department(3, "Department of Civil Engineering", R.drawable.ic_dept_civil, "#88B04B"),
            Department(4, "Department of Design", R.drawable.ic_dept_design, "#F7CAC9"),
            Department(5, "Department of Commerce", R.drawable.ic_dept_commerce, "#92A8D1")
        )
    }

    private fun performFacultySearchAndOpen(query: String) {
        if (query.isBlank()) return
        // Minimal in-app dataset to search across all courses
        val allFaculty = listOf(
            com.example.loginapp.model.Faculty(1, "Dr. Jane Doe", 1, 1, "205", "2nd Floor", "Main Academic Block"),
            com.example.loginapp.model.Faculty(2, "Prof. John Smith", 1, 2, "210", "2nd Floor", "Main Academic Block"),
            com.example.loginapp.model.Faculty(3, "Dr. Emily White", 1, 3, "310", "3rd Floor", "CS Building"),
            com.example.loginapp.model.Faculty(4, "Dr. Alice Brown", 1, 4, "315", "3rd Floor", "CS Building"),
            com.example.loginapp.model.Faculty(5, "Dr. Alan T.", 2, 5, "120", "1st Floor", "EE Block"),
            com.example.loginapp.model.Faculty(6, "Prof. Grace H.", 2, 6, "415", "4th Floor", "EE Block"),
            com.example.loginapp.model.Faculty(7, "Dr. Neil P.", 3, 7, "221", "2nd Floor", "Science Block"),
            com.example.loginapp.model.Faculty(8, "Dr. Sarah Johnson", 3, 8, "225", "2nd Floor", "Science Block"),
            com.example.loginapp.model.Faculty(9, "Prof. Ada L.", 4, 9, "118", "1st Floor", "Math Block"),
            com.example.loginapp.model.Faculty(10, "Dr. Robert Kim", 4, 10, "122", "1st Floor", "Math Block"),
            com.example.loginapp.model.Faculty(11, "Dr. Marie C.", 5, 11, "230", "2nd Floor", "Chem Block"),
            com.example.loginapp.model.Faculty(12, "Prof. David Lee", 5, 12, "235", "2nd Floor", "Chem Block")
        )
        val match = allFaculty.firstOrNull { it.name.contains(query, ignoreCase = true) }
        if (match != null) {
            val intent = Intent(this, FacultyDetailActivity::class.java)
            intent.putExtra("name", match.name)
            intent.putExtra("room", match.roomNo)
            intent.putExtra("floor", match.floorNo)
            intent.putExtra("building", match.building)
            startActivity(intent)
            // Prevent immediate re-trigger when returning
            etFacultySearch.setText("")
            etFacultySearch.clearFocus()
            getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(etFacultySearch.windowToken, 0)
        }
    }
}


