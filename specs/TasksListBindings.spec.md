# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Display the correct due-date label for a task using the Android device's current local date. Show non-empty labels and clear and hide the label whenever the task has no label, including when a recycled `TextView` previously displayed another task's label.

**Pre-conditions**
- `textView` is a non-null `TextView`.
- `task` is either `null` or a valid task.
- For the date-specific test Tips below, the Android device's local date is `2026-10-20`.

**Post-conditions**
- Success cases
  - A completed task without a due date displays `CLEAR` and is visible. `[Integration]`
    - Tip: Given device date `2026-10-20`, a TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = true, id = "task-1", dueDate = null)` / When `setDueDateLabel` is called / Then the TextView text is `CLEAR` and visibility is `VISIBLE`.
  - An active task due today displays `D-Day` and is visible. `[Integration]`
    - Tip: Given device date `2026-10-20`, a TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = false, id = "task-3", dueDate = 2026-10-20)` / When `setDueDateLabel` is called / Then the TextView text is `D-Day` and visibility is `VISIBLE`.
  - An active task due three days later displays `D-3` and is visible. `[Integration]`
    - Tip: Given device date `2026-10-20`, a TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = false, id = "task-4", dueDate = 2026-10-23)` / When `setDueDateLabel` is called / Then the TextView text is `D-3` and visibility is `VISIBLE`.
  - An active task overdue by three days displays `D+3` and is visible. `[Integration]`
    - Tip: Given device date `2026-10-20`, a TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = false, id = "task-5", dueDate = 2026-10-17)` / When `setDueDateLabel` is called / Then the TextView text is `D+3` and visibility is `VISIBLE`.
  - A null task clears and hides any previous label. `[Integration]`
    - Tip: Given device date `2026-10-20` and a TextView with text `CLEAR` and visibility `VISIBLE` / When `setDueDateLabel` is called with task `null` / Then the TextView text is `""` and visibility is `GONE`.
- Error cases
  - None. A nullable task and every valid task are accepted inputs.
- Edge cases
  - When a completed task with no due date is changed to active, the prior `CLEAR` label is removed and hidden. `[Integration]`
    - Tip: Given device date `2026-10-20`, one TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = true, id = "task-6", dueDate = null)` / When `setDueDateLabel` is called / Then the TextView text is `CLEAR` and visibility is `VISIBLE`; set the same task's `isCompleted` to `false` and call `setDueDateLabel` again / Then the TextView text is `""` and visibility is `GONE`.
  - The previous calendar day displays `D+1` and is visible. `[Integration]`
    - Tip: Given device date `2026-10-20`, a TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = false, id = "task-7", dueDate = 2026-10-19)` / When `setDueDateLabel` is called / Then the TextView text is `D+1` and visibility is `VISIBLE`.
  - The next calendar day displays `D-1` and is visible. `[Integration]`
    - Tip: Given device date `2026-10-20`, a TextView with text `""` and visibility `GONE`, and `Task(title = "Task", description = "Description", isCompleted = false, id = "task-8", dueDate = 2026-10-21)` / When `setDueDateLabel` is called / Then the TextView text is `D-1` and visibility is `VISIBLE`.

**Undefined behavior** (optional)
- None.
