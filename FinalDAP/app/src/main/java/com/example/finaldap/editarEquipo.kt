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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class editarEquipo : Fragment() {

    private lateinit var idCompartido: sharedViewModel
    private var db = Firebase.firestore

    private lateinit var equipoEditar: EditText
    private lateinit var posicionEditar: EditText
    private lateinit var linkEditar: EditText
    private lateinit var descriptionEditar: EditText
    private lateinit var NBAID: String
    private lateinit var botonEditar: Button

    private lateinit var viewModel: EditarEquipoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_equipo, container, false)

        equipoEditar = view.findViewById(R.id.equipoEditar)
        posicionEditar = view.findViewById(R.id.posicionEditar)
        linkEditar = view.findViewById(R.id.urlFoto)
        descriptionEditar = view.findViewById(R.id.descriptionNuevo)

        botonEditar = view.findViewById(R.id.botonSubirDatos)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedViewModel::class.java)
        idCompartido.data.observe(viewLifecycleOwner) { data1 ->

            db.collection("Equipos").document(data1).get().addOnSuccessListener {

                equipoEditar.setText(it.data?.get("teamName").toString())
                posicionEditar.setText(it.data?.get("teamPosition").toString())
                linkEditar.setText(it.data?.get("photo").toString())
                descriptionEditar.setText(it.data?.get("description").toString())
                NBAID = it.data?.get("idEquipo").toString()

            }.addOnFailureListener {}

            botonEditar.setOnClickListener {

                val Equipo = hashMapOf(
                    "teamName" to equipoEditar.text.toString(),
                    "teamPosition" to posicionEditar.text.toString(),
                    "photo" to linkEditar.text.toString(),
                    "description" to descriptionEditar.text.toString(),
                    "idEquipo" to NBAID
                )

                db.collection("Equipos").document(data1).set(Equipo)
                    .addOnSuccessListener {
                        Toast.makeText(context, "subido correctamente", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show()
                    }

                findNavController().navigate(R.id.action_editarEquipo_to_pantallaInicio)
            } }


        return view
    }

}