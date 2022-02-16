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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener{//, GoogleMap.OnMarkerClickListener{//, GoogleMap.OnMarkerDragListener{

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val typeAndStyle by lazy { TypeAndStyle() }
    private val cameraAndViewPort by lazy { CameraAndViewPort() }

    val sixFlags = LatLng(19.29561467437547, -99.21056399950668)
    val losAngeles = LatLng(34.079566849688774, -118.39319804492614)
    val cuEstadio = LatLng(19.332230139443514, -99.19219630000893)

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
        return  true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val sixFlagsMarket =
            map.addMarker(MarkerOptions()
                    .position(sixFlags)
                    .title("Marker in sixflags")
                    //.draggable(true))//para poder mover el marcador
                    //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_android))
                    //.icon(fromVectorToBitmap(R.drawable.ic_android, Color.parseColor("#000000")))
                    //.rotation(90f)
                    .snippet("Some random text")
                    .flat(true)// para rotar el icono con la pantalla
                )
        sixFlagsMarket!!.tag = "Six Flags"

        val cuEstadioMarker =
            map.addMarker(
                MarkerOptions()
                .position(cuEstadio)
                .title("Marker Estadio")
                .zIndex(1f)
            )

        //map.addMarker(MarkerOptions().position(sixFlags).title("Marker in Sydney"))
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sixFlags, 10f))
        map.uiSettings.apply{
            isZoomControlsEnabled = true
            //isZoomGesturesEnabled = false
            //isScrollGesturesEnabled = false
        }
        typeAndStyle.setMapStyle(map, this)
        map.setOnPolylineClickListener(this)
        lifecycleScope.launch {
            //delay(4000L)
            addPolyne()

        }
        //map.setInfoWindowAdapter(CustomInfoAdapter(this)) //Para mostrar una nueva ventana al precionar el marcador
        //map.setOnMarkerClickListener(this)
        //map.setOnMarkerDragListener(this)
        //map.setOnMarkerClickListener(this)

        /*lifecycleScope.launch {
            delay(4000)
            sixFlagsMarket.remove()
        }*/

        //onMapClicked()
        //onMapLongClick()
        //mapsAnimations()
    }

    private fun addPolyne(){
        val polyline = map.addPolyline(
            PolylineOptions().apply {
                add(sixFlags, losAngeles,cuEstadio)
                width(5f)
                color(Color.BLUE)
                geodesic(true)
                clickable(true)
            }
        )

        val newList = listOf(losAngeles, cuEstadio, sixFlags)
        polyline.points = newList
    }

    private fun mapsAnimations(){
        //camera animations
        lifecycleScope.launch {
            delay(4000L)
            //map.moveCamera(CameraUpdateFactory.zoomBy(3f))
            //map.setMinZoomPreference(15f)
            //map.setMaxZoomPreference(17f)
            //map.moveCamera(CameraUpdateFactory.scrollBy(100f, 0f))
            //map.moveCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds, 0))
            //map.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null)
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles), 2000, object : GoogleMap.CancelableCallback{
                override fun onFinish() {
                    Toast.makeText(applicationContext, "finished",Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(applicationContext, "finished",Toast.LENGTH_SHORT).show()
                }
            })
            //map.animateCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds, 100), 2000, null)
            //map.setLatLngBoundsForCameraTarget(cameraAndViewPort.melbourneBounds)
        }
        //map.setPadding(0,0,0,0)
        //typeAndStyle.setMapStyle(map,applicationContext)
    }


    private fun onMapClicked(){
        map.setOnMapClickListener {
            Toast.makeText(applicationContext, "${it.latitude}, ${it.longitude}\"", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMapLongClick(){
        map.setOnMapClickListener {
            Toast.makeText(applicationContext, "${it.latitude}, ${it.longitude}", Toast.LENGTH_SHORT).show()
            map.addMarker(MarkerOptions().position(it).title("New Marker"))
        }
    }

    private fun fromVectorToBitmap(id: Int, color: Int): BitmapDescriptor{
        val vectorDrawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)
        if(vectorDrawable == null){
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onPolylineClick(p0: Polyline) {
        Toast.makeText(this, "click polygon",Toast.LENGTH_SHORT).show()
    }


    /*
    override fun onMarkerClick(marker: Marker): Boolean {
        map.animateCamera(CameraUpdateFactory.zoomTo(20f), 2000, null)
        marker?.showInfoWindow()
        return false
    }

    override fun onMarkerDragStart(p0: Marker?) {
        Log.d("Drag", "start")
    }

    override fun onMarkerDrag(p0: Marker?) {
        Log.d("Drag", "Drag")
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        Log.d("Drag", "End")
    }

    override fun onMapClick(p0: LatLng?) {

    }*/
}