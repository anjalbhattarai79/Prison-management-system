# Prison Management System - Class Diagrams

## Complete System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    PRISON MANAGEMENT SYSTEM                                         │
└─────────────────────────────────────────────────────────────────────────────────────────────────────┘

                              ╔═════════════════════════════════╗
                              │      VIEW LAYER                 │
                              ║   (Swing GUI Components)        ║
                              ╚═════════════════════════════════╝
                                    │         │         │
                    ┌───────────────┼─────────┼─────────┬──────────────┐
                    │               │         │         │              │
            ╔───────▼────────╗  ╔──▼──╗  ╔──▼──╗  ╔───▼──╗      ╔────▼─────╗
            │  MainFrame     │  │TrashBin
            │  PrisonerDialog│  │Dialog  │  │View  │      │VisitRequest│
            │  DialogHelper  │  │        │  │Details
            │  TableButton   │  │        │  │Dialog │      │    Dialog   │
            │  Renderer/Editor           │  │       │      │             │
            └────────┬───────┘  └────────┘  └───────┘      └─────────────┘
                     │
                     │ creates/uses
                     ▼
            ╔═════════════════════════════════════╗
            │      CONTROLLER LAYER               │
            ║   Business Logic & Data Management  ║
            ╚═════════════════════════════════════╝
                     │                    
        ┌────────────┼────────────┬──────────────┬─────────────┐
        │            │            │              │             │
    ╔───▼────╗  ╔───▼────╗  ╔───▼────╗  ╔────▼───╗  ╔────▼────╗
    │PrisonControl    │CRUD │  │SimpleStack│  │SimpleQueue│  │Search/Sort
    │ler              │     │  │(Trash Bin)│  │(Recent)   │  │Operations
    └────────┘  └──────┘  └────────┘  └─────────┘  └──────────┘
        │           │         │           │             │
        └─────┬─────┴────┬────┴─────┬────┴──────┬──────┴──────┘
              │          │          │           │
              ▼          ▼          ▼           ▼
        ╔════════════════════════════════════════════════╗
        │         MODEL LAYER (Data Classes)            │
        ║      Business Objects & Data Structures       ║
        ╚════════════════════════════════════════════════╝
              │          │          │           │
        ┌─────▼──┬───────▼─┬───────▼─┬────────▼─────┐
        │         │         │         │              │
    ╔───▼──╗ ╔───▼──╗ ╔───▼──╗ ╔────▼────╗ ╔─────▼────╗
    │Prisoner
    │Model  │ │Activity
    │       │ │       │ │VisitRequest
    │       │ │    OperationResult
    │       │ │ ValidationResult
    └───────┘ └───────┘ └───────┘ └────────┘ └──────────┘
