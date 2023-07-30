package com.example.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
class MemoEntity(
        @PrimaryKey(autoGenerate = true)    //true하면 기본적으로 1 ~ ++1로 들어감
        var id : Long?,
        var memo : String = ""
    )