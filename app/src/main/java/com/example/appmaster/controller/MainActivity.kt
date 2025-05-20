package com.example.appmaster.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaster.R
import com.example.appmaster.data.dao.CarroDao
import com.example.appmaster.model.Carro

class MainActivity : AppCompatActivity() {
    private lateinit var carroDao: CarroDao
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var adapter: CarroAdapter
    private lateinit var searchView: SearchView
    private var carCharsList: List<Carro> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvChars)
        emptyTextView = findViewById(R.id.tvEmpty)
        searchView = findViewById(R.id.searchView)
        carroDao = CarroDao(this)

        loadCarros()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCar = listView.getItemAtPosition(position) as Carro
            val intent = Intent(this, DetalheCarroActivity::class.java).apply {
                putExtra("carroId", selectedCar.id)
            }
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCarros(newText)
                return true
            }
        })
    }

    private fun loadCarros() {
        carCharsList = carroDao.getAllChars()
        if (carCharsList.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
        } else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
            adapter = CarroAdapter(this, carCharsList)
            listView.adapter = adapter
        }
    }

    private fun filterCarros(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            carCharsList
        } else {
            carCharsList.filter {
                it.nome.contains(query, ignoreCase = true) ||
                        it.marca.contains(query, ignoreCase = true) ||
                        it.ano.toString().contains(query)
            }
        }
        adapter = CarroAdapter(this, filteredList)
        listView.adapter = adapter
    }

    fun newChar(view: View) {
        val intent = Intent(this, NewCar::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadCarros()
    }
}