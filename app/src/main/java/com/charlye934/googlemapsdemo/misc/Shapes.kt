package com.charlye934.googlemapsdemo.misc

import android.graphics.Color
import com.charlye934.googlemapsdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.delay

class Shapes {

    private val losAngeles = LatLng(33.923355832075785, -118.25933321818009)
    private val newYork = LatLng(40.69298455603753, -73.92340864625972)
    private val madrid = LatLng(40.46347289920206, -3.6891580432660867)

    private val p0 = LatLng(37.01877983546232, 139.13227921327797)
    private val p1 = LatLng(35.78083119105184, 138.19844137156258)
    private val p2 = LatLng(35.72287712999919, 139.82441784890233)
    private val p3 = LatLng(35.463799258361256, 138.08857809606667)


    suspend fun addPolyline(map: GoogleMap){
        val pattern = listOf(Dot(), Gap(30f), Dash(50f), Gap(30f))

        val polyline = map.addPolyline(
            PolylineOptions().apply {
                add(losAngeles, newYork, madrid)
                width(40f)
                color(Color.BLUE)
                geodesic(true)
                clickable(true)
                //pattern(pattern) // para cambiar el estilo de la linea
                jointType(JointType.ROUND)//para hacer las lineas mas grueas
                startCap(RoundCap())
                endCap(SquareCap())
            }
        )

        polyline.points = listOf<LatLng>(
            losAngeles, newYork, madrid
        )
    }

    fun addPolygon(map: GoogleMap){
        val polygon = map.addPolygon(
            PolygonOptions().apply {
                add(p0,p1,p3,p2)
                fillColor(R.color.black)
                strokeColor(R.color.black)
                //addHole(p00,p01,p02)
            }
        )
    }

    suspend fun addCircle(map: GoogleMap){
        val circle = map.addCircle(
            CircleOptions().apply {
                center(losAngeles)
                radius(5000.0)
                fillColor(R.color.purple_200)
                    .strokeColor(R.color.purple_500)
            }
        )

        delay(4000L)

        circle.fillColor = R.color.black

    }
}