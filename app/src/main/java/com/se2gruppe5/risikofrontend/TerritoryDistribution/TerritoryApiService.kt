package com.se2gruppe5.risikofrontend.TerritoryDistribution

import com.se2gruppe5.risikofrontend.game.dataclasses.TerritoryRecord
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType

class TerritoryApiService {

    private val client = OkHttpClient() // OkHttp-Client initialisieren
    private val gson = Gson()

    // Abrufen der Territorien eines Spielers
    fun getTerritoriesForPlayer(playerId: String, callback: (List<TerritoryRecord>?) -> Unit) {
        val url = "http://10.0.2.2:8080/api/territories/player/$playerId"  // Beispiel URL für lokale Entwicklung

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Fehlerbehandlung
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()

                    // Die Antwort mit Gson parsen
                    val listType: Type = object : TypeToken<List<TerritoryRecord>>() {}.type
                    val territories: List<TerritoryRecord> = gson.fromJson(responseBody, listType)

                    // Erfolgreiche Antwort, Rückgabe der Territorien
                    callback(territories)
                } else {
                    callback(null)  // Fehlerhafte Antwort
                }
            }
        })
    }

    // Aktualisieren der Truppen eines Gebiets
    fun updateTerritoryTroops(territory: TerritoryRecord, callback: (Boolean) -> Unit) {
        val url = "http://10.0.2.2:8080/api/territories/update"

        val jsonBody = gson.toJson(territory)
        val mediaType = "application/json; charset=utf-8".toMediaType() // Verwendung der Erweiterungsfunktion
        val requestBody = RequestBody.create(mediaType, jsonBody)  // Erstellen des RequestBody

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Fehlerbehandlung
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Erfolgreiche Antwort
                    callback(true)
                } else {
                    // Fehlerhafte Antwort
                    callback(false)
                }
            }
        })
    }
}