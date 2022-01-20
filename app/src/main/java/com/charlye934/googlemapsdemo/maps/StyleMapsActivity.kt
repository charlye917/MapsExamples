package com.charlye934.googlemapsdemo.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope
import com.charlye934.googlemapsdemo.R

import com.charlye934.googlemapsdemo.databinding.ActivityMapsBinding
import com.charlye934.googlemapsdemo.misc.CameraAndViewPort
import com.charlye934.googlemapsdemo.misc.TypeAndStyle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.*
import java.lang.Exception

//Tema 2
class StyleMapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_types_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
            }

            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }

            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }

            R.id.black_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NONE
            }
        }
        return true
    }

    private fun setMapStyle(googleMap: GoogleMap){
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.style_map_dark
                )
            )

            if(!success){
                Log.d("Maps", "Failed to add style")
            }
        }catch (e: Exception){
            Log.d("Maps", e.toString())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val losAngeles = LatLng(33.923355832075785, -118.25933321818009)
        map.addMarker(MarkerOptions().position(losAngeles).title("Marker in Sydeney"))
        //map.moveCamera(CameraUpdateFactory.newLatLng(losAngeles))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 15f))//para mover la camara a un punto especifico con zoom
        //Para controlar los gestos, habilitarlos o desabilitarolos
        map.uiSettings.apply {
            isZoomControlsEnabled = true//para mostrar u ocultar los botones de zoom
        }

        setMapStyle(map)
    }
}