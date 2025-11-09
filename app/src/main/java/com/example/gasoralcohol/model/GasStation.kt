package com.example.gasoralcohol.model

import java.util.Date

data class GasStation(
    val id: Long = Date().time,
    var name: String,
    var address: String,
    var gasPrice: Double,
    var alcoholPrice: Double,
    var date: String,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
) {
    /**
     * Calculates which fuel is better based on the 0.7 rule:
     * If alcohol price is less than 70% of gas price, alcohol is better
     */
    val betterChoice: String
        get() = when {
            alcoholPrice <= 0.0 || gasPrice <= 0.0 -> "unknown"
            alcoholPrice / gasPrice < 0.7 -> "alcohol"
            alcoholPrice / gasPrice > 0.7 -> "gas"
            else -> "equivalent"
        }
}