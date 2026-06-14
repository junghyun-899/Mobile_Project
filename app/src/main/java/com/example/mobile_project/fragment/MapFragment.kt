package com.example.mobile_project.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mobile_project.R
import com.example.mobile_project.db.DBHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment :
    Fragment(R.layout.fragment_map),
    OnMapReadyCallback {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        val mapFragment =
            SupportMapFragment.newInstance()

        childFragmentManager
            .beginTransaction()
            .replace(
                R.id.mapContainer,
                mapFragment
            )
            .commit()

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        val list = DBHelper(requireContext()).getAll()
        for(record in list)
        {
            if(record.latitude != 0.0 && record.longitude != 0.0)
            {
                val location = LatLng(record.latitude, record.longitude)
                googleMap.addMarker(MarkerOptions().position(location).title(record.place))
            }
        }
        if(list.isNotEmpty())
        {
            val first = list[0]
            if(first.latitude != 0.0 && first.longitude != 0.0)
            {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(first.latitude, first.longitude), 12f))
            }
        }
    }
}