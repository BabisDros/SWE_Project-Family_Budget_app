# UC10 Budget Viewing
Below is the use case where a family member views their individual or family budget.

### Primary actor: Family member

### Stakeholders:

**Family member:** Wishes to see their individual or family budget on a monthly or yearly basis.

### Preconditions

The member is logged into their personal account and all data has been loaded correctly.

### Basic flow

#### View individual income.
1. The member selects "Personal budget".
2. The member selects the basis (monthly or yearly) on which they want to view the data.
3. The member selects the subcategory "income" for more detailed information.

### Alternative flows

* At any point the application crashes.

1. The member restarts the application.
2. The member logs in again.
3. The member starts the process from step 1.

1a. The member selects "Family budget".

3a. The member selects the subcategory "expenses" for more detailed information.

3b. The member selects the subcategory "surplus" for more detailed information.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc10-activity-diagram.jpg)