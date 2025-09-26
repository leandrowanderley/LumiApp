package br.com.casainteligente.mini.energia

import kotlinx.coroutines.flow.*

class MonitorEnergia(private val sensores: List<SensorEnergia>) {
    private val atuais = MutableStateFlow<Map<String, LeituraEnergia>>(emptyMap())

    fun aoVivo(): Flow<SnapshotEnergia> {
        val fluxos = sensores.map { it.aoVivo() }
        return merge(*fluxos.toTypedArray()).map { l ->
            atuais.update { it + (l.deviceId to l) }
            snapshot()
        }
    }

    fun snapshot(): SnapshotEnergia {
        val m = atuais.value
        val porDisp = m.mapValues { it.value.watts }
        val porComodo = m.values.groupBy { it.comodo ?: "desconhecido" }
            .mapValues { (_, vs) -> vs.sumOf { it.watts } }
        return SnapshotEnergia(porDisp.values.sum(), porComodo, porDisp)
    }
}