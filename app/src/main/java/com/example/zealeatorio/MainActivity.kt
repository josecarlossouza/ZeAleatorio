package com.example.zealeatorio

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editInicio = findViewById<EditText>(R.id.editInicio)
        val editFim = findViewById<EditText>(R.id.editFim)
        val checkSemRepeticao = findViewById<CheckBox>(R.id.checkSemRepeticao)

        val buttonIniciar = findViewById<Button>(R.id.buttonIniciar)
        val buttonSair = findViewById<Button>(R.id.buttonSair)

        preferences = getSharedPreferences("dados_app", MODE_PRIVATE)

        val ultimoInicio = preferences.getInt("inicio", 0)
        val ultimoFim = preferences.getInt("fim", 100)
        val ultimaOpcao = preferences.getBoolean("semRepeticao", false)

        editInicio.setText(ultimoInicio.toString())
        editFim.setText(ultimoFim.toString())
        checkSemRepeticao.isChecked = ultimaOpcao

        buttonIniciar.setOnClickListener {

            val inicioTexto = editInicio.text.toString()
            val fimTexto = editFim.text.toString()

            if (inicioTexto.isEmpty() || fimTexto.isEmpty()) {
                Toast.makeText(this, "Preencha os dois campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inicio = inicioTexto.toInt()
            val fim = fimTexto.toInt()

            if (inicio > fim) {
                Toast.makeText(this, "O número inicial deve ser menor", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            preferences.edit()
                .putInt("inicio", inicio)
                .putInt("fim", fim)
                .putBoolean("semRepeticao", checkSemRepeticao.isChecked)
                .apply()

            val intent = Intent(this, ResultadoActivity::class.java)

            intent.putExtra("inicio", inicio)
            intent.putExtra("fim", fim)
            intent.putExtra("semRepeticao", checkSemRepeticao.isChecked)

            startActivity(intent)
        }

        buttonSair.setOnClickListener {
            finishAffinity()
        }
    }
}