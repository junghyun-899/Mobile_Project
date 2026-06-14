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
        var lat = 37.5665
        var lng = 126.9780
        for(record in list)
        {
            googleMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(record.place))
            lat += 0.02
            lng += 0.02
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.5665, 126.9780), 10f))
    }
}