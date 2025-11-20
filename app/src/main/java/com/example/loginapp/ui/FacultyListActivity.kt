package com.example.loginapp.ui

import android.os.Bundle
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONObject
import com.google.android.material.button.MaterialButton
import com.example.loginapp.R
import com.example.loginapp.model.Faculty
import com.example.loginapp.model.Department
import com.example.loginapp.model.Course

class FacultyListActivity : AppCompatActivity() {

    private lateinit var rvFaculty: RecyclerView
    private lateinit var etFacultySearch: EditText
    private lateinit var facultyAdapter: FacultyAdapter
    private var fullList: MutableList<Faculty> = mutableListOf()
    private var currentCourseId: Int = -1
    private var currentDepartmentId: Int = -1

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

    // Faculty by course
    private val facultyByCourse = mapOf(
        1 to listOf(Faculty(1, "Dr. Jane Doe", 1, 1, "205", "2nd Floor", "Main Academic Block")),
        2 to listOf(Faculty(2, "Prof. John Smith", 1, 2, "210", "2nd Floor", "Main Academic Block")),
        3 to listOf(Faculty(3, "Dr. Emily White", 1, 3, "310", "3rd Floor", "CS Building")),
        4 to listOf(Faculty(4, "Dr. Alice Brown", 1, 4, "315", "3rd Floor", "CS Building")),
        5 to listOf(Faculty(5, "Dr. Alan T.", 2, 5, "120", "1st Floor", "EE Block")),
        6 to listOf(Faculty(6, "Prof. Grace H.", 2, 6, "415", "4th Floor", "EE Block")),
        7 to listOf(Faculty(7, "Dr. Neil P.", 3, 7, "221", "2nd Floor", "Science Block")),
        8 to listOf(Faculty(8, "Dr. Sarah Johnson", 3, 8, "225", "2nd Floor", "Science Block")),
        9 to listOf(Faculty(9, "Prof. Ada L.", 4, 9, "118", "1st Floor", "Math Block")),
        10 to listOf(Faculty(10, "Dr. Robert Kim", 4, 10, "122", "1st Floor", "Math Block")),
        11 to listOf(Faculty(11, "Dr. Marie C.", 5, 11, "230", "2nd Floor", "Chem Block")),
        12 to listOf(Faculty(12, "Prof. David Lee", 5, 12, "235", "2nd Floor", "Chem Block"))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val courseId = intent.getIntExtra("courseId", -1)
        val departmentId = intent.getIntExtra("departmentId", -1)
        currentCourseId = courseId
        currentDepartmentId = departmentId

        val department = departments.find { it.id == departmentId }
        val course = coursesByDepartment[departmentId]?.find { it.id == courseId }

        toolbar.title = "${course?.name ?: "Faculty List"}"

        rvFaculty = findViewById(R.id.rvFaculty)
        etFacultySearch = findViewById(R.id.etFacultySearch)

        fullList = (facultyByCourse[courseId] ?: emptyList()).toMutableList()
        // merge any persisted entries for this course
        loadPersistedFaculty(courseId)?.let { persisted ->
            // avoid duplicates by name
            val existingNames = fullList.map { it.name.lowercase() }.toMutableSet()
            persisted.forEach { if (!existingNames.contains(it.name.lowercase())) fullList.add(it) }
        }

        facultyAdapter = FacultyAdapter(
            faculty = fullList,
            onClick = { faculty ->
                showFacultyDialog(faculty)
            }
        )
        rvFaculty.layoutManager = LinearLayoutManager(this)
        rvFaculty.adapter = facultyAdapter

        findViewById<FloatingActionButton>(R.id.fabAddFaculty).setOnClickListener {
            showAddFacultyDialog()
        }

        etFacultySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterFacultyList(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterFacultyList(query: String) {
        if (query.isBlank()) {
            facultyAdapter.updateData(fullList)
        } else {
            facultyAdapter.updateData(fullList.filter { it.name.contains(query, ignoreCase = true) })
        }
    }

    private fun showFacultyDialog(faculty: Faculty) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_faculty_details, null, false)
        val tvTitle: TextView = view.findViewById(R.id.tvFacultyTitle)
        val tvRoom: TextView = view.findViewById(R.id.tvRoom)
        val tvFloor: TextView = view.findViewById(R.id.tvFloor)
        val tvBuilding: TextView = view.findViewById(R.id.tvBuilding)
        val btnClose: MaterialButton = view.findViewById(R.id.btnClose)
        val btnViewLocation: MaterialButton = view.findViewById(R.id.btnViewLocation)
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)

        tvTitle.text = faculty.name
        tvRoom.text = "Room No.: ${faculty.roomNo}"
        tvFloor.text = "Floor No.: ${faculty.floorNo}"
        tvBuilding.text = "Building: ${faculty.building}"

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        btnClose.setOnClickListener { dialog.dismiss() }
        btnViewLocation.setOnClickListener {
            dialog.dismiss()
            // Future enhancement: open map/location screen
        }

        dialog.show()

