package e.josephmolina.riseandsoar

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.support.v4.app.NotificationCompat

class Utils {
    companion object {
        fun createPendingIntent(activity: MainActivity, alarmReceiverIntent: Intent): PendingIntent {
            return PendingIntent.getBroadcast(activity, 0,
                    alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        fun createAlarmReceiverIntent(activity: MainActivity) : Intent {
           return Intent(activity, AlarmReceiver::class.java)
        }

        fun createMainActivityPendingIntent(ringtoneService: RingtoneService) : PendingIntent {
            val mainActivityIntent = Intent(ringtoneService, MainActivity::class.java)
            return PendingIntent.getActivity(ringtoneService, 0, mainActivityIntent, 0)
        }

        fun createAlarmNotification (ringtoneService: RingtoneService, defaultSoundUri: Uri): Notification {
            val notification: Notification = NotificationCompat.Builder(ringtoneService)
                    .setContentTitle("Alarm is going off")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(defaultSoundUri)
                    .setContentText("Wake up and click me")
                    .setContentIntent(createMainActivityPendingIntent(ringtoneService))
                    .setAutoCancel(true)
                    .build()
            return notification
        }
    }
}