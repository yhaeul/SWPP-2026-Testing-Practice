# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Show the due-date label for a supplied task using the current date. A nonempty label is placed in the TextView and made visible; an empty label does not display a label.

**Pre-conditions**
- `textView` is a non-null TextView.
- The system current date is available.

**Post-conditions**
- Success cases
  - A completed task displays `CLEAR` and is visible, even if it has no due date. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `GONE`, and `Task(isCompleted = true, dueDate = null)` / When setDueDateLabel is called / Then the TextView text is `CLEAR` and visibility is `VISIBLE`.
  - An active task due today displays `D-Day` and is visible. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `GONE`, and `Task(isCompleted = false, dueDate = 2026-10-20)` / When setDueDateLabel is called / Then the TextView text is `D-Day` and visibility is `VISIBLE`.
  - An active task due in the future displays the exact calendar-day label and is visible. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `GONE`, and `Task(isCompleted = false, dueDate = 2026-10-23)` / When setDueDateLabel is called / Then the TextView text is `D-3` and visibility is `VISIBLE`.
  - An active overdue task displays the exact calendar-day label and is visible. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `GONE`, and `Task(isCompleted = false, dueDate = 2026-10-17)` / When setDueDateLabel is called / Then the TextView text is `D+3` and visibility is `VISIBLE`.
- Error cases
  - None; a nullable task is a valid input. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `VISIBLE` and text `existing` / When setDueDateLabel is called with task `null` / Then the TextView remains with text `existing` and visibility `VISIBLE`.
- Edge cases
  - An active task without a due date has no label to display, so the TextView's existing text and visibility remain unchanged. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `VISIBLE` and text `existing`, and `Task(isCompleted = false, dueDate = null)` / When setDueDateLabel is called / Then the TextView remains with text `existing` and visibility `VISIBLE`.
  - The previous calendar day displays `D+1` and is visible. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `GONE`, and `Task(isCompleted = false, dueDate = 2026-10-19)` / When setDueDateLabel is called / Then the TextView text is `D+1` and visibility is `VISIBLE`.
  - The next calendar day displays `D-1` and is visible. `[Integration]` Tip: Given current date `2026-10-20`, a TextView with visibility `GONE`, and `Task(isCompleted = false, dueDate = 2026-10-21)` / When setDueDateLabel is called / Then the TextView text is `D-1` and visibility is `VISIBLE`.

**Undefined behavior** (optional)
- None.
