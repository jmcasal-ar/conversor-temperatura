package com.example.conversordetemperatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar

const val CELSIUS = 273.15
const val FARENHEIT = (CELSIUS*(9/5))+32


const val SHARED_PREF_PESOS = "PESOS"
const val POSICION = "POSICION"

class MainActivity : AppCompatActivity() {
    private lateinit var  toolbar: Toolbar
    private lateinit var  etKelvin: EditText
    private lateinit var rgTemperatura: RadioGroup
    private lateinit var rbFarenheit: RadioButton
    private lateinit var rbCelsius: RadioButton
    private lateinit var btnConvertir: Button
    private lateinit var txtResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }

    private fun setupUI() {
        setupToolbar()
        etKelvin = findViewById(R.id.etKelvin)
        rgTemperatura = findViewById(R.id.rgTemperatura)
        rbFarenheit = findViewById(R.id.rbFarenheit)
        rbCelsius = findViewById(R.id.rbCelsius)
        btnConvertir = findViewById(R.id.btnConvertir)
        txtResultado = findViewById(R.id.txtResultado)

        btnConvertir.setOnClickListener { realizarConversion() }


        }




    private fun realizarConversion() {
        //validamos que no este vacío
        if (etKelvin.text.isNotEmpty()) {
            val resultado = when (obtenerRadioButtonSeleccionado()) {
                R.id.rbFarenheit -> convertirAFarenheit(etKelvin.text.toString().toDouble())
                R.id.rbCelsius -> convertirACelsius(etKelvin.text.toString().toDouble())
                else -> convertirACelsius(etKelvin.text.toString().toDouble())
            }
            txtResultado.text = resultado.toString()
        } else {
            MostrarMensaje("Completar todos los campos")
        }
    }
    private fun obtenerRadioButtonSeleccionado(): Int {
        return  rgTemperatura.checkedRadioButtonId
    }

    private fun convertirAFarenheit(etKelvin: Double) = (etKelvin - CELSIUS)*1.8+32

    private fun convertirACelsius(etKelvin: Double) = etKelvin - CELSIUS

private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Conversor de Temperatura"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemAyuda -> MostrarMensaje("En caso de ayuda, comunicase con ISTEA")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}