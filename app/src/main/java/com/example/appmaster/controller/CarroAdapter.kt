package com.example.appmaster.controller

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.appmaster.R
import com.example.appmaster.model.Carro

class CarroAdapter(context: Context, carros: List<Carro>) :
    ArrayAdapter<Carro>(context, R.layout.item_carro, carros) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_carro, parent, false)

        val carro = getItem(position)!!

        val tvNomeCarro = view.findViewById<TextView>(R.id.tvNomeCarroItem)
        val btnDetalhes = view.findViewById<Button>(R.id.btnDetalhesCarroItem)

        tvNomeCarro.text = carro.nome

        btnDetalhes.setOnClickListener {
            val intent = Intent(context, DetalheCarroActivity::class.java).apply {
                putExtra("carroId", carro.id)
            }
            context.startActivity(intent)
        }

        return view
    }
}