package com.example.finaldap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearEquipo : Fragment() {

    private lateinit var textEquipo: EditText
    private lateinit var textPosition: EditText
    private lateinit var textFoto: EditText
    private lateinit var textDescription: EditText
    private var db = Firebase.firestore
    private lateinit var botonCrearEquipo: Button

    private lateinit var viewModel: CrearEquipoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_equipo, container, false)

        textEquipo = view.findViewById(R.id.textoEquipo)
        textPosition = view.findViewById(R.id.textoPosition)
        textFoto = view.findViewById(R.id.textFoto)
        textDescription = view.findViewById(R.id.textDescription)
        botonCrearEquipo = view.findViewById(R.id.botonCrear)

        botonCrearEquipo.setOnClickListener {

            val documentId:String = db.collection("Equipos").document().id

            val Equipo = hashMapOf(
                "teamName" to textEquipo.text.toString(),
                "teamPosition" to textPosition.text.toString(),
                "photo" to textFoto.text.toString(),
                "description" to textDescription.text.toString(),
                "idEquipo" to documentId
            )

            db.collection("Equipos").document(documentId).set(Equipo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido correctamente", Toast.LENGTH_SHORT).show()}
                .addOnFailureListener { }

            findNavController().navigate(R.id.action_crearEquipo_to_pantallaInicio)
        }


        return view
    }

}