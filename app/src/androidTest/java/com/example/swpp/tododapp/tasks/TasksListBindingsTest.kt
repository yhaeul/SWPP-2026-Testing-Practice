package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {
    private lateinit var textView: TextView

    @Before
    fun createTextView() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        textView = TextView(context).apply {
            text = ""
            visibility = View.GONE
        }
    }

    @Test
    fun setDueDateLabel_completedWithoutDueDate_showsClearAndVisible() {
        // Given a completed task without a due date and a hidden empty TextView
        val task = task(isCompleted = true, dueDate = null)

        // When its due-date label is bound
        setDueDateLabel(textView, task)

        // Then the completed label is displayed
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeDueToday_showsDDayAndVisible() {
        // Given an active task due on the device's current date
        val task = task(isCompleted = false, dueDate = LocalDate.now())

        // When its due-date label is bound
        setDueDateLabel(textView, task)

        // Then the due-today label is displayed
        assertEquals("D-Day", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeDueThreeDaysLater_showsDMinus3AndVisible() {
        // Given an active task due three calendar days from the device's current date
        val task = task(isCompleted = false, dueDate = LocalDate.now().plusDays(3))

        // When its due-date label is bound
        setDueDateLabel(textView, task)

        // Then the exact future interval is displayed
        assertEquals("D-3", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeOverdueThreeDays_showsDPlus3AndVisible() {
        // Given an active task due three calendar days before the device's current date
        val task = task(isCompleted = false, dueDate = LocalDate.now().minusDays(3))

        // When its due-date label is bound
        setDueDateLabel(textView, task)

        // Then the exact overdue interval is displayed
        assertEquals("D+3", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_nullTask_clearsAndHidesPreviousLabel() {
        // Given a visible TextView with a previous label
        textView.text = "CLEAR"
        textView.visibility = View.VISIBLE

        // When a null task is bound
        setDueDateLabel(textView, null)

        // Then the old label is cleared and hidden
        assertEquals("", textView.text.toString())
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_taskChangedFromCompletedToActive_clearsAndHidesLabel() {
        // Given a completed task without a due date
        val task = task(isCompleted = true, dueDate = null)

        // When the task is bound as completed
        setDueDateLabel(textView, task)
        assertEquals("CLEAR", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)

        // And the same task is changed to active and bound again
        task.isCompleted = false
        setDueDateLabel(textView, task)

        // Then the previous completed label is cleared and hidden
        assertEquals("", textView.text.toString())
        assertEquals(View.GONE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeDueYesterday_showsDPlus1AndVisible() {
        // Given an active task due the previous calendar day
        val task = task(isCompleted = false, dueDate = LocalDate.now().minusDays(1))

        // When its due-date label is bound
        setDueDateLabel(textView, task)

        // Then the one-day overdue label is displayed
        assertEquals("D+1", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    @Test
    fun setDueDateLabel_activeDueTomorrow_showsDMinus1AndVisible() {
        // Given an active task due the next calendar day
        val task = task(isCompleted = false, dueDate = LocalDate.now().plusDays(1))

        // When its due-date label is bound
        setDueDateLabel(textView, task)

        // Then the one-day future label is displayed
        assertEquals("D-1", textView.text.toString())
        assertEquals(View.VISIBLE, textView.visibility)
    }

    private fun task(isCompleted: Boolean, dueDate: LocalDate?) = Task(
        title = "Task",
        description = "Description",
        isCompleted = isCompleted,
        id = "task-test",
        dueDate = dueDate
    )
}
