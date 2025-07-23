# UC1 Family Registration
Below is the use case where a family is registered in the service.

### Primary Actor: Family Guardian

### Stakeholders:

**Family Guardian:** Wants to register the family in the service.

**Family Member:** Wants to gain access to the service.

**Preconditions:** We ignore the preconditions of UC2. The application has started, and no member has an account in the service.

### Basic Flow
1. The guardian selects the option to register for the service.
2. The System creates a family and assigns it a unique ID.
3. [The guardian creates an account using Account Management](uc2-account-management.md "Use Case Inclusion [# UC2 Account Management]/[Create Account]").
4. The System designates the account as a guardian account.
5. The System adds the guardian to the family.
6. The System prompts the guardian to add family members.
7. [The guardian selects to create an additional account](uc2-account-management.md "Use Case Inclusion [# UC2 Account Management]/[Create Account]").
8. The System designates the account as a member account.
9. The System adds the member to the family.
10. The System displays the accounts that were created.

The guardian repeats steps 6–9 for each family member.

### Alternative Flows

\* At any point, the application crashes.  
1. The guardian restarts the application.  
2. The guardian begins the process from step 1.

7a. The guardian chooses not to create additional accounts.  
   1. The use case ends.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc1-activity-diagram.jpg).

## Sequence Diagram
![image](/docs/markdown/uml/requirements/en-US/uc1-sequence-diagram.jpg).