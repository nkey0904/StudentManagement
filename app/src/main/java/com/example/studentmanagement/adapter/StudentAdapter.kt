package com.example.studentmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.R
import com.example.studentmanagement.model.Student

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onCallClick: (Student) -> Unit,
    private val onEmailClick: (Student) -> Unit,
    private val onDeleteClick: (Student, Int) -> Unit,
    private val onEditClick: (Student, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.tvName)
        val idText: TextView = itemView.findViewById(R.id.tvMssv)
        val menuBtn: ImageView = itemView.findViewById(R.id.btnMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.nameText.text = student.Name
        holder.idText.text = student.Mssv

        holder.menuBtn.setOnClickListener { view ->
            val popup = android.widget.PopupMenu(view.context, view)
            popup.inflate(R.menu.popup_menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_call -> onCallClick(student)
                    R.id.menu_email -> onEmailClick(student)
                    R.id.menu_delete -> onDeleteClick(student, position)
                    R.id.menu_edit -> onEditClick(student, position)
                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount() = students.size
}