package edu.unikom.suweorajamuhamid.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.unikom.suweorajamuhamid.data.db.entity.Barang // <-- VERIFY AND CORRECT THIS IMPORT

@Dao
interface BarangDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarang(barang: Barang)

    @Update
    suspend fun updateBarang(barang: Barang)

    @Delete
    suspend fun deleteBarang(barang: Barang)

    @Query("SELECT * FROM barang_table ORDER BY nama ASC")
    fun getAllBarang(): LiveData<List<Barang>>

    @Query("SELECT * FROM barang_table WHERE id = :barangId")
    fun getBarangById(barangId: Int): LiveData<Barang?>
}