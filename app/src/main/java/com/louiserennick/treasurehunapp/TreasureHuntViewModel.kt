package com.louiserennick.treasurehunapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TreasureHuntViewModel: ViewModel(){
    private val _currentStep = MutableStateFlow(0)
    val currentStep : StateFlow<Int> = _currentStep
private val _found= MutableStateFlow(false)
    val found : StateFlow<Boolean> = _found

    val locations = listOf (
        "A piano",
        "A TV remote",
        "Sneakers",
        "Pillow",
        "Gold",
        "Refrigerator",
        "Flashing",
        "Clock",
        "Ball",
        "Book",
        "Toy Car",
        "Plant",
        "Soap",
        "Phone",
        "Scissors",
        "Chips",
        "Speaker",
        "Sweater",
        "Apple",
        "Bottle",
        "Flower Couche",
        "Treasure Chest - Final Stop"

    )
    val images = listOf (
        R.drawable.pianotomandjerry,
        R.drawable.remote,
        R.drawable.tomandjerryreebok,
        R.drawable.pillowtomandjerry,
        R.drawable.tomanderrygold,
        R.drawable.refridgetomandjerry,
        R.drawable.flash,
        R.drawable.clock,
        R.drawable.ball,
        R.drawable.book,
        R.drawable.toycar,
        R.drawable.plant,
        R.drawable.soap,
        R.drawable.phone,
        R.drawable.scissors,
        R.drawable.chips,
        R.drawable.speaker,
        R.drawable.sweater,
        R.drawable.apple,
        R.drawable.bottles,
        R.drawable.colorsofa,
    )
    // need to get a map of the city and make it a static ping
    // save it as city_map.png and place in your drawables
    fun nextLocation(){
        if(_currentStep.value < locations.size - 1){
            _currentStep.value++
            _found.value = false
        }
    }
    fun clueFound () {
        _found.value=true
    }
}
