package com.charlye934.googlemapsdemo.misc

import android.content.Context
import android.graphics.Color
import com.charlye934.googlemapsdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class Overlays {

    private val losAngeles = LatLng(33.923355832075785, -118.25933321818009)
    private val losAngelesBounds = LatLngBounds(
        LatLng(33.923355832075785, -118.25933321818009),
        LatLng(33.923355832075785, -118.25933321818009)
    )

    fun adGroundOverlay(map: GoogleMap): GroundOverlay? {

        return map.addGroundOverlay(
            GroundOverlayOptions().apply {
                //position(losAngeles, 1000f)
                positionFromBounds(losAngelesBounds)
                image(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background))//R.drawable.ic_android))
            }
        )
    }

    fun adGroundOverlayWithTag(map: GoogleMap): GroundOverlay {

        val groundOverlay = map.addGroundOverlay(
            GroundOverlayOptions().apply {
                //position(losAngeles, 1000f)
                positionFromBounds(losAngelesBounds)
                image(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background))//R.drawable.ic_android))
            }
        )

        groundOverlay!!.tag = "my tag"
        return groundOverlay
    }
}