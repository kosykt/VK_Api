package com.example.vk_api.di.module.singletones

//import android.app.Application
//import androidx.room.Room
//import com.example.data.database.AppDatabase
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//private const val DB_NAME = "database.db"
//
//@Module
//class RoomModule {
//
//    @Singleton
//    @Provides
//    fun provideRoomDatabase(app: Application): AppDatabase {
//        return Room.databaseBuilder<AppDatabase?>(
//            app.applicationContext,
//            AppDatabase::class.java,
//            DB_NAME
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//}