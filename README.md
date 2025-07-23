# Family Budget Manager App

An Android application for family budget planning, designed and developed as a group project for the Software Engineering course at the Athens University of Economics and Business (AUEB), applying software engineering principles and processes.



## Highlights

- **Iterative Development**: Four release cycles (R1–R4) following an iterative and incremental model.
- **Object‑Oriented Design**: Comprehensive UML diagrams (use‑case, class, activity, sequence) guiding Java domain logic.
- **Quality Assurance via JUnit**: JUnit tests covering domain logic and Presenter class, with coverage reports to ensure > 80% code coverage.
- **Native Android**: Basic UI, native built in Android Studio, in-memory storage.
- **Patterns implementation**: [Strategy](/FamilyBudgetApp/app/src/main/java/com/android/familybudgetapp/view/budget/cashFlowManager/CashFlowManagerInterface.java), [Template](/FamilyBudgetApp/app/src/main/java/com/android/familybudgetapp/view/membersManagement/BaseUserManagementPresenter.java#L59), [Factory Method](/FamilyBudgetApp/app/src/main/java/com/android/familybudgetapp/view/base/BaseViewModel.java#L22).


## Tech Stack & Tools

| Layer              | Technology / Tool                               |
| ------------------ | ----------------------------------------------- |
| Language           | Java                                            |
| IDE                | Android Studio                                  |
| Version Control    | GitLab                                          |
| UML Modeling       | UMLet standalone                                |
| Testing            | JUnit                                           |
| Coverage & Metrics | Android Studio Code Coverage                    |
| Documentation      | Markdown, JavaDoc                               |


## Architecture & UML diagrams

1. **R1 – Problem Domain**

   - Software requirements & Use Case Diagram  
     [Software Requirements](/docs/markdown/en-US/r1-software-requirements.md)  
     [Use case Diagram](/docs/markdown/uml/requirements/en-US/r1-use-case-diagram.jpg)

2. **R2 – System Analysis**

   - Detailed Use Case Descriptions  
     [Analysis](/docs/markdown/en-US/r2.md)  
     [Non-functional requirements](/docs/markdown/en-US/non-functional-requirements.md)  
     [Domain model](/docs/markdown/uml/requirements/domain-model.jpg)  
     [Analysis classes](/docs/markdown/uml/requirements/analysis-classes.jpg)

3. **R3 – Domain Logic**

   - Design Class Diagram for core business objects  
     [Class diagram](/docs/markdown/uml/design/class-diagram.jpg)  
     [Methods Sequence diagrams](/docs/markdown/en-US/r3-methods-sequence-diagrams%20.md)  
     [Coverage Report](/docs/coverage-reports/domain-coverage.png)

4. **R4 – System Design and Implementation**

   - UI layer class & sequence diagrams  
     [Class diagrams of Activities](/docs/markdown/en-US/r4-class-diagrams.md)  
     [Sequence diagrams of Activities](/docs/markdown/en-US/r4-sequence-diagrams.md)  
     [Full Coverage Report](/docs/coverage-reports/coverage-main.png)

## How to Run

To run this Android project locally:

### Requirements
- Android Studio (Ladybug or newer)

### Steps

1. Download the repository.
2. Open the project folder `FamilyBudgetApp` in Android Studio.
3. Build and run the app.
4. Use the following login credentials:
   
* Guardian Account (Full functionality)
  - **Username:** `usernameTest`  
  - **Password:** `passwordTest`
* Member Account  (Minimum functionality)
  - **Username:** `usernameTest2`  
  - **Password:** `passwordTest2`

## Contributors
- [Babis Drosatos](https://github.com/BabisDros)
- [Iosif Petroulakis](https://github.com/Morthlog)
- [Fanis Vlahogiannis](https://github.com/fanisvl)


## Examiner
- [Vassilis Zafeiris ](https://github.com/bzafiris)