# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return the task's due-date label. Completed tasks return `CLEAR`; active tasks with no due date return an empty string; otherwise the label reflects the total calendar-day difference between today and the due date.

**Pre-conditions**
- `today` is a valid, non-null `LocalDate`.
- `dueDate` is either `null` or a valid `LocalDate`.

**Post-conditions**
- Success cases
  - A completed task with a due date today returns `CLEAR`. `[Unit]`
    - Tip: Given dueDate `2026-10-20`, today `2026-10-20`, and isCompleted `true` / When `dueDateLabel` is called / Then the result is `CLEAR`.
  - A completed task without a due date returns `CLEAR`. `[Unit]`
    - Tip: Given dueDate `null`, today `2026-10-20`, and isCompleted `true` / When `dueDateLabel` is called / Then the result is `CLEAR`.
  - An active task without a due date returns the empty string. `[Unit]`
    - Tip: Given dueDate `null`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `""`.
  - An active task due today returns `D-Day`. `[Unit]`
    - Tip: Given dueDate `2026-10-20`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D-Day`.
  - An active task due three calendar days later returns `D-3`. `[Unit]`
    - Tip: Given dueDate `2026-10-23`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D-3`.
  - An active task overdue by three calendar days returns `D+3`. `[Unit]`
    - Tip: Given dueDate `2026-10-17`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D+3`.
- Error cases
  - None. Every nullable due date and every valid `LocalDate` is an accepted input.
- Edge cases
  - The day before today is overdue by one day. `[Unit]`
    - Tip: Given dueDate `2026-10-19`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D+1`.
  - The day after today is one day away. `[Unit]`
    - Tip: Given dueDate `2026-10-21`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D-1`.
  - A multi-week interval across a month boundary uses the total number of calendar days, not only the day component. `[Unit]`
    - Tip: Given dueDate `2026-11-23`, today `2026-10-20`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D-34`.
  - A one-day interval across a year boundary is counted as one calendar day. `[Unit]`
    - Tip: Given dueDate `2027-01-01`, today `2026-12-31`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D-1`.
  - March 1 is two days after February 28 in a leap year. `[Unit]`
    - Tip: Given dueDate `2024-03-01`, today `2024-02-28`, and isCompleted `false` / When `dueDateLabel` is called / Then the result is `D-2`.

**Undefined behavior** (optional)
- None.
