package e.josephmolina.riseandsoar

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder

class RingtoneService : Service() {

    lateinit var ringtone: Ringtone
    private val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    private var alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    private var isAlarmSet: Boolean = false

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    // Called every time a client starts the service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var alarmStatus: String = intent!!.getStringExtra("alarmStatus")

        // Alarm hasn't been set and user pressed 'set alarm'
        if (!this.isAlarmSet && alarmStatus == "on") {
            playAlarm()
            this.isAlarmSet = true
            fireNotification()
        } // Alarm has been set and stop was pressed
        else if (this.isAlarmSet && alarmStatus == "off") {
            ringtone.stop()
            this.isAlarmSet = false

        } // Stop alarm is pressed without an alarm being set
        else if (!this.isAlarmSet && alarmStatus == "off") {
            this.isAlarmSet = false

        } // 'set' alarm was pressed more than one time
        else if (this.isAlarmSet &&  alarmStatus == "on" ) {
            this.isAlarmSet = true
        }
        return START_NOT_STICKY
    }

    private fun fireNotification() {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val firedAlarmNotification: Notification = Utils.createAlarmNotification(this, defaultSoundUri)
        notificationManager.notify(0, firedAlarmNotification)
    }

    private fun playAlarm() {
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        ringtone = RingtoneManager.getRingtone(baseContext, alarmUri)
        ringtone.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isAlarmSet = false
    }
}