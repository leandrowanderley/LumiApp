// HomeViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.casainteligente.mini.analise.AnomalyDetector
import br.com.casainteligente.mini.energia.MonitorEnergia
import br.com.casainteligente.mini.energia.SensorSimulado
import br.com.casainteligente.mini.energia.SnapshotEnergia
import br.com.casainteligente.mini.sugestoes.Preferencias
import br.com.casainteligente.mini.atuacao.Casa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // Sensores simulados (agora controláveis pelo ViewModel)
    private val s1 = SensorSimulado("Chuveiro", "banheiro", 4500.0, 150.0)
    private val s2 = SensorSimulado("Geladeira", "cozinha", 120.0, 20.0)
    private val s3 = SensorSimulado("TV_Sala", "sala", 6.0, 1.0)
    private val sensoresSimulados = listOf(s1, s2, s3)
    private val monitor = MonitorEnergia(sensoresSimulados)
    private val prefs = Preferencias(limiteWattsEmPonta = 1000.0)
    private val casa = Casa(sensoresSimulados.associateBy { it.deviceId })
    private val detector = AnomalyDetector(alpha = 0.5, desvioThresh = 1.5, offlineMs = 2000)

    // Estado da UI (para Jetpack Compose)
    private val _snapshotEnergia = MutableStateFlow(SnapshotEnergia(0.0, emptyMap(), emptyMap()))
    val snapshotEnergia = _snapshotEnergia.asStateFlow()

    private val _alerts = MutableStateFlow<List<String>>(emptyList())
    val alerts = _alerts.asStateFlow()

    init {
        // Inicia a simulação
        viewModelScope.launch {
            monitor.aoVivo().collect { s ->
                val now = System.currentTimeMillis()
                _snapshotEnergia.value = s

                // Checa anomalias
                s.porDispositivo.forEach { (id, w) ->
                    detector.feed(id, w, now).forEach {
                        _alerts.value += "🚨 Alerta: ${it.tipo} em ${it.deviceId}."
                    }
                }
            }
        }
    }

    // Funções para o usuário interagir
    fun ligarChuveiro() {
        casa.aplicar(br.com.casainteligente.mini.sugestoes.Acao(br.com.casainteligente.mini.sugestoes.TipoAcao.AJUSTAR_NIVEL, mapOf("id" to s1.deviceId, "nivel" to "100")))
    }

    fun desligarChuveiro() {
        casa.aplicar(br.com.casainteligente.mini.sugestoes.Acao(br.com.casainteligente.mini.sugestoes.TipoAcao.DESLIGAR, mapOf("id" to s1.deviceId)))
    }

    // Você pode criar funções similares para outros dispositivos
}