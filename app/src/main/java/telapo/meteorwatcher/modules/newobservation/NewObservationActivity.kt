package telapo.meteorwatcher.modules.newobservation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_new_observation.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.NetworkingInterface
import telapo.meteorwatcher.dal.model.*
import telapo.meteorwatcher.modules.profile.ProfileFragment
import telapo.meteorwatcher.utility.Formater
import telapo.meteorwatcher.utility.Sensors
import java.util.*

class NewObservationActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var loc: Location? = null
    private lateinit var dt: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_observation)
        Profile.Initialise(this)
        inpOfficialStart.setIs24HourView(true)

        val spinner: Spinner = findViewById(R.id.inpSchemes)

        ArrayAdapter(this, android.R.layout.simple_spinner_item, schemeProvider.GetList()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.inpPeriodTime)
        ArrayAdapter.createFromResource(
            this,
            R.array.saPeriods,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }

        btnStart.setOnClickListener {
            startClicked()
         }

    }

    private fun validateStartTime() : Boolean {
        val local = dt.get(Calendar.HOUR_OF_DAY)*60 + dt.get(Calendar.MINUTE)
        val official = inpOfficialStart.hour*60 + inpOfficialStart.minute

        if (Math.abs(local-official) <= 30) {
            dt.add(Calendar.MINUTE, official-local)
            return true
        }
        if (Math.abs(local-(official+24*60)) <= 30) {
            dt.add(Calendar.MINUTE, (official+24*60)-local)
            return true
        }
        return false
    }


    private val schemeProvider : SchemeProvider = SampleSchemeProvider()
    private fun getScheme(): Scheme{
        return schemeProvider.GetScheme(inpSchemes.selectedItemPosition)
    }

    private fun startClicked() {
        if (validateStartTime()) {
            val o = Observation(
                listOf<Comment>(),
                Profile.CreateSnapshot(),
                loc,
                dt.clone() as Calendar,
                (inpPeriodTime.selectedItemPosition+1)*15,
                getScheme()
            )

            Toast.makeText(this,
                dt.time.toString(),
                Toast.LENGTH_SHORT).show()

            Observation.Activate(o)
        } else {
            Toast.makeText(this,
                "Difference between local time and official start time can not be greater than 30 minutes.",
                Toast.LENGTH_LONG).show()
            return
        }
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
        val locationTask = fusedLocationClient.lastLocation

        locationTask?.addOnSuccessListener { location : Location? ->
            if (location != null) {
                loc = location
                tvLocationLongitude.text = location.longitude.toString()
                tvLocationLatitude.text = location.latitude.toString()
            } else {
                tvLocationLongitude.text = getText(R.string.strNullData)
                tvLocationLatitude.text = getText(R.string.strNullData)
            }
        }


        dt = Sensors.Time.Local
        tvTimeLocal.text = Formater.GetDateTime(dt)
        tvTimeUtc.text = Formater.GetDateTime(Sensors.Time.Utc)

        tvProfileName.text = Profile.Name
        tvProfileAddress.text = Profile.AddressCity
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
                ProfileFragment().show(supportFragmentManager, ProfileFragment::class.java.simpleName)
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