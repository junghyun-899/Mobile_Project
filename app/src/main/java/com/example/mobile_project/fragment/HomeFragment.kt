package com.example.mobile_project.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_project.AddEditActivity
import com.example.mobile_project.adapter.TravelAdapter
import com.example.mobile_project.databinding.FragmentHomeBinding
import com.example.mobile_project.db.DBHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentHomeBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onResume() {

        super.onResume()

        val db = DBHelper(requireContext())

        val list = db.getAll()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerView.adapter =
            TravelAdapter(list) { record ->

                db.delete(record.id)

                val newList = db.getAll()

                binding.recyclerView.adapter =
                    TravelAdapter(newList) { }
            }

        binding.fabAdd.setOnClickListener {

            startActivity(
                Intent(
                    requireContext(),
                    AddEditActivity::class.java
                )
            )
        }
    }
}
