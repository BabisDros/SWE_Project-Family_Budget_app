# UC7 Registration of Recurring Monthly Income/Expenses
Below is the use case in which a family member records a recurring monthly income or expense.

### Primary Actor: Family Member

### Stakeholders:

**Family Member:** Wants to be able to submit income and expenses that recur on a monthly basis.

### Preconditions

The member is logged into their personal account and all data has been loaded correctly.

### Basic Flow

1. The member selects "Εnter income".
2. The System prompts the member to choose the recurrence.
3. The member sets the recurrence to “Monthly.”
4. The System prompts for start/end month and recurrence period.
5. The user selects the start month, end month, and recurrence period.
6. The System prompts the member to enter the amount.
7. The member enters the amount.
8. The System confirms the entered amount.
9. The System prompts the member to select a category.
10. The member selects the category to which the amount belongs.
11. The System prompts the member to confirm saving.
12. The member selects “Save.”

### Alternative Flows

\* At any point, the application crashes.

1. The member restarts the application.  
2. The member logs in again.  
3. The member restarts the process from step 1.

1a. The member selects "Εnter expenses".

8a. The member did not enter a number as amount.  
1. The application shows an appropriate error message.  
3. The use case returns to the start of step 6.