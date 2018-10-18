package e.josephmolina.riseandsoar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var alarmManager: AlarmManager
    lateinit var context: Context
    lateinit var pendingAlarmIntent: PendingIntent
    var hr_str: String = ""
    var min_str: String = ""
    var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmReceiverIntent = Utils.createAlarmReceiverIntent(this@MainActivity)

        set_alarm_button.setOnClickListener {
            createAlarm(alarmReceiverIntent)
        }

        stop_alarm_button.setOnClickListener {
            stopAlarm(alarmReceiverIntent)
        }

        button2.setOnClickListener {
            val testIntent = Intent(context, NewAlarm::class.java)
            startActivity(testIntent)
        }
    }

    private fun createAlarm(alarmReceiverIntent: Intent) {
        setCalenderProperties(calendar)
        setHourAndMinStatus()
        setAlarmText("Alarm set to: $hr_str : $min_str")
        alarmReceiverIntent.putExtra("alarmStatus", "on")
        pendingAlarmIntent = Utils.createPendingIntent(this@MainActivity, alarmReceiverIntent)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingAlarmIntent)
    }

    private fun stopAlarm(alarmReceiverIntent: Intent) {
        setAlarmText("Alarm off")
        pendingAlarmIntent = Utils.createPendingIntent(this@MainActivity, alarmReceiverIntent)
        alarmManager.cancel(pendingAlarmIntent)
        alarmReceiverIntent.putExtra("alarmStatus", "off")
        sendBroadcast(alarmReceiverIntent)
    }

    private fun setHourAndMinStatus() {
        var hour = time_picker.hour
        var min = time_picker.minute
        hr_str = hour.toString()
        min_str = min.toString()

        if (hour > 12) {
            hr_str = (hour - 12).toString()
        }
        if (min < 10) {
            min_str = "0$min"
        }
    }

    private fun setCalenderProperties(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, time_picker.hour)
        calendar.set(Calendar.MINUTE, time_picker.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    private fun setAlarmText(s: String) {
        is_alarm_set_textview.setText(s)
    }
}
