package com.example.progetto_si

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class mappaView : AppCompatActivity(), LocationListener {

    private val SedeLocations = listOf(
        Pair("sede 1", GeoPoint(39.361994, 16.223653)),
        Pair("sede 2", GeoPoint(38.3473, 16.017791)),
        Pair("sede 3", GeoPoint(39.359625, 17.135454))
    )

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 1
    private lateinit var mapView: MapView
    private var userLocation: GeoPoint? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mappa_view)
        Configuration.getInstance().userAgentValue = "LocationOSM/1.0"

        mapView = findViewById(R.id.map)
        mapView.setMultiTouchControls(true)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                6000,
                6f,
                this
            )
        } else {
            Toast.makeText(this,
                getString(R.string.permesso_di_localizzazione_non_concesso), Toast.LENGTH_SHORT).show()
        }
    }

    private fun trovaSedeVicina(latitude: Double, longitude: Double): GeoPoint {

        var minLa= Double.MAX_VALUE
        var minLo= Double.MAX_VALUE
        var min= Double.MAX_VALUE
        var result = FloatArray(1)

        for (location in SedeLocations) {
            Location.distanceBetween(latitude, longitude, location.second.latitude, location.second.longitude, result)
            if(result[0].toDouble()<min){
                minLa=location.second.latitude
                minLo=location.second.longitude
                min=result[0].toDouble()
            }
        }
        return GeoPoint(minLa,minLo)
    }


    override fun onLocationChanged(location: Location) {
        val geoPoint = GeoPoint(location.latitude, location.longitude)
        userLocation = geoPoint
        val marker = Marker(mapView)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = getString(R.string.sei_qui)
        val drawable = ContextCompat.getDrawable(this, R.drawable.baseline_man_24)
        marker.icon = drawable
        mapView.overlays.clear()
        mapView.overlays.add(marker)
        var Pref = trovaSedeVicina(location.latitude,location.longitude)
        addSedeMarkers(Pref)
        mapView.controller.setCenter(geoPoint)
        mapView.controller.setZoom(13.0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    private fun addSedeMarkers(pref : GeoPoint) {
        for (location in SedeLocations) {
            val marker = Marker(mapView)
            marker.position = location.second
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            if (location.second.latitude==pref.latitude &&
                location.second.longitude==pref.longitude)
                marker.title= getString(R.string.sede_securetech_vicina)
            else
                marker.title = getString(R.string.securetech)
            val drawable = ContextCompat.getDrawable(this, R.drawable.baseline_location_pin_24)
            marker.icon = drawable
            mapView.overlays.add(marker)
        }
    }

}