package com.example.appmaster.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.appmaster.data.db.DBCarro
import com.example.appmaster.model.Carro

class CarroDao(context: Context) { // Renomeando a classe para CarroDao
    private val dbHelper = DBCarro(context)

    fun getAllChars(): List<Carro> {
        val carros = mutableListOf<Carro>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBCarro.TABLE_NAME, null, null, null, null, null, null) // Usando a constante TABLE_NAME

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_ID)) // Usando constantes para colunas
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_NOME))
                val marca = cursor.getString(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_MARCA))
                val ano = cursor.getInt(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_ANO))

                carros.add(Carro(id, nome, marca, ano))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return carros
    }

    fun insert(car: Carro): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBCarro.COLUMN_NOME, car.nome) // Usando constantes para colunas
            put(DBCarro.COLUMN_MARCA, car.marca)
            put(DBCarro.COLUMN_ANO, car.ano)
        }
        val result = db.insert(DBCarro.TABLE_NAME, null, values) // Usando a constante TABLE_NAME
        db.close()
        return result
    }

    fun update(car: Carro): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBCarro.COLUMN_NOME, car.nome) // Usando constantes para colunas
            put(DBCarro.COLUMN_MARCA, car.marca)
            put(DBCarro.COLUMN_ANO, car.ano)
        }
        val result = db.update(DBCarro.TABLE_NAME, values, "${DBCarro.COLUMN_ID} = ?", arrayOf(car.id.toString())) // Usando constantes
        db.close()
        return result
    }

    fun delete(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete(DBCarro.TABLE_NAME, "${DBCarro.COLUMN_ID} = ?", arrayOf(id.toString())) // Usando constantes
        db.close()
        return result
    }

    fun getById(id: Int): Carro? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBCarro.TABLE_NAME, null, "${DBCarro.COLUMN_ID} = ?", arrayOf(id.toString()), null, null, null) // Usando constantes

        var car: Carro? = null
        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_NOME)) // Usando constantes
            val marca = cursor.getString(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_MARCA))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow(DBCarro.COLUMN_ANO))
            car = Carro(id, nome, marca, ano)
        }

        cursor.close()
        db.close()
        return car
    }
}