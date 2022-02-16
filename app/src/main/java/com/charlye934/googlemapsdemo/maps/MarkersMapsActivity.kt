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
import com.charlye934.googlemapsdemo.misc.CustomInfoAdapter
import com.charlye934.googlemapsdemo.misc.TypeAndStyle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.*
import java.lang.Exception

//Tema 4
class MarkersMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener{

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val typeAndStyle by lazy{ TypeAndStyle() }
    private val cameraAndViewPort by lazy { CameraAndViewPort() }

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
       typeAndStyle.setMapType(item, map)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val losAngeles = LatLng(33.923355832075785, -118.25933321818009)
        val losAngeles2 = LatLng(34.05771489996893, -118.55733736438894)
        val newYork = LatLng(40.69298455603753, -73.92340864625972)

        //val losAngelesMarker = map.addMarker(MarkerOptions().position(losAngeles).title("marker in los angeles"))
        val losAngelesMarker = map
            .addMarker(MarkerOptions()
                .position(losAngeles)
                .title("marker in los angeles")
                .snippet("Some random text")//si se habilita el setonmarker no se vera a menos que lo agreguemos al metodo
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_android))
                //.icon(fromVectorToBitmap(R.drawable.ic_android, Color.parseColor("#000000")))
                //.rotation(90f)
                .flat(true)//para rotar el marcador junto con el mapa
                .draggable(true))//para mover el marcador al mantenerlo precionado

        val losAngelesMarker2 = map
            .addMarker(MarkerOptions()
                .position(losAngeles2)
                .title("marker in los angeles 2")
                .zIndex(1f)
                .flat(true)//para rotar el marcador junto con el mapa
                .draggable(true))//para mover el marcador al mantenerlo precionado



        losAngelesMarker!!.tag = "Restaurant"

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f)  )//animacion para mostrar el punto establecido

        //Para controlar los gestos, habilitarlos o desabilitarolos
        map.uiSettings.apply {
            isZoomControlsEnabled = true//para mostrar u ocultar los botones de zoom
        }

        map.setInfoWindowAdapter(CustomInfoAdapter(this))
        /*lifecycleScope.launch {
            delay(4000L)
            losAngelesMarker.remove()
        }*/
        map.setOnMarkerClickListener(this)//acciones al precionar  el marcador
        map.setOnMarkerDragListener(this)
    }

    //funcion para determinar la accion al precionar el marcador
    override fun onMarkerClick(p0: Marker): Boolean {
        if(p0 != null)
            Log.d("Marker", p0.tag as String)
        else
            Log.d("Marker", "empty")
        map.animateCamera(CameraUpdateFactory.zoomTo(17f), 2000, null)
        p0?.showInfoWindow()
        return true
    }

    override fun onMarkerDrag(p0: Marker) {
        Log.d("drag", "drag $p0")
    }

    override fun onMarkerDragEnd(p0: Marker) {
        Log.d("drag", "end $p0")
    }

    override fun onMarkerDragStart(p0: Marker) {
        Log.d("drag", "start $p0")
    }
}