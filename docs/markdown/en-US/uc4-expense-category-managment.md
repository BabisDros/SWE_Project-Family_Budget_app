# UC4 Expense Category Management
Below is the use case describing the management of expense categories by the family guardian.

### Primary Actor: Family Guardian

### Stakeholders:

**Family Guardian:** Wants to manage the available family expense categories.

**Family Member:** Uses the categories to organize expenses.

### Preconditions
The guardian is logged into their account.

### Basic Flow

#### A) Create Expense Category
1. The guardian chooses to create a new expense category.
2. The guardian enters the name of the new category.
3. The System checks if the name already exists.
4. The System saves the new category.

### Alternative Flows
\* At any point, the application crashes.  
1. The guardian restarts the application.  
2. The guardian logs in again.  
3. The guardian restarts the process from step 1.

2–4a: The guardian may cancel the entry.  
3b. The category name already exists.  
1. The System displays an error message.

#### B) Update Expense Category
1. The guardian chooses to update a category. 
2. The guardian selects the expense category to update.  
3. The guardian enters a new name.  
4. The System checks if the name already exists.  
5. The System saves the updated category.  

2–3a: The guardian may cancel the update.  
4b. The category name already exists.  
1. The System displays an error message.

#### C) Delete Expense Category
1. The guardian chooses to delete a category.
2. The guardian selects the expense category to delete.  
3. The System asks for confirmation of the category deletion.  
4. The guardian confirms the deletion.  
5. The System sets the category of existing expenses in the category to be deleted as undefined.  
6. The System deletes the category.  

2–4a: The guardian may cancel the deletion.

## Activity Diagram
![image](/docs/markdown/uml/requirements/en-US/uc4-activity-diagram.jpg)