```

---

## Detailed Class Relationships

### 1. MODEL LAYER

```
┌──────────────────────────────────────────────────────────────────┐
│                    PrisonerModel                                  │
├──────────────────────────────────────────────────────────────────┤
│ - prisonerId: int                                                │
│ - name: String                                                   │
│ - age: int                                                       │
│ - gender: String                                                 │
│ - address: String                                                │
│ - crimeType: String                                              │
│ - crimeDescription: String                                       │
│ - admissionDate: LocalDate                                       │
│ - sentenceDuration: int                                          │
│ - releaseDate: LocalDate                                         │
│ - prisonLocation: String                                         │
│ - status: String {Active, Released, Transferred, Court Hearing}  │
│ - healthStatus: String                                           │
│ - familyCode: String                                             │
│ - photoPath: String                                              │
├──────────────────────────────────────────────────────────────────┤
│ + PrisonerModel(prisonerId, name, age, gender, ...)             │
│ + getPrisonerId(): int                                           │
│ + getName(): String / setName(String): void                     │
│ + getAge(): int / setAge(int): void                              │
│ + [Other getters/setters for all attributes]                    │
│ + getMonthsRemaining(): int                                      │
│ + getReleaseDate(): LocalDate                                    │
└──────────────────────────────────────────────────────────────────┘
```

```
┌────────────────────────────────────────────────────────┐
│                   Activity                              │
├────────────────────────────────────────────────────────┤
│ - action: String {ADDED, UPDATED, DELETED, RESTORED}  │
│ - prisonerName: String                                 │
│ - prisonerId: int                                      │
│ - timestamp: LocalDateTime                             │
├────────────────────────────────────────────────────────┤
│ + Activity(action, prisonerName, prisonerId)          │
│ + getAction(): String                                 │
│ + getPrisonerName(): String                           │
│ + getPrisonerId(): int                                │
│ + getTimestamp(): LocalDateTime                       │
│ + getFormattedTime(): String                          │
│ + toString(): String                                  │
└────────────────────────────────────────────────────────┘
```

```
┌──────────────────────────────────────────────────────────────────┐
│                   VisitRequest                                    │
├──────────────────────────────────────────────────────────────────┤
│ - requestId: int                                                 │
│ - prisonerId: int                                                │
│ - prisonerName: String                                           │
│ - visitorName: String                                            │
│ - relationship: String {Spouse, Parent, Child, Sibling, Other}  │
│ - preferredDate: LocalDate                                       │
│ - purpose: String                                                │
│ - status: String {Pending, Approved, Declined}                  │
│ - requestDateTime: LocalDateTime                                 │
│ - adminNotes: String                                             │
│ - nextRequestId: int (static)                                    │
├──────────────────────────────────────────────────────────────────┤
│ + VisitRequest(prisonerId, prisonerName, visitorName, ...)      │
│ + getRequestId(): int                                            │
│ + getPrisonerId(): int / setPrisonerId(int): void               │
│ + getStatus(): String / setStatus(String): void                │
│ + getAdminNotes(): String / setAdminNotes(String): void        │
│ + [Other getters/setters]                                       │
└──────────────────────────────────────────────────────────────────┘
```

```
┌─────────────────────────────────────────────────────────┐
│            OperationResult<T>                            │
├─────────────────────────────────────────────────────────┤
│ - success: boolean                                      │
│ - data: T                                               │
│ - errorMessage: String                                  │
├─────────────────────────────────────────────────────────┤
│ + isSuccess(): boolean                                  │
│ + getData(): T                                          │
│ + getErrorMessage(): String                             │
│ + static success(data: T): OperationResult<T>          │
│ + static failure(message: String): OperationResult<T>  │
└─────────────────────────────────────────────────────────┘
```

```
┌────────────────────────────────────────────────────────┐
│           ValidationResult                              │
├────────────────────────────────────────────────────────┤
│ - isValid: boolean                                     │
│ - fieldErrors: Map<String, String>                     │
├────────────────────────────────────────────────────────┤
│ + isValid(): boolean                                   │
│ + addError(field: String, message: String): void      │
│ + getErrors(): Map<String, String>                     │
└────────────────────────────────────────────────────────┘
```

---

### 2. CONTROLLER LAYER

```
┌───────────────────────────────────────────────────────────────┐
│              PrisonController                                  │
├───────────────────────────────────────────────────────────────┤
│ - prisonDetails: LinkedList<PrisonerModel>                    │
│ - recentlyAddedQueue: SimpleQueue                             │
│ - trashBin: SimpleStack                                       │
│ - recentActivities: SimpleQueue                               │
│ - visitRequests: LinkedList<VisitRequest>                     │
│ - nextPrisonerId: int                                         │
│ - MAX_ACTIVITIES: int = 10 (static)                           │
├───────────────────────────────────────────────────────────────┤
│ PRISONER OPERATIONS:                                          │
│ + addPrisoner(...): boolean                                   │
│ + updatePrisoner(...): boolean                                │
│ + deletePrisoner(id: int): boolean                            │
│ + getPrisonerById(id: int): PrisonerModel                     │
│ + getAllPrisoners(): LinkedList<PrisonerModel>               │
│ + getNextAvailableId(): int                                  │
│                                                                │
│ VISIT MANAGEMENT:                                             │
│ + addVisitRequest(request: VisitRequest): boolean            │
│ + approveVisitRequest(id: int): boolean                       │
│ + declineVisitRequest(id: int, notes: String): boolean       │
│ + getAllVisitRequests(): LinkedList<VisitRequest>            │
│ + getPendingRequests(): LinkedList<VisitRequest>             │
│                                                                │
│ TRASH/RESTORE:                                                │
│ + restoreLastDeleted(): boolean                               │
│ + emptyTrash(): void                                          │
│ + getTrashSize(): int                                         │
│                                                                │
│ ACTIVITY TRACKING:                                            │
│ + logActivity(action, name, id): void                        │
│ + getRecentActivities(): String                              │
│ + getActivityHistory(): LinkedList<Activity>                 │
│                                                                │
│ UI UTILITIES:                                                 │
│ + loadPrisonerToTable(table: JTable): void                   │
│ + loadSampleNepalData(): void                                │
└───────────────────────────────────────────────────────────────┘
         │ delegates to    │
         └────────┬────────┘
                  ▼
    ┌──────────────────────────────────────────────┐
    │           CRUD (static methods)               │
    ├──────────────────────────────────────────────┤
    │ CREATE:                                      │
    │ + addPrisoner(...): OperationResult<Integer> │
    │                                              │
    │ READ:                                        │
    │ + getPrisonerById(...): PrisonerModel        │
    │ + getAllPrisoners(...): LinkedList           │
    │ + getRecentActivities(...): String           │
    │                                              │
    │ UPDATE:                                      │
    │ + updatePrisoner(...): OperationResult       │
    │                                              │
    │ DELETE:                                      │
    │ + deletePrisoner(...): OperationResult       │
    │                                              │
    │ UTILITIES:                                   │
    │ + getNextAvailableId(...): int               │
    │ + duplicateCheck(...): boolean               │
    │ + loadSampleData(...): void                  │
    └──────────────────────────────────────────────┘
