package edu.unikom.suweorajamuhamid.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.unikom.suweorajamuhamid.data.db.entity.Barang // Corrected import
@Entity(tableName = "barang_table")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val harga: Double,
    val gambarUri: String? = null // To store URI of the image from gallery
)