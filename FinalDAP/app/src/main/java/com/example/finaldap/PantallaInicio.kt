package com.example.finaldap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PantallaInicio : Fragment() {

    private lateinit var viewModel: PantallaInicioViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var NBAlist: ArrayList<EquipoNBA>
    private var db = Firebase.firestore
    private lateinit var botonAgregar: Button
    private lateinit var idNBA: String
    private lateinit var idViewModel: sharedViewModel

    private lateinit var adapter : RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        db = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById(R.id.recyclerViewTeams)
        recyclerView.layoutManager = LinearLayoutManager(context)
        NBAlist = arrayListOf()
        botonAgregar = view.findViewById(R.id.botonAdd)

        initRecyclerView()

        botonAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicio_to_crearEquipo)
        }

        return view
    }

    private fun initRecyclerView() {
        db.collection("Equipos").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val equipoRecycler:EquipoNBA? = data.toObject<EquipoNBA>(EquipoNBA::class.java)
                    NBAlist.add(equipoRecycler!!)
                }

                adapter = RecyclerAdapter(NBAlist,
                    onDeleteClick = {position -> deleteTeam(position)
                    },
                    onEditClick = {position -> editTeam(position)
                    },
                    onItemClick = {position -> seeTeams(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun seeTeams(position:Int) {

      idNBA = NBAlist[position].idEquipo.toString()

        idViewModel = ViewModelProvider(requireActivity()).get(sharedViewModel::class.java)
        idViewModel.data.value = idNBA

        findNavController().navigate(R.id.action_pantallaInicio_to_datosEquipo)

    }

    fun editTeam(position: Int) {
        idNBA = NBAlist[position].idEquipo.toString()

        idViewModel = ViewModelProvider(requireActivity()).get(sharedViewModel::class.java)
        idViewModel.data.value = idNBA

        findNavController().navigate(R.id.action_pantallaInicio_to_editarEquipo)
    }

    fun deleteTeam (position : Int){

        db.collection("Equipos").document(NBAlist[position].idEquipo.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Equipo Eliminado", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                NBAlist.removeAt(position)
            }
            .addOnFailureListener {}
    }


}