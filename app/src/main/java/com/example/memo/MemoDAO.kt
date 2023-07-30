package com.example.memo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)   //만약 Insert 할 때 중복 값이 있으면 덮어씌우기 기능함
    fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    fun getAll() :  List<MemoEntity>

    @Delete
    fun delete(memo : MemoEntity)
}