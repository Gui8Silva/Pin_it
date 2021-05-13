package com.example.pin_it

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pin_it.API.EndPoints
import com.example.pin_it.API.Problema
import com.example.pin_it.API.ServiceBuilder

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var shared_preferences: SharedPreferences
    private lateinit var  problemas: List<Problema>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getProblemas()
        var coordenadas: LatLng
        val user_id = shared_preferences.getInt("id", 0)

        call.enqueue(object : Callback<List<Problema>> {
            override fun onResponse(call: Call<List<Problema>>, response: Response<List<Problema>>){
                if (response.isSuccessful){
                    problemas = response.body()!!
                    for (problema in problemas){
                        coordenadas = LatLng(problema.latitude.toDouble(), problema.longitude.toDouble())
                        if(problema.user_id == user_id){
                            mMap.addMarker(MarkerOptions().position(coordenadas).title(problema.id.toString()).snippet(problema.titulo + "-" + problema.descricao))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        }else{
                            mMap.addMarker(MarkerOptions().position(coordenadas).title(problema.id.toString()).snippet(problema.titulo + "-" + problema.descricao))
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Problema>>, t: Throwable){
                Toast.makeText(this@MapsActivity,"${t.message}", Toast.LENGTH_LONG).show()
            }
        })
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}