```

### 3. DATA STRUCTURE LAYER (Custom Implementations)

```
┌────────────────────────────────────────────────────┐
│          SimpleStack<T>                             │
├────────────────────────────────────────────────────┤
│ - elements: Object[]                               │
│ - top: int = -1                                    │
│ - MAX_SIZE: int = 5 (static)                      │
├────────────────────────────────────────────────────┤
│ + push(item: Object): void                         │
│ + pop(): Object [throws StackOverflow/Underflow]  │
│ + peek(): Object                                   │
│ + top(): Object (alias)                            │
│ + isEmpty(): boolean                               │
│ + size(): int                                      │
│ + clear(): void                                    │
│ + toArray(a: U[]): U[]                             │
└────────────────────────────────────────────────────┘
         ▲
         │ used by
         │
    PrisonController (trashBin)
```

```
┌──────────────────────────────────────────────────────┐
│        SimpleQueue<T>                                │
├──────────────────────────────────────────────────────┤
│ - items: ArrayList<Object>                          │
│ - head: int = 0                                     │
│ - rear: int = -1                                    │
├──────────────────────────────────────────────────────┤
│ + enqueue(item: Object): void                       │
│ + dequeue(): Object [throws QueueEmpty]            │
│ + peek(): Object                                    │
│ + front(): Object (alias)                           │
│ + rear(): Object                                    │
│ + isEmpty(): boolean                                │
│ + size(): int                                       │
│ + clear(): void                                     │
│ + toArray(a: U[]): U[]                              │
│ - compactIfNeeded(): void (private)                │
└──────────────────────────────────────────────────────┘
         ▲         ▲
         │         │ used by
         │         │
    PrisonController
    (recentlyAddedQueue)
    (recentActivities)
```

### 4. UTILITY/SERVICE LAYER

```
┌────────────────────────────────────────────┐
│    PrisonerValidator                        │
├────────────────────────────────────────────┤
│ STATIC VALIDATORS:                         │
│ + isValidName(name): boolean               │
│ + isValidAge(age): boolean                 │
│ + isValidGender(gender): boolean           │
│ + isValidAddress(address): boolean         │
│ + isValidCrime(crime): boolean             │
│ + isValidDate(date): boolean               │
│ + validatePrisoner(...): ValidationResult  │
└────────────────────────────────────────────┘
         │ used by
         ▼
      CRUD class
