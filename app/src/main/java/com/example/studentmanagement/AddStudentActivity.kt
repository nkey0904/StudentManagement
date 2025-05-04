package com.example.studentmanagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.studentmanagement.model.Student

class AddStudentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student_activity)

        val etName: EditText = findViewById(R.id.etName)
        val etMssv: EditText = findViewById(R.id.etMssv)
        val btnSave: Button = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val mssv = etMssv.text.toString().trim()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                // Trả dữ liệu về cho MainActivity
                val newStudent = Student(name, mssv, "", "")
                val resultIntent = Intent()
                resultIntent.putExtra("student", newStudent)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}