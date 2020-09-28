package com.example.conversordetemperatura

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar

const val CELSIUS = 273.15
const val SHARED_PREF_PESOS = "KELVIN"
const val VALOR = "VALOR"

class MainActivity : AppCompatActivity() {
    private lateinit var  toolbar: Toolbar
    private lateinit var  etKelvin: EditText
    private lateinit var rgTemperatura: RadioGroup
    private lateinit var rbFarenheit: RadioButton
    private lateinit var rbCelsius: RadioButton
    private lateinit var btnConvertir: Button
    private lateinit var sharedPreferences: SharedPreferences

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


        sharedPreferences = getSharedPreferences(SHARED_PREF_PESOS, Context.MODE_PRIVATE)
        val ultimoValor = sharedPreferences.getInt(VALOR, 0)
        etKelvin.setText(ultimoValor.toString())



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
            lanzarResultadoActivity(resultado)

        } else {
            MostrarMensaje("Completar todos los campos")
        }
    }

    private fun lanzarResultadoActivity(resultado: Double) {
        //Ir hacia otra activity
        val intent = Intent(this, ResultadoActivity::class.java)
        //metodo para enviar parametros
        intent.putExtra("RESULTADO", resultado)
        //Metodo para comenzar la Activity
        startActivity(intent)
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

    override fun onStop() {
        val editor = sharedPreferences.edit()
        editor.putInt(VALOR, etKelvin.text.toString().toInt())
        editor.apply()
        super.onStop()
    }
}