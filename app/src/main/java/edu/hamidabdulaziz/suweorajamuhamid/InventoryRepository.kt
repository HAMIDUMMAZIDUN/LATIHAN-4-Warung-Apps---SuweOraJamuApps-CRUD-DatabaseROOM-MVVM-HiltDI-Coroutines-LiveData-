// In package: edu.unikom.suweorajamuhamid.data.repository
package edu.unikom.suweorajamuhamid.data.repository

import androidx.lifecycle.LiveData
import edu.unikom.suweorajamuhamid.data.db.dao.BarangDao
import edu.unikom.suweorajamuhamid.data.db.dao.TransaksiDao // <-- IMPORT TransaksiDao
import edu.unikom.suweorajamuhamid.data.db.entity.Barang
import edu.unikom.suweorajamuhamid.data.db.entity.Transaksi // <-- IMPORT Transaksi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepository @Inject constructor(
    private val barangDao: BarangDao,
    private val transaksiDao: TransaksiDao // <-- INJECT TransaksiDao
) {

    // Barang operations
    fun getAllBarang(): LiveData<List<Barang>> = barangDao.getAllBarang()
    fun getBarangById(id: Int): LiveData<Barang?> = barangDao.getBarangById(id)
    suspend fun insertBarang(barang: Barang) { barangDao.insertBarang(barang) }
    suspend fun updateBarang(barang: Barang) { barangDao.updateBarang(barang) }
    suspend fun deleteBarang(barang: Barang) { barangDao.deleteBarang(barang) }

    // --- Transaksi Operations ---
    fun getAllTransaksi(): LiveData<List<Transaksi>> = transaksiDao.getAllTransaksi()
    fun getTransaksiById(id: Int): LiveData<Transaksi?> = transaksiDao.getTransaksiById(id)
    suspend fun insertTransaksi(transaksi: Transaksi) { transaksiDao.insertTransaksi(transaksi) }
    suspend fun updateTransaksi(transaksi: Transaksi) { transaksiDao.updateTransaksi(transaksi) }
    suspend fun deleteTransaksi(transaksi: Transaksi) { transaksiDao.deleteTransaksi(transaksi) }
}