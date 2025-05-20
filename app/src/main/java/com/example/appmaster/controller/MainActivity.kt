package com.example.appmaster.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaster.R
import com.example.appmaster.data.dao.CarroDao // Usando o nome da classe corrigido
import com.example.appmaster.model.Carro

class MainActivity : AppCompatActivity() {
    private lateinit var carroDao: CarroDao // Variável com 'c' minúsculo
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvChars)
        emptyTextView = findViewById(R.id.tvEmpty)
        carroDao = CarroDao(this) // Instanciando com o nome da classe correto

        listAllChars()

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedCar = parent.getItemAtPosition(position) as Carro
            val intent = Intent(this, DetalheCarroActivity::class.java).apply {
                putExtra("carroId", selectedCar.id)
            }
            startActivity(intent)
        }
    }

    private fun listAllChars() {
        val carChars = carroDao.getAllChars()
        if (carChars.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
        } else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE

            val adapter: ArrayAdapter<Carro> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                carChars
            )
            listView.adapter = adapter
        }
    }

    fun newChar(view: View) { // Função com 'n' minúsculo
        val intent = Intent(this, NewCar::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listAllChars()
    }
}