```

```
┌──────────────────────────────────────────────────┐
│         SessionManager                            │
├──────────────────────────────────────────────────┤
│ - currentUser: User                              │
│ - isLoggedIn: boolean                            │
│ - lastActivity: LocalDateTime                    │
├──────────────────────────────────────────────────┤
│ + login(username, password): boolean             │
│ + logout(): void                                 │
│ + getCurrentUser(): User                         │
│ + isSessionActive(): boolean                     │
│ + hasPermission(permission): boolean             │
└──────────────────────────────────────────────────┘
         │ used by
         ▼
   MainFrame (Authentication)
```

```
┌──────────────────────────────────────────────────┐
│      AuthenticationService                        │
├──────────────────────────────────────────────────┤
│ STATIC METHODS:                                  │
│ + authenticate(username, password): boolean      │
│ + validateCredentials(user): boolean             │
│ + changePassword(username, new): boolean         │
│ + resetPassword(username): String                │
└──────────────────────────────────────────────────┘
```

```
┌──────────────────────────────────────────────────┐
│       SearchOperation                             │
├──────────────────────────────────────────────────┤
│ STATIC SEARCH METHODS:                           │
│ + searchByName(prisoners, name): List             │
│ + searchById(prisoners, id): PrisonerModel       │
│ + searchByStatus(prisoners, status): List         │
│ + searchByCrime(prisoners, crime): List          │
│ + searchByLocation(prisoners, loc): List          │
│ + advancedSearch(...): List                       │
└──────────────────────────────────────────────────┘
```

```
┌──────────────────────────────────────────────────┐
│       SortOperation                               │
├──────────────────────────────────────────────────┤
│ STATIC SORT METHODS:                             │
│ + sortByName(prisoners): void                    │
│ + sortById(prisoners): void                      │
│ + sortByAge(prisoners): void                     │
│ + sortByAdmissionDate(prisoners): void           │
│ + sortByReleaseDate(prisoners): void             │
│ + sortByStatus(prisoners): void                  │
│ + reverseSorted(prisoners): void                 │
└──────────────────────────────────────────────────┘
```

```
┌────────────────────────────────────────┐
│      TrashBinOperation                  │
├────────────────────────────────────────┤
│ + restoreFromTrash(stack): Object      │
│ + emptyTrash(stack): void              │
│ + getTrashSize(stack): int             │
│ + getTrashContents(stack): Object[]    │
└────────────────────────────────────────┘
```

---

### 5. VIEW LAYER (Swing Components)

```
┌──────────────────────────────────────────────┐
│           MainFrame (JFrame)                  │
├──────────────────────────────────────────────┤
│ COMPONENTS:                                  │
│ - prisonersTable: JTable                     │
│ - searchField: JTextField                    │
│ - filterCombo: JComboBox                     │
│ - statusLabel: JLabel                        │
│                                              │
│ DEPENDENCIES:                                │
│ - controller: PrisonController               │
│ - sessionManager: SessionManager             │
├──────────────────────────────────────────────┤
│ + MainFrame()                                │
│ + setupUI(): void                            │
│ + loadPrisonersIntoTable(): void             │
│ + refreshTable(): void                       │
│ + searchPrisoners(): void                    │
│ + showAddDialog(): void                      │
│ + showEditDialog(id: int): void              │
│ + showDeleteConfirm(id: int): void           │
│ + showTrashBin(): void                       │
│ + showVisitRequests(): void                  │
└──────────────────────────────────────────────┘
         │ uses
         ├────────────┬──────────────┬──────────────┐
         │            │              │              │
         ▼            ▼              ▼              ▼
  PrisonerDialog  TrashBinDialog  ViewDetailsDialog
    DialogHelper   (JDialog)         (JDialog)
                                  VisitRequestsDialog
