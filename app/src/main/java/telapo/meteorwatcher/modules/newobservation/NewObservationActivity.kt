package telapo.meteorwatcher.modules.newobservation

import android.Manifest
import android.content.Intent
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
import telapo.meteorwatcher.dal.local.AppDatabase
import telapo.meteorwatcher.dal.model.Profile
import telapo.meteorwatcher.dal.model.observation.Observation
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.modules.liveobservation.HmgFragment
import telapo.meteorwatcher.modules.profile.ProfileFragment
import telapo.meteorwatcher.modules.schemes.SchemesActivity
import telapo.meteorwatcher.utility.Formater
import telapo.meteorwatcher.utility.Time
import java.util.*
import kotlin.concurrent.thread

class NewObservationActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var loc: Location? = null
    private lateinit var dt: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_observation)
        setSupportActionBar(findViewById(R.id.toolbar))

        Profile.Initialise(this)
        inpOfficialStart.setIs24HourView(true)

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

    private var schemes : List<Scheme>? = null
    private fun getScheme(): Scheme {
        return schemes!!.get(inpSchemes.selectedItemPosition)
    }

    private fun startClicked() {
        if (validateStartTime()) {
            val profile = Profile.CreateSnapshot()
            if (profile.Name == "") {
                Toast.makeText(
                    this,
                    "Please add your name.",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            if (schemes!!.size == 0) {
                Toast.makeText(
                    this,
                    "No scheme selected.",
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val o = Observation(
                mutableListOf(),
                profile,
                loc?.longitude,
                loc?.latitude,
                dt.clone() as Calendar,
                (inpPeriodTime.selectedItemPosition+1)*15,
                getScheme()
            )

            Observation.Activate(o)
            HmgFragment(this,true).show(
                supportFragmentManager,
                HmgFragment::class.java.simpleName
            )
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

    override fun onResume() {
        super.onResume()
        refresh()
    }


    private fun refresh() {
        val spinner: Spinner = findViewById(R.id.inpSchemes)

        thread {
            schemes = AppDatabase.getInstance(this).schemeDao().GetAll()
            runOnUiThread {
                ArrayAdapter(
                    this, android.R.layout.simple_spinner_item, schemes!!
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
            }
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


        dt = Time.Local
        tvTimeLocal.text = Formater.GetDateTime(dt)
        tvTimeUtc.text = Formater.GetDateTime(Time.Utc)

        tvProfileName.text = Profile.Name
        tvProfileAddress.text = Profile.AddressCity
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_observation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miProfile -> {
                ProfileFragment().show(supportFragmentManager, ProfileFragment::class.java.simpleName)
                return true
            }
            R.id.miSchemes -> {
                startActivity(Intent(this, SchemesActivity::class.java))
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