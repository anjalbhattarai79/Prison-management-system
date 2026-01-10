# 🔐 Quick Test Reference Card

## Family Portal Login Test

### Test Case 1: Successful Login
```
Prisoner ID: 101
Family Code: FAM101
Expected: Login successful, see Ram Bahadur Thapa's details
```

### Test Case 2: Invalid Family Code
```
Prisoner ID: 101
Family Code: WRONG123
Expected: "Invalid Prisoner ID or Family Code" error
```

### Test Case 3: Invalid Prisoner ID
```
Prisoner ID: 999
Family Code: FAM101
Expected: "Invalid Prisoner ID or Family Code" error
```

### Test Case 4: Empty Fields
```
Prisoner ID: (empty)
Family Code: (empty)
Expected: "Please enter both Prisoner ID and Family Code" warning
```

## Visit Request Test

### Test Case 1: Complete Valid Request
```
After logging in as Prisoner 101:
- Your Name: "Maya Thapa"
- Relationship: "Spouse"
- Preferred Date: "2026-02-15"
- Purpose: "Family visit and emotional support"
Expected: "Request submitted successfully" with Pending status
```

### Test Case 2: Invalid Date Format
```
- Your Name: "John Doe"
- Relationship: "Friend"
- Preferred Date: "15-02-2026" (wrong format)
- Purpose: "Visit"
Expected: "Invalid date format" error
```

### Test Case 3: Past Date
```
- Your Name: "Sarah Smith"
- Relationship: "Lawyer"
- Preferred Date: "2020-01-01"
- Purpose: "Legal consultation"
Expected: "Date must be today or in the future" error
```

### Test Case 4: Empty Fields
```
- Your Name: (empty)
- Relationship: "Parent"
- Preferred Date: "2026-03-01"
- Purpose: (empty)
Expected: "Please fill in all fields" warning
```

## Admin Visit Management Test

### Test Case 1: Approve Request
```
1. Admin logs in
2. Click "Visit Requests" button
3. Find a Pending request
4. Click "Approve"
5. Enter notes: "Approved for Saturday 10 AM"
6. Click OK
Expected: Status changes to "Approved" (green), family sees update
```

### Test Case 2: Decline Request
```
1. Admin logs in
2. Click "Visit Requests" button
3. Find a Pending request
4. Click "Decline"
5. Enter reason: "Security concerns"
6. Click OK
Expected: Status changes to "Declined" (red), family sees update
```

### Test Case 3: Decline Without Reason
```
1. Click "Decline" on a request
2. Leave reason field empty
3. Click OK
Expected: "Please provide a reason" warning, no status change
```

## 🎯 Quick Test Workflow (5 minutes)

### Step 1: Family Portal (2 min)
1. Run application
2. Click "Family Portal"
3. Login: ID=`101`, Code=`FAM101`
4. Verify: See "Ram Bahadur Thapa" details
5. Submit visit request:
   - Name: `Test Visitor`
   - Relationship: `Spouse`
   - Date: `2026-02-15`
   - Purpose: `Test visit`
6. Verify: Request appears in history table as "Pending"

### Step 2: Admin Review (2 min)
1. Logout from family portal
2. Click "Admin Login"
3. Login: Username=`admin`, Password=`admin123`
4. Click "Visit Requests" button
5. Verify: See the test request
6. Click "Approve"
7. Enter note: `Test approval`
8. Verify: Status changes to "Approved" (green)

### Step 3: Family Verification (1 min)
1. Logout from admin
2. Login to family portal again (ID=`101`, Code=`FAM101`)
3. Verify: Request status now shows "Approved"
4. ✅ Test Complete!

## 📋 All Test Credentials

| ID  | Code   | Name                     |
|-----|--------|--------------------------|
| 101 | FAM101 | Ram Bahadur Thapa       |
| 102 | FAM102 | Sita Maya Gurung        |
| 103 | FAM103 | Bikash Sharma Poudel    |
| 104 | FAM104 | Anita Kumari Rai        |
| 105 | FAM105 | Prakash Tamang          |
| 106 | FAM106 | Sunita Devi Chaudhary   |
| 107 | FAM107 | Nirajan Karki Chhetri   |
| 108 | FAM108 | Gita Kumari Adhikari    |
| 109 | FAM109 | Dinesh Bahadur Magar    |
| 110 | FAM110 | Krishna Kumari Shrestha |

### Admin Credentials
```
Username: admin
Password: admin123
```

## ⚠️ Common Issues & Solutions

### Issue: "Invalid credentials"
✅ Solution: Check Prisoner ID is numeric and Family Code matches exactly

### Issue: "Invalid date format"
✅ Solution: Use YYYY-MM-DD format (e.g., 2026-02-15)

### Issue: Can't see submit button
✅ Solution: Scroll down in the dashboard panel

### Issue: Visit request not appearing
✅ Solution: Click "Refresh" in admin dialog

### Issue: Approve/Decline buttons disabled
✅ Solution: These only work for "Pending" requests

## 🌟 Features to Showcase

1. **Smooth Login Flow**: Placeholder text, enter key navigation
2. **Real-time Validation**: Instant feedback on errors
3. **Professional UI**: Clean NetBeans-style design
4. **Status Sync**: Changes in admin reflect in family portal
5. **Color Coding**: Green (Approved), Red (Declined), Orange (Pending)
6. **Confirmation Dialogs**: Safe operations with user confirmation
7. **Error Handling**: Clear, helpful error messages
8. **Date Validation**: Smart checking for future dates

---

**Print this card for quick reference during testing! 📋**
