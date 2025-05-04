package com.example.studentmanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.adapter.StudentAdapter
import com.example.studentmanagement.model.Student
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gắn Toolbar vào làm ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // RecyclerView setup
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentAdapter = StudentAdapter(studentList,
            onCallClick = { student ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${student.Phone}"))
                startActivity(intent)
            },
            onEmailClick = { student ->
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${student.Email}"))
                startActivity(intent)
            },
            onDeleteClick = { student, position ->
                showDeleteConfirmation(student, position)
            },
            onEditClick = { student, position ->
                val intent = Intent(this, EditStudentActivity::class.java).apply {
                    putExtra("student", student)
                    putExtra("position", position)
                }
                startActivityForResult(intent, REQUEST_EDIT)
            }
        )

        recyclerView.adapter = studentAdapter

        // Optional: Enabling edge-to-edge if needed (for full screen experience)
        enableEdgeToEdge()
    }

    private fun showDeleteConfirmation(student: Student, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xóa sinh viên")
            .setMessage("Bạn có chắc muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                studentList.removeAt(position)
                studentAdapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_ADD -> {
                    val newStudent = data?.getParcelableExtra("student") as? Student
                    newStudent?.let {
                        studentList.add(0, it) // Add new student to the top
                        studentAdapter.notifyItemInserted(0) // Notify adapter
                    }
                }
                REQUEST_EDIT -> {
                    val updatedStudent = data?.getParcelableExtra("student") as? Student
                    val position = data?.getIntExtra("position", -1) ?: -1
                    if (updatedStudent != null && position != -1) {
                        studentList[position] = updatedStudent
                        studentAdapter.notifyItemChanged(position)
                    }
                }
            }
        }
    }

    // Optional: Enable edge-to-edge (if applicable)
    private fun enableEdgeToEdge() {
        window?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { _, _ ->
                WindowInsetsCompat.CONSUMED
            }
        }
    }


    companion object {
        const val REQUEST_ADD = 100
        const val REQUEST_EDIT = 200
    }
}