package telapo.meteorwatcher.modules.newobservation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_new_observation.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.NetworkingInterface
import telapo.meteorwatcher.utility.Formater
import telapo.meteorwatcher.utility.Sensors

class NewObservationActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_observation)

    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_REQUEST_CODE)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var locationTask = fusedLocationClient.lastLocation

        locationTask?.addOnSuccessListener { location : Location? ->
            if (location != null) {
                tvLocationLongitude.text = location.longitude.toString()
                tvLocationLatitude.text = location.latitude.toString()
            } else {
                tvLocationLongitude.text = getText(R.string.strNullData)
                tvLocationLatitude.text = getText(R.string.strNullData)
            }
        }

        tvTimeLocal.text = Formater.GetDateTime(Sensors.Time.Local)
        tvTimeUtc.text = Formater.GetDateTime(Sensors.Time.Utc)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_observation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miRefresh -> {
                refresh()
                return true
            }
            R.id.miProfile -> {
                //TODO: Show Profile Settings
                return true
            }
            R.id.miSchemes -> {
                //TODO: Show Schemes
                return true
            }
            R.id.miFetch -> {
                NetworkingInterface.FetchSchemese()
                return true
            }
            R.id.miServerStatus -> {
                Toast.makeText(this, getString(R.string.strNetworkStatus) + " " + when(NetworkingInterface.GetServerStatus()) { true->getString(
                    R.string.strOnline) false->getString(R.string.strOffline)}, Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    var PERMISSION_REQUEST_CODE = 1000

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    refresh()
                } else {
                    tvLocationLongitude.text = getText(R.string.strNullData)
                    tvLocationLatitude.text = getText(R.string.strNullData)
                    Toast.makeText(this, "Location permission NOT granted.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}