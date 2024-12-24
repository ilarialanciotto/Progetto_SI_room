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
import com.google.android.material.navigation.NavigationView
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class mappaView : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 1
    private lateinit var mapView: MapView
    private var userLocation: GeoPoint? = null // Salva la posizione dell'utente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mappa_view)
        Configuration.getInstance().userAgentValue = "ProvaLocationOSM/1.0"

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
                5000,
                10f,
                this
            )
        } else {
            Toast.makeText(this, "Permesso di localizzazione non concesso", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLocationChanged(location: Location) {
        val geoPoint = GeoPoint(location.latitude, location.longitude)
        userLocation = geoPoint // Aggiorna la posizione corrente dell'utente

        // Rimuove i marker precedenti
        mapView.overlays.clear()

        // Aggiunge il marker per la posizione dell'utente
        val marker = Marker(mapView)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Sei qui!"
        mapView.overlays.add(marker)

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
}