        btnEdit.setOnClickListener {
            dialog.dismiss()
            showEditFacultyDialog(faculty)
        }
    }

    private fun showAddFacultyDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_faculty, null, false)
        val etName: TextInputEditText = view.findViewById(R.id.etName)
        val etRoom: TextInputEditText = view.findViewById(R.id.etRoom)
        val etFloor: TextInputEditText = view.findViewById(R.id.etFloor)
        val etBuilding: TextInputEditText = view.findViewById(R.id.etBuilding)
        val tilName: TextInputLayout = view.findViewById(R.id.tilName)
        val tilRoom: TextInputLayout = view.findViewById(R.id.tilRoom)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Faculty")
            .setView(view)
            .setPositiveButton("Add", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val addBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val cancelBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            addBtn.setOnClickListener {
                tilName.error = null
                tilRoom.error = null
                val name = etName.text?.toString()?.trim().orEmpty()
                val room = etRoom.text?.toString()?.trim().orEmpty()
                val floor = etFloor.text?.toString()?.trim().orEmpty()
                val building = etBuilding.text?.toString()?.trim().orEmpty()

                var hasError = false
                if (name.isEmpty()) { tilName.error = "Name is required"; hasError = true }
                if (room.isEmpty()) { tilRoom.error = "Room No. is required"; hasError = true }
                if (fullList.any { it.name.equals(name, ignoreCase = true) }) {
                    tilName.error = "Faculty already exists"; hasError = true
                }

                if (hasError) return@setOnClickListener

                val newId = (fullList.maxOfOrNull { it.id } ?: 0) + 1
                val newFaculty = Faculty(newId, name, currentDepartmentId, currentCourseId, room, floor, building)
                fullList.add(0, newFaculty)
                facultyAdapter.updateData(fullList)
                persistFaculty(currentCourseId, fullList)
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener { dialog.dismiss() }
        }

        dialog.show()
    }

    private fun showEditFacultyDialog(faculty: Faculty) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_faculty, null, false)
        val etName: TextInputEditText = view.findViewById(R.id.etName)
        val etRoom: TextInputEditText = view.findViewById(R.id.etRoom)
        val etFloor: TextInputEditText = view.findViewById(R.id.etFloor)
        val etBuilding: TextInputEditText = view.findViewById(R.id.etBuilding)
        val tilName: TextInputLayout = view.findViewById(R.id.tilName)
        val tilRoom: TextInputLayout = view.findViewById(R.id.tilRoom)

        // Prefill
        etName.setText(faculty.name)
        etRoom.setText(faculty.roomNo)
        etFloor.setText(faculty.floorNo)
        etBuilding.setText(faculty.building)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Faculty")
            .setView(view)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val saveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val cancelBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            saveBtn.setOnClickListener {
                tilName.error = null
                tilRoom.error = null
                val name = etName.text?.toString()?.trim().orEmpty()
                val room = etRoom.text?.toString()?.trim().orEmpty()
                val floor = etFloor.text?.toString()?.trim().orEmpty()
                val building = etBuilding.text?.toString()?.trim().orEmpty()

                var hasError = false
                if (name.isEmpty()) { tilName.error = "Name is required"; hasError = true }
                if (room.isEmpty()) { tilRoom.error = "Room No. is required"; hasError = true }
                if (fullList.any { it.id != faculty.id && it.name.equals(name, ignoreCase = true) }) {
                    tilName.error = "Faculty already exists"; hasError = true
                }
                if (hasError) return@setOnClickListener

                // Update
                val index = fullList.indexOfFirst { it.id == faculty.id }
                if (index >= 0) {
                    val updated = Faculty(faculty.id, name, currentDepartmentId, currentCourseId, room, floor, building)
                    fullList[index] = updated
                    facultyAdapter.updateData(fullList)
                    persistFaculty(currentCourseId, fullList)
                }
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener { dialog.dismiss() }
        }

        dialog.show()
    }

    private fun persistFaculty(courseId: Int, list: List<Faculty>) {
        if (courseId < 0) return
        val arr = JSONArray()
        list.forEach { f ->
            val obj = JSONObject()
            obj.put("id", f.id)
            obj.put("name", f.name)
            obj.put("departmentId", f.departmentId)
            obj.put("courseId", f.courseId ?: JSONObject.NULL)
            obj.put("roomNo", f.roomNo)
            obj.put("floorNo", f.floorNo)
            obj.put("building", f.building)
            arr.put(obj)
        }
        getSharedPreferences("faculty_store", Context.MODE_PRIVATE)
            .edit()
            .putString("course_$courseId", arr.toString())
            .apply()
    }

    private fun loadPersistedFaculty(courseId: Int): List<Faculty>? {
        if (courseId < 0) return null
        val json = getSharedPreferences("faculty_store", Context.MODE_PRIVATE)
            .getString("course_$courseId", null) ?: return null
        return try {
            val arr = JSONArray(json)
            val list = mutableListOf<Faculty>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                list.add(
                    Faculty(
                        o.getInt("id"),
                        o.getString("name"),
                        o.getInt("departmentId"),
                        if (o.isNull("courseId")) null else o.getInt("courseId"),
                        o.getString("roomNo"),
                        o.getString("floorNo"),
                        o.getString("building")
                    )
                )
            }
            list
        } catch (_: Throwable) { null }
    }
}