```

```
┌────────────────────────────────────────┐
│   PrisonerDialogHelper (JDialog)        │
├────────────────────────────────────────┤
│ INPUT COMPONENTS:                      │
│ - nameField: JTextField                │
│ - ageField: JSpinner                   │
│ - genderCombo: JComboBox               │
│ - addressArea: JTextArea               │
│ - crimeTypeCombo: JComboBox            │
│ - crimeDescArea: JTextArea             │
│ - admissionDate: JDateChooser          │
│ - sentenceField: JSpinner              │
│ - locationCombo: JComboBox             │
│ - familyCodeField: JTextField          │
│ - photoPath: JTextField                │
│ - statusCombo: JComboBox               │
├────────────────────────────────────────┤
│ + PrisonerDialogHelper(parent)         │
│ + showAddDialog(): PrisonerModel       │
│ + showEditDialog(prisoner): boolean    │
│ + validateInput(): boolean             │
│ + resetFields(): void                  │
└────────────────────────────────────────┘
         │ creates/returns
         ▼
   PrisonerModel
```

```
┌───────────────────────────────────────┐
│   TableButtonRenderer (JButton)        │
├───────────────────────────────────────┤
│ Component for rendering JTable cells   │
│ as interactive buttons                 │
├───────────────────────────────────────┤
│ + TableButtonRenderer()                │
│ + getTableCellRendererComponent(): *   │
└───────────────────────────────────────┘

┌────────────────────────────────────────┐
│   TableButtonEditor (AbstractCellEditor)
├────────────────────────────────────────┤
│ Component for editing JTable cells     │
│ as interactive buttons (View/Edit)     │
├────────────────────────────────────────┤
│ + TableButtonEditor()                  │
│ + getTableCellEditorComponent(): *     │
│ + getCellEditorValue(): Object         │
└────────────────────────────────────────┘
```

```
┌──────────────────────────────────────┐
│   TrashBinDialog (JDialog)            │
├──────────────────────────────────────┤
│ COMPONENTS:                          │
│ - trashTable: JTable                 │
│ - restoreButton: JButton             │
│ - emptyButton: JButton               │
├──────────────────────────────────────┤
│ + TrashBinDialog(parent, trashBin)  │
│ + loadTrashIntoTable(): void         │
│ + restoreSelected(): void            │
│ + emptyTrash(): void                 │
│ + refreshDisplay(): void             │
└──────────────────────────────────────┘
```

```
┌──────────────────────────────────────┐
│  ViewDetailsDialog (JDialog)          │
├──────────────────────────────────────┤
│ DISPLAY COMPONENTS:                  │
│ - prisonerDetails: JPanel            │
│ - photoLabel: JLabel                 │
│ - infoText: JTextArea                │
│ - activityLog: JTextArea             │
├──────────────────────────────────────┤
│ + ViewDetailsDialog(parent, prisoner)│
│ + displayPrisonerInfo(): void        │
│ + displayPhotoIfExists(): void       │
│ + showActivityLog(): void            │
│ + printDetails(): void               │
└──────────────────────────────────────┘
```

```
┌───────────────────────────────────────┐
│ VisitRequestsDialog (JDialog)         │
├───────────────────────────────────────┤
│ COMPONENTS:                           │
│ - requestsTable: JTable               │
│ - approveButton: JButton              │
│ - declineButton: JButton              │
│ - notesArea: JTextArea                │
│ - filterCombo: JComboBox              │
├───────────────────────────────────────┤
│ + VisitRequestsDialog(parent)        │
│ + loadRequests(): void                │
│ + showApproveDialog(id: int): void   │
│ + showDeclineDialog(id: int): void   │
│ + filterByStatus(status: String): void
│ + refreshRequests(): void             │
└───────────────────────────────────────┘
         │ reads/manages
         ▼
   VisitRequest
   (LinkedList in PrisonController)
```

---

## Relationship Summary

### Aggregation & Composition

```
PrisonController ⬥────────► PrisonerModel (LinkedList)
PrisonController ⬥────────► Activity (SimpleQueue)
PrisonController ⬥────────► VisitRequest (LinkedList)
PrisonController ⬥────────► SimpleStack (trashBin)
PrisonController ⬥────────► SimpleQueue (recentlyAdded)

