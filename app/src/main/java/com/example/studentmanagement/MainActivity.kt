package com.example.studentmanagement

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.adapter.StudentAdapter
import com.example.studentmanagement.model.Student


class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName: EditText = findViewById(R.id.etName)
        val etId: EditText = findViewById(R.id.etMssv)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Khởi tạo adapter và RecyclerView
        studentAdapter = StudentAdapter(studentList) { position ->
            studentList.removeAt(position)
            studentAdapter.notifyItemRemoved(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        // Xử lý sự kiện thêm sinh viên
        btnAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val id = etId.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                studentList.add(0, Student(name, id)) // Thêm vào đầu danh sách
                studentAdapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0)

                etName.text.clear()
                etId.text.clear()
            }
        }
    }
}