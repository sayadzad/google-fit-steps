package com.example.googlefitsteps.model

import android.content.Context
import android.text.format.DateUtils
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.Bucket
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Steps  {

    val list = MutableLiveData<List<Step>>()
    var ascSort = MutableLiveData<Boolean>(false)

    fun fetchSteps(context: Context) {
        var steps = ArrayList<Step>()

        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val cal: Calendar = Calendar.getInstance()
        cal.time = Date()
        val endTime: Long = cal.getTimeInMillis()
        cal.add(Calendar.DAY_OF_YEAR, -14)
        val startTime: Long = cal.getTimeInMillis()
        val readRequest = DataReadRequest.Builder()
            .aggregate(
                DataType.TYPE_STEP_COUNT_DELTA,
                DataType.AGGREGATE_STEP_COUNT_DELTA
            )
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .build()
        val account = GoogleSignIn
            .getAccountForExtension(context, fitnessOptions)
        Fitness.getHistoryClient(context, account)
            .readData(readRequest)
            .addOnSuccessListener { response ->
                // Iterate through the data and update the step list
                val buckets = response.buckets
                val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                val bucketsIterator: Iterator<Bucket> = response.buckets.iterator()
                // Start list from today
                buckets.reverse()
                // In case there is no step data
                val backupDate = Calendar.getInstance()

                for (bucket in bucketsIterator) {
                    val dataSet = bucket.dataSets[0]
                    val dataCount = dataSet.dataPoints.size

                    var stepDate = backupDate.time
                    var stepDateString = formatter.format(stepDate)
                    var stepCount = 0
                    if (dataCount != 0) {
                        val dataPoint = dataSet.dataPoints[0]

                        stepDate = Date(dataPoint.getStartTime(TimeUnit.MILLISECONDS))
                        stepCount = dataPoint.getValue(dataPoint.dataType.fields[0]).asInt()
                    }

                    // Use "Today" and "Yesterday"
                    if (DateUtils.isToday(stepDate.time)
                        || DateUtils.isToday(stepDate.time + DateUtils.DAY_IN_MILLIS)) {
                        val now = System.currentTimeMillis()
                        stepDateString = DateUtils.getRelativeTimeSpanString(
                            stepDate.time,
                            now,
                            DateUtils.DAY_IN_MILLIS
                        ).toString()
                    }

                    val step = Step()
                    step.date = stepDateString
                    step.count = stepCount
                    steps.add(step)

                    backupDate.add(Calendar.DAY_OF_YEAR, -1)
                }

                list.value = steps
            }
    }

    fun reverseList() {
        list.value = list.value?.reversed()
        ascSort.value = !ascSort.value!!
    }
}