package com.example.finaldap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class datosEquipo : Fragment() {

    private lateinit var idCompartido: sharedViewModel
    private var db = Firebase.firestore

    private lateinit var teamData: TextView
    private lateinit var positionData: TextView
    private lateinit var photoData: ImageView
    private lateinit var descriptionData: TextView

    private lateinit var viewModel: DatosEquipoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_datos_equipo, container, false)

        teamData = view.findViewById(R.id.teamData)
        positionData = view.findViewById(R.id.positionData)
        photoData = view.findViewById(R.id.photoData)
        descriptionData = view.findViewById(R.id.descriptionData)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedViewModel::class.java)
        idCompartido.data.observe(viewLifecycleOwner) { data1 ->

            db.collection("Equipos").document(data1).get().addOnSuccessListener {

                teamData.text = (it.data?.get("teamName").toString())
                positionData.text = (it.data?.get("teamPosition").toString())
                Glide.with(photoData.context).load(it.data?.get("photo").toString()).into(photoData)
                descriptionData.text = (it.data?.get("description").toString())

            }.addOnFailureListener {}
        }

        return view
    }

}