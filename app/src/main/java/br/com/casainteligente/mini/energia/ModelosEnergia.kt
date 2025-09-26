package br.com.casainteligente.mini.energia

import kotlinx.coroutines.flow.Flow
import java.time.Instant

data class LeituraEnergia(
    val timestamp: Instant,
    val deviceId: String,
    val comodo: String?,
    val watts: Double
)

data class SnapshotEnergia(
    val totalWatts: Double,
    val porComodo: Map<String, Double>,
    val porDispositivo: Map<String, Double>
)

interface SensorEnergia {
    val deviceId: String
    fun aoVivo(): Flow<LeituraEnergia>
}