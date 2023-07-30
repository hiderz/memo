package com.example.memo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MemoEntity::class), version = 1)
abstract class MemoDatabase : RoomDatabase(){
    abstract  fun memoDAO() : MemoDAO

    companion object{
        var INSTANCE : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase?{
            if(INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MemoDatabase::class.java, "memo.db")
                            .fallbackToDestructiveMigration()   //version이 업그레이드되면 드랍한다는 의미
                            .build()
                }
            }
            return INSTANCE
        }
    }
}