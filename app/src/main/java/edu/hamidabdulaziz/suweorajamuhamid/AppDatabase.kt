// In package: edu.unikom.suweorajamuhamid.data.db
package edu.unikom.suweorajamuhamid.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.unikom.suweorajamuhamid.data.db.dao.BarangDao
import edu.unikom.suweorajamuhamid.data.db.dao.TransaksiDao
import edu.unikom.suweorajamuhamid.data.db.entity.Barang
import edu.unikom.suweorajamuhamid.data.db.entity.Transaksi

@Database(
    entities = [Barang::class, Transaksi::class],
    version = 2, // Ensure this is incremented if you changed schema from a previous version
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun barangDao(): BarangDao
    abstract fun transaksiDao(): TransaksiDao

    companion object {
        @Volatile // Ensures that the value of INSTANCE is always up-to-date and the same to all execution threads.
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // If the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) { // synchronized block to ensure only one thread can execute this at a time
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "suwe_ora_jamu_database" // Your database name
                )
                    // IMPORTANT: If you've changed the database schema (e.g., added Transaksi table),
                    // you need a migration strategy or allow destructive migration for development.
                    .fallbackToDestructiveMigration() // For development. For production, implement proper migrations.
                    .build()
                INSTANCE = instance
                // return instance
                instance // Return the newly created instance
            }
        }
    }
}