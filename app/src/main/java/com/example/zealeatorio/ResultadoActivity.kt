package com.example.zealeatorio

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class ResultadoActivity : AppCompatActivity() {

    private lateinit var textNumero: TextView
    private lateinit var quadradoCor: LinearLayout

    private var inicio = 0
    private var fim = 0
    private var semRepeticao = false

    private val numerosGerados = mutableSetOf<Int>()

    private val cores = listOf(
        "#1B5E20",
        "#0D47A1",
        "#4A148C",
        "#BF360C",
        "#263238"
    )

    private var indiceCor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        textNumero = findViewById(R.id.textNumero)
        quadradoCor = findViewById(R.id.quadradoCor)

        val buttonVoltar = findViewById<Button>(R.id.buttonVoltar)

        inicio = intent.getIntExtra("inicio", 0)
        fim = intent.getIntExtra("fim", 100)
        semRepeticao = intent.getBooleanExtra("semRepeticao", false)

        gerarNumero()

        textNumero.setOnClickListener {
            gerarNumero()
        }

        buttonVoltar.setOnClickListener {
            finish()
        }
    }

    private fun gerarNumero() {

        val totalPossibilidades = (fim - inicio) + 1

        if (semRepeticao && numerosGerados.size >= totalPossibilidades) {
            Toast.makeText(
                this,
                "Todas as possibilidades de número dentro do range já foram geradas. A partir de agora haverão repetições.",
                Toast.LENGTH_LONG
            ).show()
            numerosGerados.clear()
        }

        var numero: Int
        do {
            numero = Random.nextInt(inicio, fim + 1)
        } while (semRepeticao && numerosGerados.contains(numero))

        numerosGerados.add(numero)
        textNumero.text = numero.toString()

        quadradoCor.setBackgroundColor(Color.parseColor(cores[indiceCor]))

        indiceCor++
        if (indiceCor >= cores.size) {
            indiceCor = 0
        }
    }
}