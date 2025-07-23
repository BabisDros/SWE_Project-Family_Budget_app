# UC8 Set Monthly Expense Limit per Category
Below is the use case where the guardian of the family sets the monthly expense limit per category.

### Primary actor: Guardian of family

### Stakeholders:

**Guardian of family:** Wants to set monthly limits per expense category.

**Family member:** Wants to know the monthly expense limit for a category.

**Preconditions**

The guardian is logged into their personal account and all data has been loaded correctly.

**Basic flow**

1. The System displays the available categories.
2. The guardian selects one of the available categories.
3. The guardian enters an amount as the monthly limit.
4. The guardian clicks save.

**Alternative flows**

* At any point the application crashes.
1. The member restarts the application.
2. The member logs in again.
3. The member starts the process from step 1.

3a. The member did not enter a number in the amount.
1. The application displays an appropriate error message.
2. The use case returns to the beginning of step 3.