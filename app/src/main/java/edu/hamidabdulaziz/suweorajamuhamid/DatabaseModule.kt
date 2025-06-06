// In package: edu.unikom.suweorajamuhamid.di
package edu.unikom.suweorajamuhamid.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.unikom.suweorajamuhamid.data.db.AppDatabase
import edu.unikom.suweorajamuhamid.data.db.dao.BarangDao
import edu.unikom.suweorajamuhamid.data.db.dao.TransaksiDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Correct: Provides dependencies at the application level
object DatabaseModule {

    @Singleton // Correct: Ensures only one instance of AppDatabase
    @Provides  // Correct: Tells Hilt how to create AppDatabase
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        // This assumes AppDatabase.getDatabase(context) is correctly implemented
        // (e.g., standard Room singleton pattern) and returns a valid, non-null AppDatabase instance.
        return AppDatabase.getDatabase(context)
    }

    @Singleton // Correct: Ensures only one instance of BarangDao
    @Provides  // Correct: Tells Hilt how to create BarangDao
    fun provideBarangDao(appDatabase: AppDatabase): BarangDao {
        // This is correct. Hilt will inject the AppDatabase instance from provideAppDatabase().
        // It assumes appDatabase.barangDao() is a valid abstract method in your AppDatabase class
        // for which Room generates an implementation.
        return appDatabase.barangDao()
    }

    @Singleton // Correct: Ensures only one instance of TransaksiDao
    @Provides  // Correct: Tells Hilt how to create TransaksiDao
    fun provideTransaksiDao(appDatabase: AppDatabase): TransaksiDao {
        // Correct, similar to provideBarangDao.
        // Assumes appDatabase.transaksiDao() is a valid abstract method.
        return appDatabase.transaksiDao()
    }
}