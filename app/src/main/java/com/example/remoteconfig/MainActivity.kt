package com.example.remoteconfig

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import com.example.remoteconfig.databinding.ActivityMainBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var bind:ActivityMainBinding
    lateinit var Remotecon : FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        Remotecon = FirebaseRemoteConfig.getInstance()
        val chetime = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        val co = "#00FF00"
        Remotecon.setDefaultsAsync(mapOf("color" to co))
        val ig = "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png"
        Remotecon.setDefaultsAsync(mapOf("imgae" to ig))
        Remotecon.setConfigSettingsAsync(chetime)
        Remotecon.setDefaultsAsync(R.xml.default_value)
        Remotecon.fetchAndActivate().addOnCompleteListener(this) { tusk->
            if(tusk.isSuccessful){
                val wish_id = Remotecon.getString("change_it_key")
                bind.txt.text = wish_id
                 val image_id = Remotecon.getString("image_key")
                val id = bind.imageView
                Picasso.get().load(image_id).into(id)
                val bg_id = Remotecon.getString("bg_id_color")
                bind.bgLa.setBackgroundColor(Color.parseColor(bg_id))

            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failure the Remote",Toast.LENGTH_SHORT).show()
        }
    }
}