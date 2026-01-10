# Family Portal Feature - Implementation Guide

## Overview
The family portal has been fully implemented with authentication, dashboard, and visit request functionality. Family members can now:
- Log in using Prisoner ID and Family Code
- View prisoner details (name, status, location, health, admission date, release date)
- Submit visit requests
- View their visit request history with approval status

## What's Been Implemented

### 1. **VisitRequest Model** (`model/VisitRequest.java`)
- Manages visit request data
- Tracks request status: Pending, Approved, Declined
- Auto-increments request IDs starting from 1001
- Stores visitor information, relationship, preferred date, and purpose

### 2. **Controller Updates** (`controller/PrisonController.java`)
- `validateFamilyLogin(prisonerId, familyCode)` - Authenticates family members
- `addVisitRequest()` - Creates new visit requests
- `getAllVisitRequests()` - Retrieves all requests for admin
- `getVisitRequestsForPrisoner()` - Gets requests for specific prisoner
- `updateVisitRequestStatus()` - Approves/declines requests
- `getPendingVisitRequestsCount()` - Counts pending requests

### 3. **Family Portal Authentication** (`view/MainFrame.java`)
- Family login panel with prisoner ID and family code validation
- Placeholder text for input fields
- Error handling for invalid credentials
- Success message on login
- Session management with `currentFamilyPrisonerId`

### 4. **Family Dashboard** (`view/MainFrame.java`)
- **Prisoner Information Panel**: Displays name, status, admission date, location, health, expected release
- **Visit Request Form**: 
  - Visitor name field
  - Relationship dropdown (Parent, Spouse, Sibling, Child, Friend, Lawyer, Other)
  - Preferred date field (YYYY-MM-DD format)
  - Purpose text field
  - Submit button with full validation
- **Visit History Table**: Shows all submitted requests with status
- **Logout Button**: Clears session and returns to home

### 5. **Admin Visit Management** (`view/VisitRequestsDialog.java`)
- Professional dialog matching NetBeans design style
- Table showing all visit requests with full details
- **Approve button**: Opens dialog for notes/instructions
- **Decline button**: Requires reason for declining
- Real-time status updates
- Color-coded status (Green=Approved, Red=Declined, Orange=Pending)
- Status counter showing total/pending/processed requests
- Refresh functionality

## How to Use

### For Family Members:

1. **Login**:
   - Click "Family Portal" button on home screen
   - Enter Prisoner ID (e.g., 101, 102, 103...)
   - Enter Family Code (e.g., FAM101, FAM102, FAM103...)
   - Click "Access Portal"

2. **View Prisoner Information**:
   - After login, see prisoner details in the dashboard
   - Information includes: name, status, admission date, location, health status, expected release date

3. **Submit Visit Request**:
   - Fill in your name
   - Select your relationship from dropdown
   - Enter preferred date in YYYY-MM-DD format (e.g., 2026-02-15)
   - Enter purpose of visit
   - Click "Submit Request"
   - Request will show as "Pending" in history table

4. **Check Request Status**:
   - View all your submitted requests in the "Your Visit History" table
   - Status will update to "Approved" or "Declined" after admin review

5. **Logout**:
   - Click "Log Out" button
   - Confirms before logging out

### For Administrators:

1. **View Visit Requests**:
   - Login to admin dashboard
   - Click "Visit Requests" button
   - See all visit requests from all families

2. **Approve Requests**:
   - Click "Approve" button for a request
   - Optionally add notes/instructions
   - Request status changes to "Approved"

3. **Decline Requests**:
   - Click "Decline" button for a request
   - **Must** provide reason for declining
   - Request status changes to "Declined"

4. **Refresh**:
   - Click "Refresh" to see latest requests

## Sample Test Data

Here are the family codes for testing (matching the 10 sample prisoners):

