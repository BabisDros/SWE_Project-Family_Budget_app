# UC2 Account Management
Below is the use case describing how a family member’s account is managed.

### Primary Actor: Family Guardian

### Stakeholders:

**Family Guardian:** Wants to manage a family member’s account.

**Family Member:** Wants to gain access to the service, update their personal information, or have their account deleted.

### Preconditions
The guardian is logged into their account.

### Basic Flow
#### A) Account Creation 
1. The guardian enters a username.
2. The System checks that no other user has the same username.
3. The guardian enters a password.
4. The System verifies that the password meets the specifications.
5. The System stores the account details.

### Alternative Flows

\* At any point, the application crashes.  
1. The guardian restarts the application.  
2. The guardian logs into the system.  
3. The guardian restarts the process from step 1.

1–4a. The guardian may cancel the process.

2a. The system finds an account with the same username.  
1. The System displays an error message prompting the user to enter a different username.

4a. The password does not meet the specifications.  
1. The System displays an error message prompting the user to enter a new password.

#### B) Account Update 
1. The guardian searches for the member.
2. The System displays the member’s information.  
   * 2a. The member does not exist.  
   *    1. The System displays an error message.  
   *    2. The update process ends.
3. The guardian modifies the member’s details.
4. The System validates the updated information.  
   * 4a. The System finds an account with the same username.  
   *    1. The System displays an error message prompting the user to enter a new username.  
   * 4b. The password does not meet the specifications.  
   *    1. The System displays an error message prompting the user to enter a new password.
5. The System saves the member’s updated details.  
   * 1–3a. The guardian may cancel the update process.

#### C) Account Deletion 
1. The guardian searches for the member.
2. The guardian selects to delete the member.  
   * 2a. The member does not exist.  
   *    1. The System displays an error message.  
   *    2. The deletion process ends.
3. The System deletes the member’s information.  
   * 1–2a. The guardian may cancel the deletion.

## Sequence Diagram
![image](/docs/markdown/uml/requirements/en-US/uc2-sequence-diagram.jpg)