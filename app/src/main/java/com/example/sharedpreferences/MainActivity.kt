package com.example.sharedpreferences

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val etName: TextInputEditText by lazy { findViewById(R.id.tetName) }
    private val etAge: TextInputEditText by lazy { findViewById(R.id.tetAge) }
    private val cbReminded: CheckBox by lazy { findViewById(R.id.cb) }
    private val btnSave: Button by lazy { findViewById(R.id.btnSave) }
    private val btnLoad: Button by lazy { findViewById(R.id.btnLoad) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val age: Int? = etAge.text.toString().toIntOrNull()
            val reminded: Boolean = cbReminded.isChecked

            if (nameAndAgeIsCorrect(name = name, age = age)) {
                editor.apply {
                    putString("name", name)
                    age?.let { putInt("age", it) }
                    putBoolean("reminded", reminded)
                    apply()
                }

                Toast.makeText(this, getString(R.string.thanks_we_will_get_back), Toast.LENGTH_LONG)
                    .show()

            } else {
                Toast.makeText(this, getString(R.string.enter_details_correct), Toast.LENGTH_LONG)
                    .show()
            }
        }
        btnLoad.setOnClickListener {
            val name = sharedPreferences.getString("name", null)
            val age: Int = sharedPreferences.getInt("age", -1)
            val reminded: Boolean = sharedPreferences.getBoolean("reminded", false)

            etName.setText(name)
            etAge.setText(age.toString())
            cbReminded.isChecked = reminded

        }
    }

    private fun nameAndAgeIsCorrect(name: String, age: Int?) = name.isNotEmpty() && age != null
}