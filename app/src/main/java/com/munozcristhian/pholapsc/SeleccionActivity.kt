package com.munozcristhian.pholapsc

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.munozcristhian.pholapsc.databinding.SeleccionImpresionBinding

class SeleccionActivity : AppCompatActivity() {
    private lateinit var binding: SeleccionImpresionBinding
    private lateinit var checkBoxes: MutableList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seleccion_impresion)
        binding = SeleccionImpresionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgViewBackImpresion.setOnClickListener{
            //val intention = Intent(this, HomeFotosActivity::class.java)
            //startActivity(intention)
        }


        binding.imgViewCheck.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Solicitud de Impresión")
            dialogBuilder.setMessage("Sus fotografías serán entregadas en máximo 3 días laborables")
            dialogBuilder.setPositiveButton("Confirmar", DialogInterface.OnClickListener { _, _ ->
                startActivity(intention)
                Toast.makeText(this, "Solicitud de impresión realizada con éxito.", Toast.LENGTH_LONG).show()
            })
            dialogBuilder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                startActivity(intention)
            //pass
            })
            dialogBuilder.create().show()

        }

        binding.imgViewBackImpresion.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            startActivity(intention)
        }

    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                null -> {
                    if (checked) {
                        view.toggle()
                    } else {
                        view.toggle()
                    }
                }
            }
        }
    }
}