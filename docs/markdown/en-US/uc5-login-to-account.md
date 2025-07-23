# UC5 Login to Account
Below is the use case where a family member attempts to log into their account.

### Primary Actor: Family Member

### Stakeholders:

**Family Member:** Wants to securely log into their account, preventing access by unauthorized people, including other family members.

### Preconditions

The application has been started and has connection to the database.

### Basic Flow

1. The member enters their username and password into the application.  
2. The application checks the provided credentials.  
3. The application accepts the provided credentials.  
4. The application finds a matching record in its database.  
5. The application loads the member’s family data. 
6. The member gains access.

### Alternative Flows

\* At any point, the application crashes.

1. The member restarts the application.  
2. The member tries to log in again.

3a. The credentials contain forbidden characters.

1. The application notifies the user that the credentials contain unrecognized characters.  
2. It gives the user another chance to enter the credentials.

4a. The application finds no matching record in the database.

1. The application informs the user that either the username or the password is incorrect.  
2. It gives the user another chance to enter the credentials.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc5-activity-diagram.jpg)

## Sequence Diagram
![image](/docs/markdown/uml/requirements/en-US/uc5-sequence-diagram.jpg)