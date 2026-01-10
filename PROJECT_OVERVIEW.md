# Prison Management System - Complete Project Overview

## 📚 **Aim of the Project**

**The aim of this project is to design and implement an educational Prison Management System that demonstrates the practical application of core Data Structures and Algorithms concepts in solving real-world data management problems within a realistic administrative context.**

---

## 🎯 **Objectives of the Project**

1. **To implement and demonstrate core data structures** (LinkedList, Queue, and Stack) in a practical prisoner record management system

2. **To apply and compare search algorithms** (Linear Search and Binary Search) for efficient data retrieval and understand their time complexity differences

3. **To develop a complete CRUD system** with comprehensive input validation, error handling, and data integrity mechanisms

4. **To practice Model-View-Controller (MVC) architectural pattern** for clean code organization and separation of concerns

5. **To analyze algorithm performance and make informed decisions** about appropriate data structure selection based on operational requirements

---

## 📚 **Primary Educational Focus**

### **Learning Data Structures and Algorithms**

This project is **primarily an educational exercise** designed to demonstrate understanding and practical application of **Data Structures and Algorithms (DSA)** concepts taught in CS5005 Data Structures and Specialist Programming course.

**Key Learning Goal:** To understand how theoretical data structure concepts translate into real-world software solutions.

---

## 🎯 **Why Prison Management System?**

A Prison Management System (PMS) was chosen as the **practical implementation domain** because it provides:

1. **Real-world complexity** that requires thoughtful data structure selection
2. **Multiple operational scenarios** (add, search, update, delete, sort, track, recover)
3. **Clear demonstration opportunities** for different data structures:
   - **LinkedList** for dynamic prisoner records
   - **Queue** for tracking recent activities (FIFO behavior)
   - **Stack** for undo/trash bin functionality (LIFO behavior)
   - **Search algorithms** (Linear and Binary)
   - **Sorting algorithms** (multiple approaches)

4. **Practical relevance** - addresses real information management challenges in correctional facilities
5. **Comprehensive CRUD operations** - demonstrates full lifecycle data management

**Important:** The domain (Prison Management) is the vehicle, not the destination. The destination is **mastering data structures**.

---

## 🧩 **What This Project Demonstrates**

### **1. Core Data Structures Implementation**

#### **LinkedList<PrisonerModel>**
- **What it is:** A sequence of connected nodes where each prisoner record points to the next
- **Why used here:** 
  - Dynamic size - can grow/shrink as prisoners are added/removed
  - Efficient insertion and deletion operations
  - Natural fit for sequential data processing
- **Real-world application:** Main storage for all prisoner records

#### **Queue<PrisonerModel> (using LinkedList)**
- **What it is:** First-In-First-Out (FIFO) data structure
- **Why used here:**
  - Track recent additions in chronological order
  - Automatically maintains the 5 most recent operations
  - Demonstrates FIFO principle
- **Real-world application:** Activity log showing recent prisoner additions

#### **Stack<PrisonerModel>**
- **What it is:** Last-In-First-Out (LIFO) data structure
- **Why used here:**
  - Implements undo functionality
  - Most recently deleted item can be recovered first
  - Demonstrates LIFO principle
- **Real-world application:** Trash bin/recovery system for deleted records

### **2. Algorithm Implementations**

#### **Search Algorithms**
- **Linear Search:**
  - Searches through list sequentially
  - Works on unsorted data
  - Time complexity: O(n)
  - Used for: Searching by name, crime type, status, location
  
- **Binary Search:**
  - Divides search space in half each iteration
  - Requires sorted data
  - Time complexity: O(log n) - much faster!
  - Used for: Searching by ID in sorted list

#### **Sorting Algorithms**
- Sort prisoner records by:
  - ID (ascending/descending)
  - Name (alphabetical)
  - Age (youngest/oldest first)
  - Admission Date (newest/oldest first)
  - Sentence Duration (shortest/longest first)
- Demonstrates: Comparison-based sorting and algorithm application

### **3. Object-Oriented Programming (OOP)**

#### **Model-View-Controller (MVC) Architecture**
- **Model** (PrisonerModel.java): Data representation
- **View** (MainFrame.java, GUI classes): User interface
- **Controller** (PrisonController.java, CRUD.java): Business logic

#### **Separation of Concerns**
- Each class has a single, well-defined responsibility
- CRUD operations isolated in dedicated class
- Search operations in separate class
- Sort operations in separate class

### **4. Software Engineering Practices**

- **Input Validation:** Comprehensive data validation with regex patterns
- **Error Handling:** Try-catch blocks and user-friendly error messages
- **Code Documentation:** Detailed comments and JavaDoc
- **Console Logging:** Debugging and operation tracking
- **Clean Code:** Readable, maintainable, well-structured code

---

