package com.example.appmaster.controller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appmaster.R
import com.example.appmaster.data.dao.CarroDao // Usando o nome da classe corrigido
import com.example.appmaster.model.Carro

class NewCar : AppCompatActivity() {

    private lateinit var carroDao: CarroDao // Variável com 'c' minúsculo
    private var carroId: Int = 0 // Nome da variável mais claro
    private lateinit var etNome: EditText
    private lateinit var etMarca: EditText
    private lateinit var etAno: EditText
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        carroDao = CarroDao(this) // Inicialização correta do CarroDao

        etNome = findViewById(R.id.etNome)
        etMarca = findViewById(R.id.etMarca)
        etAno = findViewById(R.id.etAno)
        btnDelete = findViewById(R.id.btnDelete)

        carroId = intent.getIntExtra("carroId", 0) // Usando a chave consistente
        if (carroId != 0) {
            btnDelete.visibility = Button.VISIBLE
            editCar()
        }
    }

    fun saveChar(view: View) {
        // Seu código para salvar o carro aqui
        if (etNome.text.isNotEmpty() && etMarca.text.isNotEmpty() && etAno.text.isNotEmpty()) {
            val ano = etAno.text.toString().toIntOrNull()
            if (ano != null) {
                if (carroId == 0) {
                    val newCar = Carro(
                        nome = etNome.text.toString(),
                        marca = etMarca.text.toString(),
                        ano = ano
                    )
                    carroDao.insert(newCar)
                    Toast.makeText(this, "Carro Adicionado", Toast.LENGTH_SHORT).show()
                } else {
                    val updateCarro = Carro(
                        id = carroId,
                        nome = etNome.text.toString(),
                        marca = etMarca.text.toString(),
                        ano = ano
                    )
                    carroDao.update(updateCarro)
                    Toast.makeText(this, "Carro Atualizado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Por favor, digite um ano válido", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editCar() {
        val car = carroDao.getById(carroId) // Usando o nome correto da função do DAO
        car?.let {
            etNome.setText(it.nome)
            etMarca.setText(it.marca)
            etAno.setText(it.ano.toString())
        }
    }

    fun deleteCar(view: View) {
        carroDao.delete(carroId) // Chamando a função delete do DAO
        Toast.makeText(this, "Carro excluído com sucesso", Toast.LENGTH_SHORT).show()
        finish()
    }
}