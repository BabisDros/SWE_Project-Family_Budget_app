# UC9 Moneybox Management
Below is the use case in which the moneybox is managed.

### Primary actor: Family member

### Stakeholders:

**Family member:** Wants to create a moneybox for making a purchase.

### Preconditions
The member is logged into their account.

### Basic flow
#### A) Create Moneybox
1. The member enters a moneybox name.
2. The System confirms that no moneybox exists with the same name.
3. The member enters the amount.
4. The System saves the moneybox details.

### Alternative flows

* At any point the application crashes.
1. The member restarts the application.
2. The member logs into the system.
3. The member starts the process from step 1.

1-3a. The member may cancel the process.

2a. The name already exists.
1. The System displays an error message prompting for a new name.

#### B) Update Moneybox
1. The member searches for the moneybox.
2. The system presents the moneybox details.
   * 2a. The moneybox does not exist.
   *    1. The system displays an error message.
   *    2. The Update terminates.
3. The member modifies the moneybox details.
4. The system confirms the correctness of the details.
   * 4a. The name already exists.
   *    1. The System displays an error message prompting for a new name.
5. The system saves the moneybox details.
   * 1-3a. The member may cancel the Update.

#### C) Delete Moneybox
1. The member searches for the moneybox.
2. The member selects deletion of the moneybox.
   * 2a. The moneybox does not exist.
   *    1. The system displays an error message.
   *    2. The Deletion terminates.
3. The system deletes the moneybox details.
   * 1-2a. The member may cancel the Deletion.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc9-activity-diagram.jpg).

## Sequence Diagram
![image](/docs/markdown/uml/requirements/en-US/uc9-sequence-diagram.jpg).