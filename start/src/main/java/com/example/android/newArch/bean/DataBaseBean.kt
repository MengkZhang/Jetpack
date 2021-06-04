package com.example.android.newArch.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moment")
data class DataBaseBean(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    val content: String?,
    val sender: String?,
    val comments: String?,
    val images: String?
) {
    override fun toString(): String {
        return super.toString()
    }
}

