// br/com/casainteligente/mini/notificacao/Notificador.kt

import br.com.casainteligente.mini.analise.Alerta

interface Notificador {
    fun enviar(alerta: Alerta)
}

class NotificadorConsole : Notificador {
    override fun enviar(alerta: Alerta) {
        println("🚨 [${alerta.tipo}] ${alerta.deviceId} — ${alerta.detalhe}")
    }
}