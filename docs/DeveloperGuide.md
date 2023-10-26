---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# Flagship Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`InternshipLogic`**](#logic-component): The command executor.
* [**`InternshipModel`**](#model-component): Holds the data of the App in memory.
* [**`InternshipStorage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `InternshipListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Internship` object residing in the `Model`.

### Logic component

**API** : [`InternshipLogic.java`](https://github.com/AY2324S1-CS2103T-W17-1/tp/blob/master/src/main/java/seedu/address/logic/InternshipLogic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `InternshipBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `InternshipModel` when it is executed (e.g. to delete an internship).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `InternshipBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `CreateCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `CreateCommand`) which the `InternshipBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `CreateCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `InternshipModel` component,

* stores the address book data i.e., all `Internship` objects (which are contained in a `UniqueInternshipList` object).
* stores the currently 'selected' `Internship` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Internship>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `InternshipModel` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Requirement` list in the `InternshipBook`, which `Internship` references. This allows `InternshipBook` to only require one `Requirement` object per unique requirement, instead of each `Internship` needing their own `Requirement` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `InternshipStorage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `InternshipBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `InternshipModel` component (because the `InternshipStorage` component's job is to save/retrieve objects that belong to the `InternshipModel`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th internship in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `create c/[COMPANY_NAME] ro/[ROLE] a/[APPLICATION_STATUS] de/[DEADLINE] s/[START_DATE] d/[DURATION] r/[REQUIREMENT]…​` to add a new internship. The `create` command also calls `InternshipModel#commitInternshipBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the internship was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the internship being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_


### Create command

#### Implementation

The create command is facilitated by `InternshipLogicManager`. User input is first parsed by `InternshipBookParser#parseCommand()` and checked if it is a `create` command with a valid format.
Upon successful verification, the `create` command is executed. The internship entry's `COMPANY_NAME` and `ROLE` is checked for potential duplicates in the existing database managed by `InternshipStorage`. 
If none is found, the internship entry is successfully created and stored in `InternshipStorage`.

The create command is exposed in the `InternshipModel` interface as `InternshipModel#createInternship`.

Given below is an example usage scenario and how the create command behaves at each step.

Step 1. The user inputs `create c/Jane Street ro/Coffee maker a/Yet to apply de/25/12/2022 s/20/01/2023 du/3 re/C++ re/Coffee` 
and it is parsed by `InternshipBookParser` to verify that it has the valid format of a `create` command.

<puml src="diagrams/CreateCommandParse.puml" alt="CreateCommandParse" />

<box type="info" seamless>

**Note:** If the command does not follow the valid format of a `create` command, a ParseException will be thrown if the 
command does not correspond to any possible command formats, and we will not proceed to step 2. If it corresponds to the 
format of another valid command (that is not `create`), subsequent execution in step 2 will follow the logic flow of 
the other corresponding command.

</box>

Step 2. The `create` command is executed. If there does not exist a duplicate entry in `InternshipStorage`, the internship 
entry is created successfully.

<puml src="diagrams/CreateCommandExecute.puml" alt="CreateCommandExecute" />

<box type="info" seamless>

**Note:** If there exists a duplicate entry, a CommandException will be thrown, and we will not proceed to step 3.

</box>

Step 3. The internship entry is stored in `InternshipStorage`.

<puml src="diagrams/CreateCommandStore.puml" alt="CreateCommandStore" />

#### Design considerations:

**Aspect: What constitutes a duplicate internship entry:**

* **Alternative 1 (current choice):** Case-sensitive, identical `COMPANY_NAME` and `ROLE` is individually sufficient
    * Pros: Easy to manage and debug
    * Cons: Does not label duplicates in the strict equality sense 

* **Alternative 2:** Case-sensitive, identical attributes across all fields are necessary for an entry to be classified a duplicate
    * Pros: Label duplicates in the strictest possible sense 
    * Cons: Most accidental duplicate entries need not resemble one another completely across all attributes.

### Sort feature

### Sort Implementation

The sort mechanism is facilitated by InternshipBook. InternshipBook provides us with the field of currentComparator, which stores the current sorting order of the lists. Additionally, it provides the main sort operation:

- `UniqueInternshipList#sortInternships(comparator)` — Sorts the current internship list using the comparator.
- `InternshipModelManager#updateSortComparator(comparator)` — Updates currentComparator to save the most recent sorted order.

<box type="info" seamless>

**Note:** The currentComparator takes on a default value of BY_COMPANY_NAME. The command `sort default` is equivalent to `sort co/ASC`.

</box>

The `UniqueInternshipList#sortInternships(comparator)` is called from the `InternshipModelManager#sortInternships(comparator)` method.

Given below is an example usage scenario and how the sort mechanism behaves at each step.

Step 1. The user launches the application and already has a bunch of internships listed. The currentComparator is in its default value.

<puml src="diagrams/SortCommandState0.puml" alt="SortCommandState0" />

<box type="info" seamless>

**Note:** This diagram shows the high level diagram with the intermediate layers hidden. It is meant to show the order of the internships within the list.

</box>

Step 2. The user inputs `sort ro/ASC` (in the format of [CATEGORY]/[ASC/DESC]) and it is parsed by `InternshipBookParser` to verify that it has the valid format of a `sort` command.

<puml src="diagrams/SortCommandParse.puml" alt="SortCommandParse" />

<box type="info" seamless>

**Note:** If the command does not follow the valid format of a `sort` command, a ParseException will be thrown if the
command does not correspond to any possible command formats, and we will not proceed to step 3. If it corresponds to the
format of another valid command (that is not `sort`), subsequent execution in step 3 will follow the logic flow of
the other corresponding command.

</box>

Step 3. The `sort` command is executed. The currentComparator field in InternshipBook is updated and `UniqueInternshipList#sortInternships(comparator)` is called.

<puml src="diagrams/SortCommandState1.puml" alt="SortCommandState1" />

<box type="info" seamless>

**Note:** Once the currentComparator field is updated, any other operations on InternshipBook that edits the internship list will also call the `UniqueInternshipList#sortInternships(comparator)` command and sort the internship list after the update.
This diagram shows the high level diagram with the intermediate layers hidden. It is meant to show the order of the internships within the list.

</box>

The following sequence diagram shows how the sort operation works:

<puml src="diagrams/SortSequenceDiagram.puml" alt="SortSequenceDiagram" />

#### Design considerations:

**Aspect: How we retain the sort order:**

* **Alternative 1 (current choice):** Keep track of the sorting and sort after every change made to internship book.
    * Pros: Ensures the internship list remains sorted after modifications.
    * Cons: Increases time complexity of operations.

* **Alternative 2:** Ignore the current sorted order after a change is made
    * Pros: Will reduce the complexity of operations as operations do not need to sort after making changes to the internship list.
    * Cons: Will cause the updated list to be no longer sorted.

### Filter feature

### Filter Implementation

The `filter` command is facilitated by  `InternshipLogicManager`. User input is first parsed by `InternshipBookParser#parseCommand()` and checked if it is a filter command with a valid format. Once the format is validated, the predicate required to filter the internships based on user criteria is generated.

This predicate is then used to update the filtered internship list in the `InternshipModel`. The filtered list will only contain internships that satisfy the conditions specified by the user.

The filter command is exposed in the InternshipModel interface as `InternshipModel#updateFilteredInternshipList`.

Given below is an example usage scenario and how the filter command behaves at each step.

Step 1. The user launches the application and already has a bunch of internships listed. 

<puml src="diagrams/FilterCommandState0.puml" alt="FilterCommandState0" />

<box type="info" seamless>

**Note:** This diagram shows the high level diagram with the intermediate layers hidden. It is meant to show the order of the internships within the list.

</box>

Step 2.The user inputs `filter c/[COMPANY_NAME]` (in the format of [CATEGORY]/[KEYWORDS] for categories such as company name, role, application status, requirements) or `filter du/[DURATION_A]-[DURATION_B]` (in the format of [CATEGORY]/[KEYWORD]-[] for categories such as start date, duration, and deadline) and it is parsed by `InternshipBookParser` to verify that it has the valid format of a `sort` command.

<puml src="diagrams/FilterCommandParse.puml" alt="FilterCommandParse" />

<box type="info" seamless>

**Note:** If the command does not follow the valid format of a `filter` command, a ParseException will be thrown if the
command does not correspond to any possible command formats, and we will not proceed to step 3. If it corresponds to the
format of another valid command (that is not `filter`), subsequent execution in step 3 will follow the logic flow of
the other corresponding command.

</box>

Step 3. The `filter` command is executed. The predicate generated based on user criteria is used to filter internships in the `InternshipModel`.

<puml src="diagrams/FilterCommandExecute.puml" alt="FilterCommandExecute" />
<box type="info" seamless>

**Note:** The filtered list in the `InternshipModel` is updated to only include internships that satisfy the conditions specified by the predicate.

</box>

#### Design considerations:
**Aspect: How the filter conditions are specified:**

* **Alternative 1 (current choice):** Use a predicate-based approach to allow flexible filtering criteria.
    * Pros: Allows a dynamic and flexible way to filter internships based on various criteria.
    * Cons: Might be more complex than a simpler, fixed-criteria approach.
  
* **Alternative 2:** Use a fixed set of filter criteria.
    * Pros: Simpler to implement.
    * Cons: Less flexible and might not cover all use cases or user needs.

### Modify feature

### Modify Implementation

The `modify` command is facilitated through the `InternshipLogicManager`. Upon user input, it first goes through parsing by the `InternshipBookParser#parseCommand()`. This is to ensure that the input is indeed a `modify` command and that it adheres to a valid format.

Once it is verified as a legitimate command, the `modify` function is initiated. An essential part of this process is checking the new internship entry's `COMPANY_NAME` and `ROLE` against potential duplicates in the current `InternshipStorage` database. Notably, while doing this check, the original internship entry's `COMPANY_NAME` and `ROLE` are ignored. If no duplicate is identified, the internship entry is edited and then stored within `InternshipStorage`.

Developers can access the modify command through the `InternshipModel` interface via the `InternshipModel#modifyInternship` function.

Below is an example that demonstrates the modify command's behavior step-by-step:

Step 1: A user provides the command `modify 1 c/Jane Street ro/Coffee maker a/Yet to apply de/25/12/2022 s/20/01/2023 du/3 re/C++ re/Coffee`. This command is parsed by `InternshipBookParser` to validate if it is structured correctly for a `modify` command.

<puml src="diagrams/ModifyCommandParse.puml" alt="ModifyCommandParse" />

<box type="info" seamless>

**Note:** In cases where the command doesn't adhere to the valid `modify` command format, a `ParseException` gets triggered. If the command doesn't fit any recognized command patterns, we don't transition to step 2. However, if it does match another valid command format (other than `modify`), the logic for that other command is then executed in step 2.

</box>

Step 2: Post validation, the `modify` command is executed. If no duplicate entry exists in `InternshipStorage`, the editing of the internship entry goes through successfully.

<puml src="diagrams/ModifyCommandExecute.puml" alt="ModifyCommandExecute" />

<box type="info" seamless>

**Note:** When a duplicate entry is detected, a `CommandException` is raised, preventing progression to step 3.

</box>

Step 3: After successful editing, the internship entry is saved into `InternshipStorage`.

<puml src="diagrams/ModifyCommandStore.puml" alt="ModifyCommandStore" />

The following sequence diagram shows how the modify operation works:

<puml src="diagrams/ModifySequenceDiagram.puml" alt="ModifySequenceDiagram" />

#### Design considerations:

**Aspect: How should we handle fields that aren't provided in the command?**

* **Alternative 1 (current choice):** Only modify the fields specified in the command, while retaining the original values of other fields.
    * Pros: Enables us to fetch the original internship data and modify only the intended fields without rebuilding the entire internship entry.
    * Cons: Retrieving the original internship entry might introduce extra complexity to the codebase.

* **Alternative 2:** Replace the entire internship entry with the index and details given in the command.
    * Pros: Simplifies the process as it can reuse code from both delete and create commands.
    * Cons: The command must encompass all required fields, even if the modification pertains only to a single field or a minor typo.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* is a college student or recent graduate actively looking for internship opportunities.
* values the importance of being organized and keeping track of their internship applications' progress.
* wants a centralized platform to manage their internship applications and related information.
* can type fast 
* is reasonably comfortable using CLI apps

**Value proposition**: organize and manage internship applications efficiently through a command-line interface, ensuring that no opportunities are missed and applications are tracked systematically.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                | I want to …​                                                       | So that I can…​                                         |
|----------|----------------------------------------|--------------------------------------------------------------------|---------------------------------------------------------|
| `* * *`  | new user                               | create new internship entries                                      | store internship entries in Flagship                    |
| `* * *`  | user keeping track of my internships   | modify the details of my internship entries                        | be aware of the status                                  |
| `* * *`  | user                                   | delete my internship entries                                       | declutter my workspace                                  |
| `* * *`  | user                                   | view all internship entries                                        | keep track of the details of current internship entries |
| `* *`    | user keeping track of many internships | be notified when I enter a duplicate internship entry              | keep my list free of duplicate information              |
| `*`      | returning user                         | continue with my last accessed data file or choose a different one | manage multiple datasets concurrently                   |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `InternshipBook` and the **Actor** is the `user`, unless specified otherwise)