CRUD ────►  PrisonerModel (static methods operate on)
CRUD ────►  Activity (creates/logs)
CRUD ────►  OperationResult (returns)

MainFrame ────►  PrisonController (delegates to)
MainFrame ────►  SessionManager (authentication)

[View Dialogs] ────►  PrisonerModel (display/edit)
[View Dialogs] ────►  VisitRequest (display/manage)
```

### Data Flow

```
USER INPUT
    ▼
MainFrame (View)
    ▼
PrisonController (Orchestration)
    ▼
┌─────────────────┬──────────────┐
▼                 ▼              ▼
CRUD          SearchOp      SortOp
(Business      (Business     (Business
 Logic)         Logic)        Logic)
    │              │             │
    └──────────────┼─────────────┘
                   ▼
         Model Layer
    (PrisonerModel, Activity,
     VisitRequest, etc.)
         
         PrisonerValidator
         (Validation)
         
         SessionManager
         (Auth/Sessions)
```

---

## Key Design Patterns Used

1. **MVC Pattern**: Separation of Model, View, and Controller
2. **Delegation Pattern**: MainFrame delegates to PrisonController; PrisonController delegates to CRUD
3. **Factory Pattern**: PrisonerDialogHelper creates PrisonerModel instances
4. **Singleton Pattern**: SessionManager (authentication)
5. **Custom Data Structures**: SimpleStack and SimpleQueue for specific requirements
6. **Utility/Service Layer**: Static methods for SearchOperation, SortOperation, TrashBinOperation

---

## Class Interaction Examples

### Adding a Prisoner
```
MainFrame (User clicks "Add")
    ↓
PrisonerDialogHelper.showAddDialog()
    ↓ (User fills form & clicks OK)
PrisonController.addPrisoner()
    ↓
CRUD.addPrisoner() [validates + creates]
    ↓
PrisonerModel created
    ↓ (Add to list)
prisonDetails.add(newPrisoner)
    ↓
Activity logged
    ↓
MainFrame.refreshTable()
```

### Searching Prisoners
```
MainFrame (User enters search term)
    ↓
SearchOperation.searchByName()
    ↓ (returns matching)
LinkedList<PrisonerModel>
    ↓
MainFrame.loadResultsToTable()
```

### Managing Trash Bin
```
MainFrame (User clicks "Trash Bin")
    ↓
TrashBinDialog opens
    ↓
TrashBinOperation.getTrashContents()
    ↓
SimpleStack contents → JTable display
    ↓
User selects item & clicks "Restore"
    ↓
TrashBinOperation.restoreFromTrash()
    ↓
SimpleStack.pop()
    ↓
Add back to prisonDetails
```

---

## Database-Like Behavior (In-Memory)

```
PrisonerController serves as:
┌─────────────────────────────────────────┐
│    In-Memory Data Store                  │
├─────────────────────────────────────────┤
│ prisonDetails (LinkedList)   ← PRIMARY  │
│ recentlyAddedQueue (Queue)   ← INDEX   │
│ trashBin (Stack)             ← ARCHIVE │
│ recentActivities (Queue)     ← LOG     │
│ visitRequests (LinkedList)   ← RELATED │
└─────────────────────────────────────────┘
```

---

## Access Control & Validation Flow

```
User Input
    ▼
PrisonerValidator.validatePrisoner()
    ▼
ValidationResult (success/errors)
    ▼
IF valid:
  CRUD.addPrisoner() → OperationResult
ELSE:
  Display validation errors
    ▼
OperationResult (success + data OR failure + message)
    ▼
MainFrame displays success/error to user
```

---

## Thread Safety & Session Management

```
SessionManager
    ├─ currentUser: authenticated user
    ├─ isLoggedIn: session state
    ├─ lastActivity: timestamp
    └─ Checks user permissions before operations

PrisonController
    └─ All CRUD operations should check
       session before proceeding
```

This represents your complete Prison Management System architecture!
