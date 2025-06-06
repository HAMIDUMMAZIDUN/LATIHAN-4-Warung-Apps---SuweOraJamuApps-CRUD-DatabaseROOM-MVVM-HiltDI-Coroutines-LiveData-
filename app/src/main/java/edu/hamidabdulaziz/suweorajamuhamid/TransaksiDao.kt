package edu.unikom.suweorajamuhamid.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.unikom.suweorajamuhamid.data.db.entity.Transaksi

@Dao
interface TransaksiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaksi(transaksi: Transaksi)

    @Update
    suspend fun updateTransaksi(transaksi: Transaksi)

    @Delete
    suspend fun deleteTransaksi(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi_table ORDER BY tanggal DESC")
    fun getAllTransaksi(): LiveData<List<Transaksi>> // Or Flow<List<Transaksi>>

    @Query("SELECT * FROM transaksi_table WHERE id = :transaksiId")
    fun getTransaksiById(transaksiId: Int): LiveData<Transaksi?> // Or Flow<Transaksi?>
}