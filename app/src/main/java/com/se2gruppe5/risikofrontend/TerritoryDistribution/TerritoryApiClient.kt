package com.se2gruppe5.risikofrontend.TerritoryDistribution

import com.google.gson.Gson
import com.se2gruppe5.risikofrontend.game.dataclasses.TerritoryRecord
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class TerritoryApiClient {

    private val client = OkHttpClient()

    // Abrufen der Territorien eines Spielers
    fun fetchTerritoriesForPlayer(playerId: String, onSuccess: (List<TerritoryRecord>) -> Unit, onFailure: (String) -> Unit) {
        val url = "http://dein-backend-url/api/territories/player/$playerId"  // Passe die URL an

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    body?.let {
                        val territories = parseTerritoriesJson(it)
                        onSuccess(territories)
                    }
                } else {
                    onFailure("Fehler beim Abrufen der Territorien: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                onFailure("Netzwerkfehler: ${e.message}")
            }
        })
    }

    // Funktion zum Aktualisieren der Truppen eines Territoriums
    fun updateTerritoryTroops(territory: TerritoryRecord, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val url = "http://dein-backend-url/api/territories/update"  // Passe die URL an

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val json = """
             "id": ${territory.id},
             "troops": ${territory.troops}
        }
        """.trimIndent()

        val body = RequestBody.create(mediaType, json)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("Fehler beim Aktualisieren der Truppen: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                onFailure("Netzwerkfehler: ${e.message}")
            }
        })
    }

    // Hilfsfunktion zum Parsen des JSON in eine Liste von TerritoryRecord
    private fun parseTerritoriesJson(json: String): List<TerritoryRecord> {
        val gson = Gson()
        return gson.fromJson(json, Array<TerritoryRecord>::class.java).toList()
    }
}