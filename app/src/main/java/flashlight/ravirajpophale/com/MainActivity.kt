package flashlight.ravirajpophale.com

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.support.v4.app.ActivityCompat
//import android.support.v4.content.ContextCompat
import android.widget.ImageButton
import android.widget.Toast
import android.hardware.camera2.CameraAccessException
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION = 200
    var flashLightStatus: Boolean = false
    var btAction: ImageButton? = null
    var tvStatus: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAction = findViewById(R.id.btAction)
        tvStatus = findViewById(R.id.tvStatus)
        tvStatus!!.setText("ON")

        btAction!!.setOnClickListener {
            val permissions = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissions != PackageManager.PERMISSION_GRANTED)
                    setupPermissions()
                else {
                    openFlashLight()
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    openFlashLight()
                }
            }
        }
    }


    private fun setupPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION -> {
                if (grantResults.isEmpty() || !grantResults[0].equals(PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        openFlashLight()
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun openFlashLight() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!flashLightStatus) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, true)
                }
                btAction!!.setImageDrawable(getDrawable(R.drawable.on_icon))
                tvStatus!!.setText("ON")
                flashLightStatus = true

            } catch (@SuppressLint("NewApi") e: CameraAccessException) {
            }
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, false)
                }
                btAction!!.setImageDrawable(getDrawable(R.drawable.off_icon))
                tvStatus!!.setText("OFF")
                flashLightStatus = false
            } catch (@SuppressLint("NewApi") e: CameraAccessException) {
            }
        }

    }
}
