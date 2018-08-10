package com.example.nandi.roomsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nandi.roomsample.db.AppDb
import com.example.nandi.roomsample.model.Data
import com.example.nandi.roomsample.rv.BaseAdapter
import com.example.nandi.roomsample.rv.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.people.view.*

class MainActivity : AppCompatActivity() {
    lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data = Data()

        rvPeople.setHasFixedSize(true)
        rvPeople.layoutManager = LinearLayoutManager(this)
        rvPeople.adapter = adapter
        getDataFromDb()

        btnSend.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                data.name = etName.text.toString()
                val dataStream = Observable.just(data)
                dataStream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            data ->
                            AppDb.getInstance(this@MainActivity).getDao().insert(data)
                            Toast.makeText(this@MainActivity, "Data added", Toast.LENGTH_SHORT).show()
                            clearEditText()
                            getDataFromDb()
                        }, {
                            error -> Toast.makeText(this@MainActivity, "Cannot insert data", Toast.LENGTH_SHORT).show()
                        })
            }
        })
    }

    fun clearEditText() {
        etName.setText("")
    }

    private fun getDataFromDb() {
        val datas = Observable.fromCallable<List<Data>> { AppDb.getInstance(this@MainActivity).getDao().getAll() }
        datas.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data -> adapter.setData(data)
                })
    }

    var adapter = object : BaseAdapter<Data, BaseViewHolder<Data>>() {
        override fun getItemResourceLayout(viewType: Int) = R.layout.people

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Data> =
                getViewHolder(getView(parent, viewType))
    }

    fun getViewHolder(view: View): BaseViewHolder<Data> {
        return object : BaseViewHolder<Data>(view){
            override fun onBind(data: Data) {
                view.tvName.text = data.name
                view.ivDelete.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        val dataStream = Observable.just(data)
                        dataStream
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    data ->
                                    AppDb.getInstance(this@MainActivity).getDao().delete(data)
                                    Toast.makeText(this@MainActivity, "Data deleted", Toast.LENGTH_SHORT).show()
                                    getDataFromDb()
                                }, {
                                    error -> Toast.makeText(this@MainActivity, "Cannot delete data", Toast.LENGTH_SHORT).show()
                                })
                    }
                })
            }

        }
    }
}