**UC1: Create an internship entry**

**MSS**

1.  User requests to create an internship entry with the necessary details
2.  InternshipBook adds the internship entry to the list

**Extensions**
* 1a. Command is of invalid format
    * 1a1. InternshipBook shows an error message.

    Use case ends.

**UC2: Delete an internship**

**MSS**

1.  User requests to see list of internships
2.  InternshipBook shows a list of internships
3.  User requests to delete a specific internship in the list
4.  InternshipBook deletes the internship

    Use case ends.

**Extensions**
* 1a. Command is of invalid format
    * 1a1. InternshipBook shows an error message.
  
    Use case ends.

* 3a. Command is of invalid format
    * 3a1. InternshipBook shows an error message.
    
    Use case ends.

**UC3: Edit an internship**

**MSS**

1.  User requests to read internship entry
2.  InternshipBook shows the internship entry
3.  User requests to edit a certain field of the internship entry
4.  InternshipBook shows the internship entry with the changes made
5.  User requests to exit editing mode
6.  InternshipBook saves the updated internship entry to the hard disk

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. InternshipBook shows an error message.

      Use case resumes at step 2.

*{More to be added}*

### Non-Functional Requirements

#### Technical

* Should work on any _mainstream OS_ as long as it has Java `11` or above installed.

#### Performance

* Should be able to hold up to 1000 internships without a noticeable sluggishness in performance for typical usage.
* Should respond to user commands within 2 seconds under normal conditions.

#### Usability

* A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
* A new user should be able to grasp the basic features within 10 minutes.

#### Security and Data Integrity

* Should encrypt user data both during transfer and when stored.
* Should authenticate user based on username.

*{More to be added}*

### Glossary

* **Internship entry**: An entry in Flagship with information regarding the internship, including company name, role, application status, start date, duration, and role requirements
* **Application status**: The current status of the internship entry (Yet to apply, Applied, In Progress, Accepted, Rejected)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting an internship

1. Deleting an internship while all internships are being shown

   1. Prerequisites: List all internships using the `list` command. Multiple internships in the list.

   1. Test case: `delete 1`<br>
      Expected: First internship is deleted from the list. Details of the deleted internship shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No internship is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