| Prisoner ID | Prisoner Name | Family Code | Location |
|------------|---------------|-------------|----------|
| 101 | Ram Bahadur Thapa | FAM101 | Central Jail, Kathmandu |
| 102 | Sita Maya Gurung | FAM102 | Pokhara Jail, Kaski |
| 103 | Bikash Sharma Poudel | FAM103 | Biratnagar Jail, Morang |
| 104 | Anita Kumari Rai | FAM104 | Biratnagar Jail, Morang |
| 105 | Prakash Tamang | FAM105 | Central Jail, Kathmandu |
| 106 | Sunita Devi Chaudhary | FAM106 | Nepalgunj Jail, Banke |
| 107 | Nirajan Karki Chhetri | FAM107 | Bharatpur Jail, Chitwan |
| 108 | Gita Kumari Adhikari | FAM108 | Bhairahawa Jail, Rupandehi |
| 109 | Dinesh Bahadur Magar | FAM109 | Central Jail, Kathmandu |
| 110 | Krishna Kumari Shrestha | FAM110 | Central Jail, Kathmandu |

### Example Test Flow:

1. **Family Login**:
   - Prisoner ID: `101`
   - Family Code: `FAM101`
   - Should see: Ram Bahadur Thapa's information

2. **Submit Visit Request**:
   - Your Name: `Sita Thapa`
   - Relationship: `Spouse`
   - Preferred Date: `2026-02-15`
   - Purpose: `Family visit and support`

3. **Admin Review**:
   - Admin logs in
   - Opens "Visit Requests"
   - Sees request from Sita Thapa
   - Clicks "Approve" with note: "Approved for Saturday morning visit"

4. **Family Checks Status**:
   - Family logs back in
   - Sees request status changed to "Approved"

## UI/UX Features

### Design Consistency:
- Matches NetBeans drag-drop style components
- Professional color scheme (Blue/Gray theme)
- Clean, organized layouts
- Proper spacing and alignment

### User-Friendly Features:
- Placeholder text in input fields
- Clear error messages
- Success confirmations
- Color-coded status indicators
- Hover effects on buttons
- Input validation with helpful feedback
- Focus management (Enter key navigation)

### Security:
- Family code validation
- Session management
- Input sanitization
- Confirmation dialogs for important actions

## Technical Details

### Data Structures Used:
- `LinkedList<VisitRequest>` - Stores all visit requests
- `LinkedList<PrisonerModel>` - Stores prisoner data
- Request ID auto-increment system

### Validation:
- Prisoner ID must be numeric
- Family code must match prisoner's code
- All visit request fields are required
- Date must be in YYYY-MM-DD format
- Date must be today or future
- Decline reason is mandatory

### Status Flow:
```
Visit Request Created → Status: "Pending"
                           ↓
Admin Reviews → Approve → Status: "Approved" (with notes)
             → Decline → Status: "Declined" (with reason)
```

## Files Modified/Created

### New Files:
1. `src/model/VisitRequest.java` - Visit request model
2. `src/view/VisitRequestsDialog.java` - Admin visit management dialog
3. `FAMILY_PORTAL_GUIDE.md` - This guide

### Modified Files:
1. `src/controller/PrisonController.java` - Added visit request methods
2. `src/view/MainFrame.java` - Implemented family portal authentication and dashboard

## Notes

- The system maintains state for logged-in family members
- Visit requests persist during the application session
- All visit requests are visible to administrators
- Family members only see their prisoner's requests
- The UI follows NetBeans Form design patterns
- Color scheme matches the existing admin interface

## Future Enhancements (Optional)

- Visit scheduling calendar
- Email notifications
- Visit history with completed visits
- Multiple family members per prisoner
- Photo uploads for visitor verification
- Visit time slot selection
- SMS notifications for status changes
- Database persistence

## Support

For issues or questions:
1. Check that Prisoner ID and Family Code match exactly
2. Verify date format is YYYY-MM-DD
3. Ensure all required fields are filled
4. Contact administrator for family code if forgotten

---

**Implementation Date**: January 10, 2026
**Status**: Fully Functional
**Testing**: Manual testing recommended using sample data above
