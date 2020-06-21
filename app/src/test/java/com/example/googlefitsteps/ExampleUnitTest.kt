package com.example.googlefitsteps

import com.example.googlefitsteps.model.Step
import com.example.googlefitsteps.viewmodel.StepsViewModel
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun test_clickReturnCorrectObject() {
        val steps = simulateSteps()
        val viewModel = StepsViewModel()

        // TODO
    }

    private fun simulateSteps(): List<Step> {
        val steps = ArrayList<Step>()
        for (i in 0..14) {
            val db = Step()
            db.date = "Day $i"
            db.count = i
            steps.add(db)
        }
        return steps
    }
}