package e.josephmolina.riseandsoar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.newalarm.*
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*


class NewAlarm : AppCompatActivity() {
    private val calender = Calendar.getInstance()
    private val dateFormat:SimpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newalarm)

        displayCurrentAlarmTime()
        alarm_time.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                setNewAlarmTime(hour, minute)
                displayCurrentAlarmTime()
            }
            TimePickerDialog(this, timeSetListener, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false).show()
        }
    }


    private fun displayCurrentAlarmTime() {
        alarm_time.text = dateFormat.format(calender.time)
    }

    private fun setNewAlarmTime(hour: Int,  minute: Int) {
        calender.set(Calendar.HOUR_OF_DAY, hour)
        calender.set(Calendar.MINUTE, minute)
    }
}

