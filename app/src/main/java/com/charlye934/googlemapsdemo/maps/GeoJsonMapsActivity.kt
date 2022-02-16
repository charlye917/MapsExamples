package com.charlye934.googlemapsdemo.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.charlye934.googlemapsdemo.R

import com.charlye934.googlemapsdemo.databinding.ActivityMapsBinding
import com.charlye934.googlemapsdemo.misc.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.data.geojson.GeoJsonLayer
import kotlinx.coroutines.*


//Tema 9
class GeoJsonMapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val typeAndStyle by lazy{ TypeAndStyle() }
    private val cameraAndViewPort by lazy { CameraAndViewPort() }
    private val overlays by lazy { Overlays() }

    private lateinit var clusterManager: ClusterManager<MyItem>

    private val locationList = listOf(
        LatLng(35.43892913607833, 132.7636534),
        LatLng(36.781855123, 138.208345 ),
        LatLng(35.43892913452, 132.76123),
        LatLng(36.781324, 138.208738345 ),
        LatLng(35.43234, 132.763654),
        LatLng(36.781234, 138.2087234 ),
        LatLng(35.438234, 132.7636456),
        LatLng(36.7243, 138.2087234 )
    )

    private val titleList = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8"
    )

    private val snippetList = listOf(
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum",
        "Lorem Ipsum"
    )

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
            isMyLocationButtonEnabled = true
        }
        //checkLocationPermission()
        typeAndStyle.setMapStyle(map, this)
        clusterManager = ClusterManager(this, map)
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
        addMarkers()
       /* val layer = GeoJsonLayer(map, R.raw.map, this)
        layer.addLayerToMap()

        val polygonStyle = layer.defaultPolygonStyle
        polygonStyle.apply {
            fillColor = ContextCompat.getColor(this@GeoJsonMapsActivity, R.color.purple_200)
        }

        layer.setOnFeatureClickListener {
            Log.d("__tag", it.getProperty("country"))
        }

        for(feature in layer.features){
            if(feature.hasProperty("country")){
                Log.d("Mapsactivity","success")
            }
        }*/

    }

    private fun addMarkers(){
        locationList.zip(titleList).zip(snippetList).forEach { pair ->
            val myItem = MyItem(pair.first.first, "Title: ${pair.first.second}", "sinepet ${pair.second}")
            clusterManager.addItem(myItem)
        }
    }

    private fun checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
            Toast.makeText(applicationContext, "Already enable", Toast.LENGTH_SHORT).show()
        }else{
            requestPermission()
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode != 1){
            return
        }
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
            map.isMyLocationEnabled = true
        }else{
            Toast.makeText(applicationContext, "We need your permission", Toast.LENGTH_SHORT).show()

        }
    }

}