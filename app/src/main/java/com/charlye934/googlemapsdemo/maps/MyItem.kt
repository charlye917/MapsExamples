package com.charlye934.googlemapsdemo.maps

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(
    private val position: LatLng,
    private val title: String,
    private val snippet: String
): ClusterItem {


    override fun getPosition(): LatLng {
        TODO("Not yet implemented")
    }

    override fun getTitle(): String? {
        TODO("Not yet implemented")
    }

    override fun getSnippet(): String? {
        TODO("Not yet implemented")
    }
}