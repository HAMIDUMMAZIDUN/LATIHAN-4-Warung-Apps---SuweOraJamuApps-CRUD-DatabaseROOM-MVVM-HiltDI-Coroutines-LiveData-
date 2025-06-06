package edu.unikom.suweorajamuhamid.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaksi_table",
    foreignKeys = [ForeignKey(
        entity = Barang::class, // Assuming your item entity is Barang
        parentColumns = ["id"],
        childColumns = ["barangId"],
        onDelete = ForeignKey.CASCADE // Or another action like SET_NULL or RESTRICT
    )]
)
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val barangId: Int,          // Foreign key referencing Barang
    val jumlah: Int,            // Quantity of the item
    val tanggal: Long,          // Timestamp of the transaction (e.g., System.currentTimeMillis())
    val hargaSatuanSaatTransaksi: Double, // Price of one item at the time of transaction
    val totalHarga: Double      // Total price for this transaction entry (jumlah * hargaSatuanSaatTransaksi)
)