# UC3 Surplus Management
Below is the use case describing the management of the family surplus by the family guardian.

### Primary Actor: Family Guardian

### Stakeholders:

**Family Guardian:** Wants to manage the family surplus.

**Family Member:** Needs the surplus to make a purchase.

### Preconditions
The guardian is logged into their account.

### Basic Flow
1. The guardian requests the available surplus.
2. The System displays the available surplus.
3. The guardian requests the available moneyboxes.
4. The System displays the available moneyboxes.
5. The guardian selects a moneybox.
6. The System prompts for the amount to add.
7. The guardian enters the amount and confirms the addition.
8. The System deducts the amount from the surplus and adds it to the moneybox.

The guardian repeats steps 5–8 until the surplus is zero.

### Alternative Flows
\* At any point, the application crashes.  
1. The guardian restarts the application.  
2. The guardian restarts the process from step 1.

2a. No available surplus.  
1. The System displays an error message.  
2. The use case terminates.

4a. No available moneyboxes.  
1. The guardian allocates the surplus as savings.  
2. The use case terminates.

8a. The remaining surplus is insufficient.  
1. The System displays an error message.  
2. The use case returns to step 7.

5–7a. The guardian may cancel the process.  
1. The System returns to step 4.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc3-activity-diagram.jpg)

## Sequence Diagram
![image](/docs/markdown/uml/requirements/en-US/uc3-sequence-diagram.jpg)
