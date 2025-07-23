# UC11 View Public Statistics
Below is the use case where a member can view public statistics concerning all families.

### Primary actor: Family member

### Stakeholders:

**Family member:** Wants to see statistics of all families.

### Preconditions
The member is logged into their account.

### Basic flow
1. The member requests the public statistics.
2. The System displays the categories of statistics.
3. The member selects a category.
4. The System displays the statistics.

### Alternative flows
* At any point the application crashes.
1. The member restarts the application.
2. The member logs in again.
3. The member starts the process from step 1.

2a. No statistics are available.
1. The System displays an error message.
2. The use case terminates.

3-4a. The member may cancel the process.
1. The System returns to step 2.