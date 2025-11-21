📘 Trackify – Smart Faculty Location & Navigation System

Trackify is an Android-based smart campus navigation solution designed to help students quickly locate faculty members across university departments. It removes the hassle of searching from one floor to another and provides accurate room, floor, and building information in one tap.

Trackify includes:

Android Mobile App (Students & Admin)

Room & Faculty Database (Local / Room DB)

Interactive Search + Department Navigation

This system replaces manual inquiries and outdated paper directories with a clean digital interface.

🚀 Project Overview

Trackify allows users to browse departments, select courses, and view faculty members along with their office locations.
Admins can add or edit faculty details directly from the app using an intuitive interface.

The app provides:

Fast faculty search

Clear building → floor → room location details

Easy department & course-based navigation

Admin-friendly tools to add/update faculty information

📱 System Components
1. Android Mobile App (Students + Admin)
Core Features

✔ Login / Signup (optional if implemented)
✔ Search faculty by name
✔ Department list with category cards
✔ Course-wise faculty listing
✔ View faculty office details
✔ View building, room number & floor
✔ Add new faculty (Admin access)
✔ Edit existing faculty details
✔ Floating Action Button for quick add
✔ Pop-up modal dialogs for Add / Edit / View
✔ Clean and responsive UI with Material Components

Admin Tools

Add faculty

Edit faculty

Manage location details

Update building, floor, room numbers

2. Local Faculty Management System (SQLite / Room DB)

Handles:

Faculty data storage

Departments & courses

CRUD operations (Create, Read, Update, Delete)

Search queries

Offline functionality

3. UI/UX Interaction Flow

Home Screen → Departments → Courses → Faculty List → Faculty Details
↳ From Faculty Details: View Location / Edit / Close

Admin-only FAB (+) → Add Faculty Dialog


🧱 Architecture Diagram
Students/Admin → Trackify Android App → Local Database 
↑                                             ↓
│——————— Search, View, Add, Edit ————————│

🛠️ Tech Stack
Mobile App (Android – Kotlin/Java)

RecyclerView + CardView

Material Components

Glide

FloatingActionButton

DialogFragment

SearchView

🔗 App Functionalities & Flows
Faculty Operations

Add new faculty

Edit faculty details

View office location

Search by name

Department Navigation

Department cards

Course selection

Faculty list under each course

Location Details Shown

Room No.

Floor No.

Building Name

📸 Trackify App — Screen Descriptions
1️⃣ Login Screen

Username and password fields

Login / Signup buttons

Clean onboarding layout

![login](https://github.com/user-attachments/assets/8b3f51a8-80d9-458d-9064-04e6266540c1)

2️⃣ Departments Screen

Displays all departments in colorful cards

Each card has an icon + department name

Search bar at top

Filter options: All, My Departments, Favorites

![2](https://github.com/user-attachments/assets/46807a0b-1104-496b-817f-de2fcf164930)

3️⃣ Course Selection Screen

Shows course list (B.Tech, M.Tech, BCA, MCA etc.)

Cards show course title + subtext “Tap to view faculty”

![3](https://github.com/user-attachments/assets/708e9473-6d1d-40b6-8863-b031974c6fc0)

4️⃣ Faculty List Screen

Displays faculty members under selected course

Search bar for fast filtering

Faculty card shows name + “Tap to view office details”

![4](https://github.com/user-attachments/assets/0f644ff1-6436-4635-a64a-a2ac9279618e)

5️⃣ Faculty Details Dialog

Shows:

Name

Room Number

Floor

Building

View Location

Edit

Close

![6](https://github.com/user-attachments/assets/415ec8de-f47b-4822-8159-0e726017dba0)

6️⃣ Edit Faculty Modal

Editable fields for name, room, floor, building

Save / Cancel buttons

![7](https://github.com/user-attachments/assets/fd4f86c1-08a5-4d96-867c-5ef4777c6531)

7️⃣ Add Faculty Modal

Fields: Name, Room No., Floor No., Building

Add / Cancel buttons

Launched using FAB (+)

![8](https://github.com/user-attachments/assets/5ca71e75-b402-4477-811b-2756229b71f2)

🔮 Future Enhancements

Indoor campus navigation map

QR-based room directory

Cloud sync (Firebase)

Role-based admin login

Dark mode

Firestore for real-time updates

📂 Trackify — Project Structure

LoginApp4/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/loginapp4/
│   │   │   │       ├── AddFaculty.kt
│   │   │   │       ├── AddFacultyView.kt
│   │   │   │       ├── DepartmentDetailsActivity.kt
│   │   │   │       ├── DepartmentListActivity.kt
│   │   │   │       ├── EditFacultyActivity.kt
│   │   │   │       ├── FacultyDetailsActivity.kt
│   │   │   │       ├── FacultyListActivity.kt
│   │   │   │       ├── MainActivity.kt
│   │   │   │       ├── SearchActivity.kt
│   │   │   │       ├── models/
│   │   │   │       │   ├── Department.kt
│   │   │   │       │   ├── Faculty.kt
│   │   │   │       │   └── Course.kt
│   │   │   │       ├── adapters/
│   │   │   │       │   ├── DepartmentAdapter.kt
│   │   │   │       │   ├── CourseAdapter.kt
│   │   │   │       │   └── FacultyAdapter.kt
│   │   │   │       ├── database/
│   │   │   │       │   ├── DBHelper.kt
│   │   │   │       │   └── DatabaseManager.kt
│   │   │   │       └── utils/
│   │   │   │           ├── Extensions.kt
│   │   │   │           └── constants/
│   │   │   │               └── AppConstants.kt
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_department_list.xml
│   │   │   │   │   ├── activity_faculty_list.xml
│   │   │   │   │   ├── activity_department_details.xml
│   │   │   │   │   ├── activity_faculty_details.xml
│   │   │   │   │   ├── activity_edit_faculty.xml
│   │   │   │   │   ├── activity_search.xml
│   │   │   │   │   ├── dialog_add_faculty.xml
│   │   │   │   │   └── card layouts (department, faculty, course)
│   │   │   │   │
│   │   │   │   ├── drawable/
│   │   │   │   ├── mipmap/
│   │   │   │   └── values/
│   │   │   │       ├── colors.xml
│   │   │   │       ├── strings.xml
│   │   │   │       └── themes.xml
│   │   │   │
│   │   │   ├── AndroidManifest.xml
│   │   │
│   │   ├── test/
│   │   ├── androidTest/
│   │
│   ├── build.gradle.kts
│   └── settings.gradle.kts
│
├── gradle/
├── gradlew
├── gradlew.bat
└── build.gradle.kts
