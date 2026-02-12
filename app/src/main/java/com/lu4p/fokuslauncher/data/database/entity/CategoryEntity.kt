package com.lu4p.fokuslauncher.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user-defined category.
 * This stores custom category names created by the user.
 */
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val name: String,
    val isCustom: Boolean = true,
    val sortOrder: Int = 0
)
