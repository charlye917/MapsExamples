package com.charlye934.googlemapsdemo.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

//Tema 1
class IntroducctionMapsActivity : AppCompatActivity(), OnMapReadyCallback{

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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val losAngeles = LatLng(33.923355832075785, -118.25933321818009)
        map.addMarker(MarkerOptions().position(losAngeles).title("Marker in Sydeney"))
        //map.moveCamera(CameraUpdateFactory.newLatLng(losAngeles))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 15f))//para mover la camara a un punto especifico con zoom
        //Para controlar los gestos, habilitarlos o desabilitarolos
        map.uiSettings.apply {
            isZoomControlsEnabled = true//para mostrar u ocultar los botones de zoom
            isCompassEnabled = true//muestra una brujula al rotar el mapa para que nos posiciones nuevamente como estabamos originalmente
            //isMapToolbarEnabled = true//para mostrar dos iconos que nos llevaran a google maps al precionar el marcador
            //isMyLocationButtonEnabled = true
            //isZoomGesturesEnabled = false// para activar o desactivar el zoom usando los gestos en la pantalla
            //isScrollGesturesEnabled = false//para mantener la pantalla estatica y no moverla del punto seleccionado
        }

        //Para controlar los margenes del mapa
        map.setPadding(0,0,300,0)
    }
}