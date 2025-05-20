package com.example.appmaster.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaster.R
import com.example.appmaster.data.dao.CarroDao
import com.example.appmaster.model.Carro

class DetalheCarroActivity : AppCompatActivity() {

    private lateinit var carroDao: CarroDao
    private var carroId: Int = 0
    private lateinit var tvNome: TextView
    private lateinit var tvMarca: TextView
    private lateinit var tvAno: TextView
    private lateinit var btnEditar: Button
    private lateinit var btnExcluir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_carro)

        carroDao = CarroDao(this)

        tvNome = findViewById(R.id.tvDetalheNome)
        tvMarca = findViewById(R.id.tvDetalheMarca)
        tvAno = findViewById(R.id.tvDetalheAno)
        btnEditar = findViewById(R.id.btnEditarCarro)
        btnExcluir = findViewById(R.id.btnExcluirCarro)

        carroId = intent.getIntExtra("carroId", 0)

        if (carroId != 0) {
            loadCarroDetalhes()
        } else {
            Toast.makeText(this, "ID do carro inválido", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this, NewCar::class.java).apply {
                putExtra("carroId", carroId)
            }
            startActivity(intent)
        }

        btnExcluir.setOnClickListener {
            deleteCarro()
        }
    }

    private fun loadCarroDetalhes() {
        val carro = carroDao.getById(carroId)
        carro?.let {
            tvNome.text = it.nome
            tvMarca.text = it.marca
            tvAno.text = it.ano.toString()
        } ?: run {
            Toast.makeText(this, "Carro não encontrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun deleteCarro() {
        carroDao.delete(carroId)
        Toast.makeText(this, "Carro excluído com sucesso", Toast.LENGTH_SHORT).show()
        finish() // Volta para a MainActivity após a exclusão
    }

    override fun onResume() {
        super.onResume()
        if (carroId != 0) {
            loadCarroDetalhes() // Recarrega os detalhes se algo foi editado
        }
    }
}