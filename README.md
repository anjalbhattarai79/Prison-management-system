# Prison-management-system 🏛️🚔

## Project Overview 🔍
A simple Prison Management and Information System (PMIS) to record inmate check-ins, activities, and alerts. The README documents core entities, recommended data structures, controllers (business logic), UI views, and example workflows for the coursework.

## Domain Entities 🧩
- Inmate 👤
  - Fields: ID, name, cell/block, admissionDate, sentence, riskLevel
- Check-in Record ✅
  - Fields: inmateID, timestamp, location (block/cell), type (count, activity, lock-in)
- Activity Log 📝
  - Fields per entry: inmateID, activityType (work, education, recreation), timestamp, duration
- Alert / Incident ⚠️
  - Fields: id, inmateID (optional), timestamp, type, severity, description, processedFlag

## Recommended Data Structures 🧠
- HashMap (Map InmateID → Inmate) 🗺️
  - Fast lookup by inmate ID for check-ins and reporting.
- ArrayList (List of Check-in Records) 📋
  - Daily ordered records; append-only usage for the day.
- LinkedList (per-inmate Activity Log) 🔗
  - Efficient appends and sequential traversal of activity history.
- Queue (pending alerts) 🧾
  - FIFO processing of new alerts / incidents by staff.
- Stack (undo functionality) ⤴️
  - Push administrative actions to allow revert of the most recent change.

## Controllers (Business Logic) ⚙️
- CheckInController 🛂
  - Validate ID (scan/manual) or staff verification, create Check-in Record, update attendance status, raise alerts on mismatches or missing ID.
- ActivityController 🎯
  - Add / update per-inmate activity log entries, compute totals/durations.
- AlertController 🚨
  - Detect missing check-ins, unauthorized movement, and generate Alert records into the alerts queue.
- AdminController 🧑‍💼
  - Query reports (daily attendance, alerts list, inmate history), process alerts, and perform admin-level updates.
- IntegrationController 🔌
  - Provide a minimal API surface to sync with external systems (PMIS/PVIS/CCIS) or export reports.

## Views / UI (Suggested) 🖥️
- Prison Staff Dashboard 👮‍♂️
  - Quick check-in interface (ID scan or manual entry), live attendance view, immediate alerts stream.
- Admin Dashboard 🧑‍⚖️
  - Reports, inmate history lookup, alert processing panel, undo last administrative action.
- Integration UI / API Endpoints 🔁
  - Endpoints for exporting/importing inmate and incident data.

## Data Flow (high level) 🔄
1. ID verification (scan/manual) 🆔 → CheckInController validates → HashMap lookup of Inmate → append Check-in Record to ArrayList.
2. ActivityController appends activity entries to inmate’s LinkedList 🔗.
3. AlertController monitors the stream of Check-in Records and scheduled checks; enqueues Alert into Queue when anomalies appear 🚨.
4. AdminController processes Alerts (dequeues), updates records, and may push undoable actions to the Stack ⤴️.

## Example Use-cases 💡
- Daily roll call: iterate Check-in Records for the day, mark missing inmates and generate alerts ✅.
- Activity report: traverse an inmate’s LinkedList to compute total activity time per week 📊.
- Undo admin mistake: pop last action from Stack to revert a wrong check-in or alert dismissal ↩️.
- Alert processing: staff dequeues next pending alert, investigates, marks processed 🧾.

## Implementation Notes 🛠️
- Keep entity classes small and focused (POJOs/DTOs).
- Isolate data structure choices behind repository/manager classes so you can swap implementations for testing.
- Persist daily records to a simple file or database for coursework evaluation; in-memory data structures can back the runtime logic.

## Next steps ✅
- Define class diagrams or simple interfaces for repositories and controllers.
- Create unit tests for CheckInController, AlertController, and basic data structure operations (enqueue/dequeue, push/pop, map lookups).
- Implement a small CLI or web UI prototype to demonstrate workflows.
