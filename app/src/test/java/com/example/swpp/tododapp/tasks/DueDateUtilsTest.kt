package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DueDateUtilsTest {

    @Test
    fun dueDateLabel_completedWithDueDateToday_returnsClear() {
        // Given a completed task due today
        val today = LocalDate.of(2026, 10, 20)

        // When its label is calculated
        val result = dueDateLabel(today, today, true)

        // Then the completed label is returned
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_completedWithoutDueDate_returnsClear() {
        // Given a completed task without a due date
        val today = LocalDate.of(2026, 10, 20)

        // When its label is calculated
        val result = dueDateLabel(null, today, true)

        // Then the completed label is returned
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_activeWithoutDueDate_returnsEmptyString() {
        // Given an active task without a due date
        val result = dueDateLabel(null, LocalDate.of(2026, 10, 20), false)

        // Then no label is returned
        assertEquals("", result)
    }

    @Test
    fun dueDateLabel_activeDueToday_returnsDDay() {
        // Given an active task due today
        val today = LocalDate.of(2026, 10, 20)

        // When its label is calculated
        val result = dueDateLabel(today, today, false)

        // Then the due-today label is returned
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_activeDueThreeDaysLater_returnsDMinus3() {
        // Given an active task due three days later
        val result = dueDateLabel(
            LocalDate.of(2026, 10, 23),
            LocalDate.of(2026, 10, 20),
            false
        )

        // Then the exact future interval is returned
        assertEquals("D-3", result)
    }

    @Test
    fun dueDateLabel_activeOverdueThreeDays_returnsDPlus3() {
        // Given an active task overdue by three days
        val result = dueDateLabel(
            LocalDate.of(2026, 10, 17),
            LocalDate.of(2026, 10, 20),
            false
        )

        // Then the exact overdue interval is returned
        assertEquals("D+3", result)
    }

    @Test
    fun dueDateLabel_activeDueYesterday_returnsDPlus1() {
        // Given an active task due the day before today
        val result = dueDateLabel(
            LocalDate.of(2026, 10, 19),
            LocalDate.of(2026, 10, 20),
            false
        )

        // Then the overdue interval is one day
        assertEquals("D+1", result)
    }

    @Test
    fun dueDateLabel_activeDueTomorrow_returnsDMinus1() {
        // Given an active task due the day after today
        val result = dueDateLabel(
            LocalDate.of(2026, 10, 21),
            LocalDate.of(2026, 10, 20),
            false
        )

        // Then the future interval is one day
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_activeAcrossMonthBoundary_returnsTotalCalendarDays() {
        // Given an active task 34 calendar days after today across a month boundary
        val result = dueDateLabel(
            LocalDate.of(2026, 11, 23),
            LocalDate.of(2026, 10, 20),
            false
        )

        // Then the total calendar-day interval is returned
        assertEquals("D-34", result)
    }

    @Test
    fun dueDateLabel_activeAcrossYearBoundary_returnsOneDay() {
        // Given an active task due the day after a year boundary
        val result = dueDateLabel(
            LocalDate.of(2027, 1, 1),
            LocalDate.of(2026, 12, 31),
            false
        )

        // Then the interval is one calendar day
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_activeAcrossLeapDay_returnsTwoDays() {
        // Given an active task due two calendar days after February 28 in a leap year
        val result = dueDateLabel(
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 2, 28),
            false
        )

        // Then the interval includes February 29
        assertEquals("D-2", result)
    }
}
