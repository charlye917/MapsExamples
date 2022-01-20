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

//Tema 3
class CameraViewportMapsActivity : AppCompatActivity(), OnMapReadyCallback{

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
        val newYork = LatLng(40.69298455603753, -73.92340864625972)

        map.addMarker(MarkerOptions().position(losAngeles).title("Marker in Sydeney"))
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles))//para ver el mapa en 3d
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))//para ver el mapa en 3d
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f), 4000, null)//animacion para mostrar el punto establecido

        //Para controlar los gestos, habilitarlos o desabilitarolos
        map.uiSettings.apply {
            isZoomControlsEnabled = true//para mostrar u ocultar los botones de zoom
        }

        /*lifecycleScope.launch {
            delay(4000L)
            movCamera()
            animateCamera()

            //map.setLatLngBoundsForCameraTarget(cameraAndViewPort.melbourneBounds)//nos sierve para restringir  el scroll
        }*/
        //typeAndStyle.setMapStyle(map, this)
        //para modificar el maximo y el minimo que trendra el zoom
        //map.setMinZoomPreference(15f)
        //map.setMaxZoomPreference(17f)

        onMapClick()
        onMapLongClicked()
    }

    private fun movCamera(){
        map.moveCamera(CameraUpdateFactory.zoomBy(3f))//acerca mas el mapa despues de 2 segundos
        //map.moveCamera(CameraUpdateFactory.newLatLng(newYork))//nos posisiona en ny despues de unos segundos
        map.moveCamera(CameraUpdateFactory.scrollBy(100f, 0f))//mueve el mapa los pixeles que le indiquemos
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraAndViewPort.melbourneBounds.center, 10f))//posiciona en las coordenadas indicadas despues de un tiempo
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds, 100))//posiciona en las coordenadas indicadas despues de un tiempo
    }

    private fun animateCamera(){
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds, 100), 2000, null)//animacion para mover el mapa a otra posicion
        map.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null)//animacion para hacercar mas la ubicacion actual
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles), 2000, null)//animacion para mostrar en 3d el mapa
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles), 2000, object : GoogleMap.CancelableCallback{
            override fun onFinish() {
                Toast.makeText(applicationContext, "finished", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "canceled", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun onMapClick(){
        map.setOnMapClickListener {
            Toast.makeText(applicationContext, "single click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMapLongClicked(){
        map.setOnMapLongClickListener {
            map.addMarker(MarkerOptions().position(it).title("Marker in los angeles"))
            Toast.makeText(applicationContext, "${it.longitude}, ${it.latitude}", Toast.LENGTH_SHORT).show()
        }
    }
}