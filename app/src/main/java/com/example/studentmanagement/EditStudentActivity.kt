package com.example.studentmanagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.studentmanagement.model.Student


class EditStudentActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_student_activity)

        val etName: EditText = findViewById(R.id.etName)
        val etMssv: EditText = findViewById(R.id.etMssv)
        val btnSave: Button = findViewById(R.id.btnUpdate)

        // Lấy dữ liệu từ Intent
        val student = intent.getParcelableExtra<Student>("student")
        student?.let {
            etName.setText(it.Name)
            etMssv.setText(it.Mssv)
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val mssv = etMssv.text.toString().trim()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                // Trả dữ liệu đã chỉnh sửa về MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("mssv", mssv)
                resultIntent.putExtra("position", intent.getIntExtra("position", -1))
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}