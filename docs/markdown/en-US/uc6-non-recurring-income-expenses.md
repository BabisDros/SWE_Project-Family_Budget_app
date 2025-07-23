# UC6 Non-recurring Income/Expenses
Below is the use case where a family member submits non-recurring income or expenses.

### Primary Actor: Family Member

### Stakeholders:

**Family Member:** Wants to be able to submit income and expenses that do not recur monthly.

### Preconditions

The member is logged into their personal account and all data has been loaded correctly.

### Basic Flow

1. The member selects "enter income".  
2. The System prompts the member to choose the recurrence.  
3. The member sets the recurrence to “Non‑recurring.”  
4. The System prompts the member to enter the amount.  
5. The member enters the amount.  
6. The System confirms the entered amount.  
7. The System prompts the member to select a category. 
8. The member selects the appropriate category. 
9. The System asks for confirmation to save. 
10. The member selects "save".

### Alternative Flows

\* At any point, the application crashes.

1. The member restarts the application.  
2. The member logs in again.  
3. The member restarts the process from step 1.

1a. The member selects "enter expenses".

6a. The member did not enter a number as amount.  
1. The application shows an appropriate error message.  
2. The use case returns to step 4.

10a. The member does not save.  
1. The use case ends without updating data.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc6-activity-diagram.jpg)

## Sequence Diagram
![image](/docs/markdown/uml/requirements/en-US/uc6-sequence-diagram.jpg)