## 🖥️ **What The System Does (User Perspective)**

### **Core Features:**

#### **1. Add New Prisoner**
- Enter prisoner details (name, age, gender, address, crime information, etc.)
- System automatically generates unique ID
- Auto-generates family access code (e.g., FAM123)
- Validates all inputs (no invalid characters, age ranges, etc.)
- Calculates release date based on sentence duration

#### **2. View All Prisoners**
- Displays all prisoners in a table format
- Shows: ID, Name, Age, Gender, Crime Type, Admission Date, Sentence, Status
- Each row has Edit and Delete buttons
- Easy-to-read, organized presentation

#### **3. Search Prisoners**
- **Search by:** ID, Name, Crime Type, Status, or Location
- **Two methods:**
  - Linear Search (works on any data)
  - Binary Search (faster, requires sorting by ID first)
- Instantly filters and displays matching records

#### **4. Sort Prisoners**
- **Sort by:** ID, Name, Age, Admission Date, or Sentence Duration
- **Order:** Ascending or Descending
- Results update instantly in the table

#### **5. Edit Prisoner Information**
- Click Edit button on any prisoner row
- Modify any field (name, age, status, crime details, etc.)
- All validations still apply
- Updates reflect immediately

#### **6. Delete Prisoner**
- Click Delete button on any prisoner row
- Confirmation dialog prevents accidental deletion
- Deleted prisoner goes to trash bin (Stack)
- Can be recovered using Restore function

#### **7. Restore Deleted Prisoner**
- Recovers the most recently deleted prisoner
- Pulled from trash bin (Stack - LIFO)
- Prisoner returned to main list with all data intact

#### **8. View Recent Activities**
- Shows last 5 prisoners added to the system
- Displays in a separate dialog
- Helps track recent changes

### **Sample Workflow:**

```
1. User adds new prisoner "John Doe"
   → System generates ID: 123
   → Family code: FAM123
   → Stored in LinkedList
   → Added to Queue (recent activities)

2. User searches for "John Doe"
   → Linear search through LinkedList
   → Displays matching record

3. User accidentally deletes John Doe
   → Removed from LinkedList
   → Pushed to Stack (trash bin)

4. User realizes mistake and clicks "Restore"
   → Popped from Stack
   → Added back to LinkedList

5. User views recent activities
   → Queue shows last 5 additions including John Doe
```

---

## 🔧 **Technical Architecture**

### **Package Structure:**
```
Prison-Management-System/
├── src/
│   ├── model/
│   │   └── PrisonerModel.java          (Data entity)
│   ├── view/
│   │   ├── MainFrame.java              (Main GUI)
│   │   ├── PrisonerDialogHelper.java   (Add/Edit dialogs)
│   │   ├── TableButtonRenderer.java    (Button display)
│   │   └── TableButtonEditor.java      (Button actions)
│   └── controller/
│       ├── PrisonController.java       (Main controller)
│       ├── CRUD.java                   (Create, Read, Update, Delete)
│       ├── SearchOperation.java        (Linear & Binary search)
│       ├── SortOperation.java          (Sorting algorithms)
│       └── TrashBinOperation.java      (Stack operations)
```

### **Data Flow:**
```
User Action → GUI (View Layer)
              ↓
    Controller Layer (Validates & Routes)
              ↓
    Business Logic (CRUD/Search/Sort)
              ↓
    Data Structures (LinkedList/Queue/Stack)
              ↓
    Model (PrisonerModel object)
              ↓
    Back to GUI (Display Results)
```

### **Key Classes and Their Responsibilities:**

1. **PrisonerModel.java**
   - Represents a single prisoner
   - Contains all prisoner attributes
   - Getters and setters for data access

2. **PrisonController.java**
   - Manages data structures (LinkedList, Queue, Stack)
   - Coordinates operations between view and logic
   - Delegates to specialized classes (CRUD, Search, Sort)

3. **CRUD.java**
   - `addPrisoner()` - Adds new record with validation
   - `getPrisonerById()` - Retrieves specific prisoner
   - `updatePrisoner()` - Modifies existing record
   - `deletePrisoner()` - Removes record and pushes to Stack

4. **SearchOperation.java**
   - `linearSearch()` - Sequential search through list
   - `binarySearch()` - Efficient search on sorted list
   - Supports multiple search criteria

5. **SortOperation.java**
   - Multiple sorting methods for different attributes
   - Ascending and descending orders
   - Updates LinkedList in place

6. **TrashBinOperation.java**
   - `pushToTrash()` - Adds deleted prisoner to Stack
   - `popFromTrash()` - Recovers most recent deletion
   - Manages Stack operations

---

## 📊 **Data Structure Choices - Educational Perspective**

### **Why LinkedList over ArrayList?**

