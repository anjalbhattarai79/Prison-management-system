# Prison Management System - Project Introduction

## 1. Introduction

### 1.1 Background and Context

In modern correctional facilities, efficient management of prisoner information is crucial for maintaining security, organization, and accountability. Traditional paper-based systems or outdated digital solutions often lead to inefficiencies, data inconsistencies, and operational challenges. The Prison Management System (PMS) addresses these challenges by providing a comprehensive, data structure-driven solution for managing prisoner records and operations.

This project is developed as part of the CS5005 Data Structures and Specialist Programming coursework at Islington College, demonstrating the practical application of fundamental data structures and algorithms in solving real-world problems. The system showcases how different data structures—including LinkedLists, Queues, and Stacks—can be strategically employed to optimize various aspects of prison management operations.

### 1.2 Project Overview

The Prison Management System is a Java-based desktop application built using the Swing GUI framework. It implements a complete Create, Read, Update, and Delete (CRUD) functionality for managing prisoner records, along with advanced features such as search operations, sorting algorithms, and a trash bin recovery system. The application follows the Model-View-Controller (MVC) architectural pattern to ensure clean separation of concerns, maintainability, and scalability.

### 1.3 Problem Statement

Current prison management systems face numerous operational and technical challenges that hinder efficiency and accuracy in correctional facilities:

#### 1.3.1 Problems with Existing Systems

**1. Inefficient Data Retrieval and Search Operations**
- Manual searching through large prisoner databases is time-consuming and error-prone
- Lack of optimized search algorithms leads to slow response times when locating specific prisoners
- Staff waste valuable time scrolling through extensive records to find information
- No support for multi-criteria search (by ID, name, crime type, location, or status)
- **Solution**: Our system implements both Linear Search and Binary Search algorithms, providing fast and flexible search capabilities across multiple prisoner attributes

**2. Poor Data Organization and Sorting Capabilities**
- Records are often stored in insertion order without logical organization
- Difficulty in generating sorted reports based on different criteria (age, admission date, sentence duration)
- Administrators cannot quickly identify prisoners by priority or specific characteristics
- **Solution**: The system provides multiple sorting algorithms allowing instant organization by ID, name, age, admission date, or sentence duration

**3. Absence of Activity Tracking and Audit Trails**
- No visibility into recent system changes or additions
- Difficult to track who was added, when, and by whom
- Loss of operational context for recent activities
- **Solution**: Queue data structure maintains a rolling log of the 5 most recent additions, providing administrators with immediate visibility into latest system activities

**4. Irreversible Data Deletion and Loss**
- Accidental deletions result in permanent data loss
- No undo mechanism for recovering mistakenly deleted prisoner records
- Staff errors can lead to critical information being permanently removed
- Requires manual data re-entry if deletion was unintentional
- **Solution**: Stack-based trash bin system allows recovery of deleted prisoners with a simple restore operation (Last-In-First-Out principle)

**5. Lack of Data Validation and Integrity Issues**
- Manual data entry leads to inconsistent formats and invalid information
- No standardized validation rules for names, ages, dates, or other fields
- Duplicate entries and data corruption
- Invalid characters in critical fields (names with numbers, negative ages, etc.)
- **Solution**: Comprehensive validation layer with regex patterns, range checks, and business rules ensuring only valid, consistent data enters the system

**6. Inefficient Family Communication Management**
- No streamlined system for family members to access prisoner information
- Manual processing of family visit requests and information queries
- Security concerns with unauthorized access to prisoner data
- **Solution**: Auto-generated family access codes provide secure, unique identifiers for family portal access, linking visitors to specific prisoners

**7. Manual ID Generation and Record Conflicts**
- Manual ID assignment leads to duplicate IDs and conflicts
- No systematic approach to ensuring unique prisoner identifiers
- Time wasted resolving ID conflicts and correcting records
- **Solution**: Automated ID generation system ensures sequential, unique prisoner IDs without conflicts

**8. Poor User Interface and Usability**
- Complex command-line interfaces or outdated legacy systems
- Steep learning curve for new staff members
- Increased training time and operational errors
- **Solution**: Intuitive graphical user interface (GUI) with clear buttons, forms, and table displays makes operations accessible to all staff levels

**9. Scalability and Performance Issues**
- Inefficient data structures cause system slowdowns as records increase
- Linear time complexity for all operations
- System becomes unusable with large prisoner populations
- **Solution**: Strategic use of LinkedList for flexible insertion/deletion, Queue for bounded recent activities, and optimized search algorithms ensure consistent performance

**10. Limited Reporting and Analytics**
- Difficulty generating reports on prisoner statistics
- No quick overview of prison population by status, location, or crime type
- Manual compilation of data for administrative reports
- **Solution**: Integrated sorting and filtering capabilities enable instant report generation based on any prisoner attribute

#### 1.3.2 Impact of These Problems

These challenges result in:
- **Operational Inefficiency**: Staff spend excessive time on administrative tasks
- **Security Risks**: Difficulty tracking prisoner locations and status in real-time
- **Data Loss**: Accidental deletions and lack of backup mechanisms
- **Poor Decision Making**: Limited data organization prevents informed administrative decisions
- **Increased Costs**: More staff time required for manual processes
- **Compliance Issues**: Difficulty maintaining accurate records for legal and regulatory requirements

#### 1.3.3 System Requirements

To address these problems, the Prison Management System must:
- Provide instant search and retrieval capabilities
- Support multiple sorting criteria for data organization
- Maintain activity logs for accountability and tracking
- Implement recovery mechanisms for deleted records
- Enforce strict data validation rules
- Offer an intuitive, user-friendly interface
- Scale efficiently with growing prisoner populations
- Generate automated reports and analytics

