package com.yasxin.githubuser.ui.setings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.yasxin.githubuser.R

class SetingActivity : AppCompatActivity() {
    private val pref by lazy { PrefHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seting)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_night_mode)

        switchTheme.isChecked = pref.getBoolean("pref_is_dark_mode")

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked ->
            when (isChecked) {
                true -> {
                    pref.put("pref_is_dark_mode",true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                false -> {
                    pref.put("pref_is_dark_mode",false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }


    }

}