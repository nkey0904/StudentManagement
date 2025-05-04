package com.example.studentmanagement.model

//import java.io.Serializable
import android.os.Parcel
import android.os.Parcelable

data class  Student(
    val Name: String,
    val Mssv: String,
    val Email: String,
    val Phone: String
 ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Mssv)
        parcel.writeString(Email)
        parcel.writeString(Phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}