### 1.4 Project Objectives

The primary objectives of this project are:

1. **Implement Core Data Structures**: Demonstrate practical understanding of LinkedLists, Queues, and Stacks through their strategic application in prisoner management operations

2. **Develop CRUD Operations**: Create a robust system for adding, viewing, updating, and deleting prisoner records with comprehensive validation

3. **Implement Search Algorithms**: Integrate both Linear Search and Binary Search algorithms to enable efficient data retrieval

4. **Apply Sorting Techniques**: Implement multiple sorting algorithms to organize prisoner data based on different attributes

5. **Design User-Friendly Interface**: Create an intuitive graphical user interface that simplifies complex operations for prison administrators

6. **Ensure Data Validation**: Implement comprehensive input validation to maintain data integrity and prevent errors

7. **Provide Recovery Mechanisms**: Implement a trash bin system using Stack data structure to allow recovery of accidentally deleted records

### 1.5 Scope of the System

The Prison Management System encompasses the following features:

**Core Functionality:**
- Add new prisoner records with auto-generated IDs and family access codes
- View all prisoner records in a structured table format
- Update existing prisoner information
- Delete prisoner records with trash bin backup
- Restore recently deleted prisoners from trash bin

**Data Management:**
- Comprehensive prisoner information including personal details, crime information, admission dates, sentence duration, and status tracking
- Automatic calculation of release dates based on admission date and sentence duration
- Family portal access code generation for visitor management

**Search and Sort Operations:**
- Multiple search criteria (ID, Name, Crime Type, Status, Location)
- Linear Search and Binary Search implementations
- Sorting by various attributes (ID, Name, Age, Admission Date, Sentence Duration)
- Real-time filtering and data organization

**Advanced Features:**
- Recent activities tracking using Queue data structure
- Undo functionality using Stack data structure for deleted records
- Detailed console logging for debugging and operation tracking
- Input validation with user-friendly error messages

### 1.6 System Architecture

The application follows the MVC architectural pattern:

**Model Layer** (`model` package):
- `PrisonerModel.java`: Represents the prisoner entity with all attributes and business logic

**View Layer** (`view` package):
- `MainFrame.java`: Primary GUI interface with table display and control buttons
- `PrisonerDialogHelper.java`: Helper class for add/edit dialog forms
- `TableButtonRenderer.java` & `TableButtonEditor.java`: Custom components for action buttons in table

**Controller Layer** (`controller` package):
- `PrisonController.java`: Main controller coordinating all operations
- `CRUD.java`: Handles all Create, Read, Update, Delete operations
- `SearchOperation.java`: Implements search algorithms (Linear, Binary)
- `SortOperation.java`: Implements sorting algorithms
- `TrashBinOperation.java`: Manages trash bin Stack operations

### 1.7 Data Structures Implementation

The system strategically employs different data structures for optimal performance:

1. **LinkedList<PrisonerModel>**: 
   - Main storage for all prisoner records
   - Provides efficient insertion and deletion operations
   - Allows sequential traversal for linear search operations

2. **Queue<PrisonerModel>** (using LinkedList):
   - Tracks recently added prisoners (FIFO - First In, First Out)
   - Maintains a rolling log of the last 5 additions
   - Provides administrators with quick access to recent changes

3. **Stack<PrisonerModel>**:
   - Implements trash bin functionality (LIFO - Last In, First Out)
   - Enables undo operations for deleted records
   - Allows recovery of the most recently deleted prisoner

### 1.8 Technology Stack

- **Programming Language**: Java (JDK 8 or higher)
- **GUI Framework**: Java Swing
- **Build Tool**: Apache Ant (NetBeans project structure)
- **IDE**: NetBeans IDE
- **Design Pattern**: Model-View-Controller (MVC)
- **Date Handling**: Java 8 Time API (LocalDate)

### 1.9 Key Features Demonstration

This project demonstrates proficiency in:

1. **Object-Oriented Programming**: Encapsulation, abstraction, and clean code principles
2. **Data Structure Selection**: Strategic choice of appropriate data structures for specific operations
3. **Algorithm Implementation**: Linear search, binary search, and various sorting algorithms
4. **GUI Development**: Creating professional, user-friendly interfaces with Swing
5. **Input Validation**: Comprehensive validation with regex patterns and business rules
6. **Exception Handling**: Robust error handling and user feedback mechanisms
7. **Code Organization**: Clean architecture with separation of concerns
8. **Documentation**: Comprehensive inline documentation and logging

### 1.10 Report Structure

This report is organized as follows:

- **Section 2 - System Design**: Detailed explanation of architectural decisions, class diagrams, and data flow
- **Section 3 - Data Structures Analysis**: In-depth discussion of LinkedList, Queue, and Stack implementations
- **Section 4 - Algorithm Implementation**: Explanation of search and sort algorithms with complexity analysis
- **Section 5 - Implementation Details**: Code walkthrough of key components and features
- **Section 6 - Testing and Validation**: Test cases, validation scenarios, and results
- **Section 7 - Challenges and Solutions**: Issues encountered and resolution strategies
- **Section 8 - Conclusion and Future Enhancements**: Project summary and potential improvements

---

**Student**: Anjal Bhattarai  
**Student ID**: 264932  
**Course**: CS5005 Data Structures and Specialist Programming  
**Institution**: Islington College  
**Academic Year**: 2023-2024
