package com.example.memo

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), OnDeleteListener {

    //lateinit = 나중에 초기화할 때 사용
    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        button_add.setOnClickListener {
            val memo = MemoEntity(null, edittext_memo.text.toString())
            insertMemo(memo)
            edittext_memo.setText("")
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    fun insertMemo(memo : MemoEntity){
        //1. MainThread vs WorkerThread(Background Thread)
        //UI관련된건 MainThread에서 이루어진다.
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                //insertTask를 실행하면 background에서 어떤걸 할건지 정함
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        insertTask.execute()
    }

    fun getAllMemos(){
        val getTask = (object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }
        }).execute()

    }

    fun deleteMemo(memo : MemoEntity){
        val deleteTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        deleteTask.execute()
    }

    fun setRecyclerView(memoList : List<MemoEntity>){
        recyclerView.adapter = MyAdapter(this, memoList, this)
    }


    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }

}