**Educational Demonstration:**
- Shows understanding of dynamic data structures
- Illustrates pointer/reference concepts
- Demonstrates insertion/deletion efficiency

**Practical Justification:**
- Frequent insertions/deletions in prison management
- Size unknown at compile time
- No need for random access by index

### **Why Queue for Recent Activities?**

**Educational Demonstration:**
- Perfect example of FIFO principle
- Shows bounded queue implementation
- Demonstrates queue operations (enqueue, dequeue)

**Practical Justification:**
- Chronological activity tracking
- Natural fit for "recent additions"
- Auto-maintains fixed size (5 most recent)

### **Why Stack for Trash Bin?**

**Educational Demonstration:**
- Perfect example of LIFO principle
- Shows stack operations (push, pop, peek)
- Real-world undo functionality

**Practical Justification:**
- Most recent deletion recovered first (intuitive)
- Simple implementation
- Matches user expectation for "undo"

---

## 📈 **Learning Outcomes Achieved**

### **1. Data Structure Selection**
✅ Understanding when to use LinkedList vs Array  
✅ Knowing when Queue is appropriate (FIFO scenarios)  
✅ Recognizing Stack use cases (LIFO scenarios)  
✅ Making informed choices based on operations needed

### **2. Algorithm Application**
✅ Implementing Linear Search from scratch  
✅ Implementing Binary Search with prerequisites  
✅ Understanding time complexity (O(n) vs O(log n))  
✅ Applying sorting algorithms to real data

### **3. Software Design**
✅ MVC architectural pattern  
✅ Separation of concerns  
✅ Single Responsibility Principle  
✅ Code organization and modularity

### **4. Practical Skills**
✅ Input validation and data integrity  
✅ Error handling and user feedback  
✅ GUI development with Java Swing  
✅ Debugging and console logging

### **5. Problem Solving**
✅ Breaking complex problems into manageable parts  
✅ Choosing appropriate data structures for specific tasks  
✅ Optimizing operations (search, sort) for better performance  
✅ Handling edge cases (empty list, duplicate IDs, etc.)

---

## 🎓 **Educational Value - What I Learned**

### **Before This Project:**
- Data structures were abstract concepts in textbooks
- Algorithms were theoretical exercises
- Didn't understand when to use which structure

### **After This Project:**
- **Concrete Understanding:** See how LinkedList works in a real application
- **Performance Impact:** Experience the difference between O(n) and O(log n)
- **Design Decisions:** Understand trade-offs in choosing data structures
- **Practical Application:** Can apply DSA knowledge to any domain
- **Code Organization:** Learned clean architecture principles
- **Validation Importance:** Understand data integrity challenges

### **Key Insights:**

1. **Data structures are tools** - Choose based on what operations you need
2. **Algorithm efficiency matters** - Binary search is noticeably faster
3. **Design patterns help** - MVC keeps code organized and maintainable
4. **Validation is critical** - Bad data breaks everything
5. **User experience matters** - Technical correctness isn't enough

---

## 🚀 **How to Use This System**

### **Prerequisites:**
- Java JDK 8 or higher
- NetBeans IDE (or any Java IDE)

### **Running the Application:**

1. **Open Project:**
   - Open NetBeans
   - File → Open Project
   - Select `Prison-Management-System` folder

2. **Build Project:**
   - Right-click project → Build

3. **Run Application:**
   - Right-click project → Run
   - Main window appears with empty prisoner table

4. **Add Sample Prisoners:**
   - Click "Add Prisoner" button
   - Fill in form (all fields with * are required)
   - Click OK
   - Repeat to add more prisoners

5. **Try Different Operations:**
   - **Search:** Click "Search" button, enter criteria
   - **Sort:** Click "Sort" button, choose attribute and order
   - **Edit:** Click "Edit" button on any table row
   - **Delete:** Click "Delete" button on any table row
   - **Restore:** Click "Restore from Trash" to undo deletion
   - **View Recent:** Click "Recent Activities" button

---

## 📝 **Code Examples - Educational Highlights**

### **Example 1: LinkedList Usage**
```java
// Initialize LinkedList
private LinkedList<PrisonerModel> prisonDetails = new LinkedList<>();

// Add prisoner (O(1) at end)
prisonDetails.add(newPrisoner);

// Iterate through LinkedList (O(n))
for (PrisonerModel prisoner : prisonDetails) {
    // Process each prisoner
}

// Remove by object (O(n) to find, O(1) to remove)
prisonDetails.remove(prisoner);
```

### **Example 2: Queue for Recent Activities**
```java
// Initialize Queue with LinkedList
private Queue<PrisonerModel> recentlyAddedQueue = new LinkedList<>();

// Enqueue (add to rear)
recentlyAddedQueue.offer(prisoner);

// Maintain size limit
if (recentlyAddedQueue.size() > MAX_RECENT) {
    recentlyAddedQueue.poll(); // Dequeue (remove from front)
}
```

