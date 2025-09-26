package br.com.casainteligente.mini.energia

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.time.Instant
import kotlin.random.Random

class SensorSimulado(
    override val deviceId: String,
    private val comodo: String,
    private val baseW: Double,
    private val variacao: Double = baseW * 0.1,
    private val intervaloMs: Long = 1000L
) : SensorEnergia {

    private val ligado = MutableStateFlow(true)
    fun ligar() { ligado.value = true }
    fun desligar() { ligado.value = false }

    override fun aoVivo(): Flow<LeituraEnergia> = ligado.flatMapLatest { on ->
        flow {
            while (true) {
                val wBase = (baseW + Random.nextDouble(-variacao, variacao)).coerceAtLeast(0.0)
                val w = if (on) wBase else 0.0
                emit(LeituraEnergia(Instant.now(), deviceId, comodo, w))
                delay(intervaloMs)
            }
        }
    }
}