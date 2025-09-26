// br/com/casainteligente/mini/notificacao/NotificadorMobile.kt

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lumiapp.R // Você precisará de um arquivo strings.xml com o nome do app
import br.com.casainteligente.mini.analise.Alerta

class NotificadorMobile(private val context: Context) : Notificador {

    private val CHANNEL_ID = "CasaInteligente_Channel"
    private var notificationId = 0

    override fun enviar(alerta: Alerta) {
        val titulo = "Alerta: ${alerta.tipo}"
        val texto = "${alerta.deviceId} - ${alerta.detalhe}"

        // Constrói a notificação
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ícone para a notificação
            .setContentTitle(titulo)
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Exibe a notificação
        with(NotificationManagerCompat.from(context)) {
            // A notificação pode ter um ID único para ser atualizada
            notify(notificationId++, builder.build())
        }
    }
}