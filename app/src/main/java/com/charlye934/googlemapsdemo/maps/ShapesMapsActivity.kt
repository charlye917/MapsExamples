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
import com.charlye934.googlemapsdemo.misc.Shapes
import com.charlye934.googlemapsdemo.misc.TypeAndStyle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.*
import java.lang.Exception

//Tema 5
class ShapesMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener{

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val typeAndStyle by lazy{ TypeAndStyle() }
    private val cameraAndViewPort by lazy { CameraAndViewPort() }
    private val shapes by lazy { Shapes() }

    private val losAngeles = LatLng(33.923355832075785, -118.25933321818009)

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

        val losAngelesMarker = map
            .addMarker(MarkerOptions()
                .position(losAngeles)
                .title("marker in los angeles")
                .snippet("Some random text"))

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))
        map.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        map.setOnPolylineClickListener(this)

        lifecycleScope.launch {
            shapes.addPolyline(map)
            //shapes.addPolygon(map)
            //shapes.addCircle(map)
        }

    }

    override fun onPolylineClick(p0: Polyline) {
        Toast.makeText(applicationContext, "click",Toast.LENGTH_SHORT).show()

    }
}