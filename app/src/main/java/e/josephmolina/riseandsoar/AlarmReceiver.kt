package e.josephmolina.riseandsoar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmStatus: String = intent!!.getStringExtra("alarmStatus")

        val serviceIntent = Intent(context, RingtoneService::class.java)
        serviceIntent.putExtra("alarmStatus", alarmStatus)
        context!!.startService(serviceIntent)
    }
}