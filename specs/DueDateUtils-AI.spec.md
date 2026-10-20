# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return the task’s D-day label using the calendar-day difference from today. Completed tasks always show `CLEAR`; active tasks without a due date show no label.

**Pre-conditions**
- `today` is a non-null calendar date.

**Post-conditions**
- Success cases
  - A completed task returns `CLEAR` regardless of its due date. `[Unit]` Tip: Given dueDate `2026-10-20`, today `2026-10-20`, and isCompleted `true` / When dueDateLabel is called / Then the result is `CLEAR`.
  - An active task with no due date returns the empty string. `[Unit]` Tip: Given dueDate `null`, today `2026-10-20`, and isCompleted `false` / When dueDateLabel is called / Then the result is `""`.
  - An active task due today returns `D-Day`. `[Unit]` Tip: Given dueDate `2026-10-20`, today `2026-10-20`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D-Day`.
  - An active task due in the future returns `D-N`, where N is the exact number of calendar days until the due date. `[Unit]` Tip: Given dueDate `2026-10-23`, today `2026-10-20`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D-3`.
  - An active overdue task returns `D+N`, where N is the exact number of calendar days since the due date. `[Unit]` Tip: Given dueDate `2026-10-17`, today `2026-10-20`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D+3`.
- Error cases
  - None; all nullable due dates and all calendar dates are valid inputs. `[Unit]` Tip: Given dueDate `null`, today `2026-10-20`, and isCompleted `true` / When dueDateLabel is called / Then the result is `CLEAR`.
- Edge cases
  - The day immediately before today is overdue by one day. `[Unit]` Tip: Given dueDate `2026-10-19`, today `2026-10-20`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D+1`.
  - The day immediately after today is one day away. `[Unit]` Tip: Given dueDate `2026-10-21`, today `2026-10-20`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D-1`.
  - Calendar-day differences remain correct across a month boundary. `[Unit]` Tip: Given dueDate `2026-11-01`, today `2026-10-31`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D-1`.
  - Calendar-day differences remain correct across a year boundary. `[Unit]` Tip: Given dueDate `2027-01-01`, today `2026-12-31`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D-1`.
  - Calendar-day differences remain correct across leap day. `[Unit]` Tip: Given dueDate `2024-03-01`, today `2024-02-28`, and isCompleted `false` / When dueDateLabel is called / Then the result is `D-2`.

**Undefined behavior** (optional)
- None.
