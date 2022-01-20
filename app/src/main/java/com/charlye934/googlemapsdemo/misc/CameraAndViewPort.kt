package com.charlye934.googlemapsdemo.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class CameraAndViewPort {
    val losAngeles: CameraPosition = CameraPosition.Builder()
        .target(LatLng(33.923355832075785, -118.25933321818009))
        .zoom(17f)
        .bearing(100f)
        .tilt(45f)
        .build()

    val melbourneBounds = LatLngBounds(
        LatLng(35.43892913607833, 132.7636733640477),
        LatLng(36.78185513344242, 138.20873862146118 )
    )
}