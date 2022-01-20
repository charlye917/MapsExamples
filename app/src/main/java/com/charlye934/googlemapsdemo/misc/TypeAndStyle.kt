package com.charlye934.googlemapsdemo.misc

import android.content.Context
import android.util.Log
import android.view.MenuItem
import com.charlye934.googlemapsdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import java.lang.Exception

class TypeAndStyle {
    fun setMapStyle(googleMap: GoogleMap, context: Context){
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.style_map_dark
                )
            )
            if(!success)
                Log.d("__tag","failed to add style")
        }catch (e: Exception){
            Log.d("__tag","${e.message}")
        }
    }

    fun setMapType(item: MenuItem, map: GoogleMap){
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
    }
}