package com.example.axezziblerestaurants

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.axezziblerestaurants.databinding.ActivityMapBinding
import com.example.mapsintro.PlacesInfoAdapter
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Thread.sleep


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val REQUEST_LOCATION = 1
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    lateinit var locationProvider: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var myLocation: LatLng
    lateinit var selectedRestaurant: Restaurant

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION_KEY, POSITION_NOT_SET)
        selectedRestaurant = DataManager.restaurants[restaurantPosition]
        myLocation = LatLng(0.0,0.0)
        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(2000).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {

                    myLocation = LatLng(location.latitude,location.longitude) //Users current location
                    mMap.addMarker(MarkerOptions().position(myLocation).title("Your current location")) //Add marker on map with users current location
                    Log.d("!!!", "lat: ${location.latitude}, lng: ${location.longitude}")
                }
            }
        }

        //Check permission for location
        if ( ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        }

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        startLocationUpdates()
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val adapter = PlacesInfoAdapter(this)
        mMap.setInfoWindowAdapter(adapter)

        for(restaurant in DataManager.restaurants){
            //Set the address to contain street address, postalcode and city to get accurate positioniing
            var address = restaurant.address.plus(" ".plus(restaurant.postalCode.toString().plus(" ".plus(restaurant.city))))
            var restaurantPosition = getLocationByAddress(this,address)
            //If restaurant locaton was found then place marker
            if(restaurantPosition != null){
                val marker = mMap.addMarker(MarkerOptions().position(restaurantPosition))
                marker?.tag = restaurant
                mMap.addMarker(MarkerOptions().position(restaurantPosition).title(restaurant.name))
                if(restaurant.name == selectedRestaurant.name && restaurant.address == selectedRestaurant.address && restaurant.city == selectedRestaurant.city){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantPosition,15.0f))
                    marker?.showInfoWindow()
                }
            }


        }
        startLocationUpdates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        }
    }
    /*
    Gets the latitude and longitude by using street address
     */
    fun getLocationByAddress(context: Context, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        try {
            val address = coder.getFromLocationName(strAddress, 5) ?: return null
            val location = address.first()
            return LatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            Log.d("!!!",e.message.toString())
        }
        return null
    }
    /*
    Get location updates if the user has moved
     */
    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationProvider.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }
    //Stop recieving updates on location
    fun stopLocationUpdates() {
        locationProvider.removeLocationUpdates(locationCallback)
    }

    //Continue with location updates
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    //Temporary stop location updates
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}