### **Example 3: Stack for Trash Bin**
```java
// Initialize Stack
private Stack<PrisonerModel> trashBin = new Stack<>();

// Push (add to top)
trashBin.push(deletedPrisoner);

// Pop (remove from top)
if (!trashBin.isEmpty()) {
    PrisonerModel restored = trashBin.pop();
}

// Peek (view top without removing)
PrisonerModel topItem = trashBin.peek();
```

### **Example 4: Linear Search**
```java
public static List<PrisonerModel> linearSearch(
    LinkedList<PrisonerModel> list, String criteria, String value) {
    
    List<PrisonerModel> results = new ArrayList<>();
    
    for (PrisonerModel prisoner : list) {  // O(n)
        if (matchesCriteria(prisoner, criteria, value)) {
            results.add(prisoner);
        }
    }
    
    return results;
}
```

### **Example 5: Binary Search**
```java
public static PrisonerModel binarySearch(
    LinkedList<PrisonerModel> list, int targetId) {
    
    int left = 0;
    int right = list.size() - 1;
    
    while (left <= right) {  // O(log n)
        int mid = left + (right - left) / 2;
        PrisonerModel midPrisoner = list.get(mid);
        
        if (midPrisoner.getPrisonerId() == targetId) {
            return midPrisoner;  // Found!
        } else if (midPrisoner.getPrisonerId() < targetId) {
            left = mid + 1;  // Search right half
        } else {
            right = mid - 1;  // Search left half
        }
    }
    
    return null;  // Not found
}
```

---

## 🎯 **Project Objectives Summary**

### **Primary Objective (90% of focus):**
> **Master Data Structures and Algorithms through practical implementation**

### **Secondary Objectives (10% of focus):**
- Apply OOP principles (encapsulation, abstraction)
- Learn GUI development with Java Swing
- Practice software architecture (MVC pattern)
- Understand input validation and data integrity
- Develop problem-solving and debugging skills

### **Why This Approach?**
> "I learn best by doing. Reading about LinkedLists is one thing, but actually using them to solve a real problem - adding prisoners, searching for them, deleting them, sorting them - that's when it truly clicks. The Prison Management System isn't the goal; it's the playground where I practice and master data structures."

---

## 💡 **Unique Aspects of This Project**

1. **Real Console Logging**
   - Every operation prints detailed logs
   - Shows internal workings (helps understand what's happening)
   - Excellent for learning and debugging

2. **Comprehensive Validation**
   - 10+ validation rules
   - Shows importance of data integrity
   - Real-world software quality standards

3. **Multiple Search Approaches**
   - Compare Linear vs Binary search
   - See performance difference firsthand
   - Understand when to use each

4. **Undo Functionality**
   - Real-world feature using Stack
   - Perfect demonstration of LIFO principle
   - User-friendly and practical

5. **Activity Tracking**
   - Queue used for actual feature
   - Not just a theoretical exercise
   - Shows FIFO in action

---

## 📚 **Related Documentation**

- **PROJECT_INTRODUCTION.md** - Formal academic introduction
- **REFERENCES.md** - Academic citations for data structures
- **NEPAL_PRISON_REFERENCES.md** - Context-specific references
- **README.md** - Quick overview and setup guide

---

## 🎓 **For Evaluators/Instructors**

This project demonstrates:

✅ **Deep understanding** of LinkedList, Queue, and Stack  
✅ **Practical application** of Linear and Binary search algorithms  
✅ **Implementation skills** for sorting algorithms  
✅ **Software design** knowledge (MVC, separation of concerns)  
✅ **Code quality** (documentation, validation, error handling)  
✅ **Problem-solving** ability (choosing right structures for right tasks)  
✅ **Academic rigor** (proper citations, justified choices)

**The student can explain:**
- Why LinkedList was chosen over ArrayList
- When to use Linear vs Binary search
- How Queue implements FIFO behavior
- How Stack implements LIFO behavior
- Time complexity of each operation
- Trade-offs in design decisions

---

## 🙏 **Acknowledgments**

This project was developed as coursework for:
- **Course:** CS5005 Data Structures and Specialist Programming
- **Institution:** Islington College
- **Student:** Anjal Bhattarai (ID: 264932)
- **Academic Year:** 2023-2024

Special thanks to instructors and classmates for guidance and inspiration.

---

## ⚖️ **Disclaimer**

This is an **educational project** created for learning purposes. While it simulates a Prison Management System, it is:
- Not intended for production use
- Not connected to any real correctional facility
- Simplified for educational clarity
- Focused on demonstrating data structure concepts

The primary value is **educational**, not functional deployment.

---

**Remember: The Prison Management System is the example. Data Structures and Algorithms are the lesson. 🎓**
