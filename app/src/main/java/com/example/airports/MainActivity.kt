package com.example.airports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airports.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val airportCodes = mutableListOf<String>()
    private lateinit var binding: ActivityMainBinding

    lateinit var airportStatus: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addAirportCode.isEnabled = false

        binding.airportCode.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun afterTextChanged(p0: Editable?) {
                binding.addAirportCode.isEnabled = binding.airportCode.text.isNotBlank()
            }
        })

        binding.addAirportCode.setOnClickListener {
            airportCodes.add(binding.airportCode.text.toString())
            binding.airportCode.setText("")

            launch { updateAirportStatus() }
        }

        binding.airportStatus.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager (this@MainActivity)
            adapter = AirportAdapter()
        }
    }

    private suspend fun updateAirportStatus() {
        val airports = getAirportStatus(airportCodes)
        val airportAdapter = binding.airportStatus.adapter as AirportAdapter
        airportAdapter.updateAirportsStatus(airports)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}