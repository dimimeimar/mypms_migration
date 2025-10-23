# MyPMS - Package Management System (JavaFX Edition)

🚀 **Complete migration από Java Swing σε JavaFX με modern Material Design UI**

## 📋 Περιεχόμενα
- [Περιγραφή](#περιγραφή)
- [Τεχνολογίες](#τεχνολογίες)
- [Απαιτήσεις](#απαιτήσεις)
- [Εγκατάσταση](#εγκατάσταση)
- [Εκτέλεση](#εκτέλεση)
- [Δομή Project](#δομή-project)
- [Features](#features)
- [Screenshots](#screenshots)

---

## 🎯 Περιγραφή

Το **MyPMS** είναι ένα ολοκληρωμένο σύστημα διαχείρισης αποστολών και αντικαταβολών για logistics επιχειρήσεις. Η εφαρμογή μεταφέρθηκε από Java Swing σε **JavaFX** με:

- ✨ Modern Material Design UI
- 🎨 Custom CSS styling
- 📱 Responsive layouts
- ⚡ JavaFX Properties για data binding
- 🔄 Real-time updates

---

## 🛠️ Τεχνολογίες

### Frontend
- **JavaFX 21.0.1** - Modern UI framework
- **CSS3** - Material Design styling
- **FXML** (όπου χρειάζεται)

### Backend
- **Java 17** - Core programming language
- **MySQL 8.0+** - Database
- **JDBC** - Database connectivity

### External APIs
- **ACS Courier API** - Tracking integration
- **Firebase/Firestore** - Cloud sync
- **Jackson 2.15.2** - JSON processing

### Build & Dependencies
- **Maven 3.9+** - Build tool
- **Apache POI 5.2.3** - Excel export
- **ControlsFX 11.1.2** - Advanced components

---

## 📦 Απαιτήσεις

- **Java 17+** (JDK)
- **Maven 3.9+**
- **MySQL 8.0+**
- **4GB RAM** (minimum)

---

## 🚀 Εγκατάσταση

### 1. Clone το repository

```bash
git clone https://github.com/dimimeimar/mypms_migration.git
cd mypms_migration
```

### 2. Ρύθμιση MySQL Database

```sql
-- Δημιουργία database
CREATE DATABASE mypms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Import το schema (αν υπάρχει)
mysql -u root -p mypms < database/schema.sql
```

### 3. Ρύθμιση Database Configuration

Άνοιξε το αρχείο `src/main/java/com/mypms/config/DatabaseConfig.java` και άλλαξε τα credentials:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/mypms";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password"; // Άλλαξε εδώ
```

### 4. Build το project

```bash
mvn clean install
```

---

## ▶️ Εκτέλεση

### Maven (Development)

```bash
mvn javafx:run
```

### JAR Executable

```bash
mvn clean package
java -jar target/mypms-javafx-2.0.0.jar
```

### IDE (IntelliJ IDEA / Eclipse)

1. Import ως Maven project
2. Run `MainApplication.java`

---

## 📁 Δομή Project

```
mypms_migration/
├── pom.xml                                    # Maven configuration
├── README.md                                  # Documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── module-info.java              # Java modules
│   │   │   └── com/mypms/
│   │   │       ├── MainApplication.java       # 🏠 Entry point
│   │   │       ├── config/
│   │   │       │   └── DatabaseConfig.java    # Database configuration
│   │   │       ├── customers/                 # 👥 Customer Management
│   │   │       │   ├── model/
│   │   │       │   │   └── Pelatis.java       # Customer model (JavaFX Properties)
│   │   │       │   ├── dao/
│   │   │       │   │   └── PelatisDAO.java    # Database operations
│   │   │       │   └── ui/
│   │   │       │       └── CustomerManagementView.java  # UI
│   │   │       ├── shipments/                 # 📦 Shipment Management
│   │   │       │   ├── model/
│   │   │       │   │   └── Apostoli.java      # Shipment model
│   │   │       │   ├── dao/
│   │   │       │   │   └── ApostoliDAO.java
│   │   │       │   └── ui/
│   │   │       │       └── ShipmentManagementView.java
│   │   │       └── antikatavoles/             # 💰 COD Management
│   │   │           ├── model/
│   │   │           │   └── Antikatavoli.java
│   │   │           ├── dao/
│   │   │           │   └── AntikatavoliDAO.java
│   │   │           └── ui/
│   │   │               └── AntikatavoliManagementView.java
│   │   └── resources/
│   │       └── css/
│   │           └── main.css                   # 🎨 Modern Material Design CSS
│   └── test/
└── [original Swing files]                     # Kept for reference
```

---

## ✨ Features

### 🏠 Dashboard
- Modern card-based navigation
- 3 main modules με icons και animations
- Database connection status indicator
- Version information

### 👥 Διαχείριση Πελατών
- ✅ TableView με sortable columns
- ✅ Search και filtering (επωνυμία, κωδικός, κατηγορία)
- ✅ CRUD operations (Create, Read, Update, Delete)
- ✅ JavaFX Properties για real-time binding
- ✅ Double-click για λεπτομέρειες
- ✅ Row counter

**Πεδία Πελάτη:**
- Κωδικός, Κατηγορία (A-E)
- Επωνυμία Εταιρίας
- Νομική Μορφή, ΑΦΜ, ΔΟΥ
- Επικοινωνία (Email, Τηλέφωνα)
- Διεύθυνση (Πόλη, Οδός, ΤΚ)

### 📦 Διαχείριση Αποστολών
- ✅ TableView με custom cell renderers
- ✅ Multi-filter search (Αριθμός, Courier, Status)
- ✅ Date formatting (dd/MM/yyyy)
- ✅ Currency formatting (€)
- ✅ Color-coded status badges
- ✅ Integration με ACS Courier API
- ✅ CRUD operations

**Πεδία Αποστολής:**
- Αριθμός Αποστολής, Courier
- Παραλήπτης, Διεύθυνση
- Ημερομηνίες (Παραλαβή, Παράδοση)
- Αντικαταβολή (BigDecimal)
- Status (MyPMS, Courier)
- Tracking details

**Couriers:**
- ACS
- ΕΛΤΑ Courier
- Speedex
- Γενική Ταχυδρομική
- DHL

### 💰 Διαχείριση Αντικαταβολών
- ✅ TableView με real-time stats
- ✅ Status badges (Αποδόθηκε / Εκκρεμεί)
- ✅ Live statistics (Σύνολο, Αποδοθέντα, Εκκρεμή)
- ✅ Mark as paid functionality
- ✅ Date tracking
- ✅ CRUD operations

**Πεδία Αντικαταβολής:**
- ID Αποστολής
- Κατάσταση (Αποδόθηκε/Εκκρεμεί)
- Ημερομηνία Απόδοσης
- Παραστατικά (ACS, MyPMS)
- Σχόλια

---

## 🎨 UI Design

### Color Palette
```css
Primary (Navy Blue):   #192A56
Success (Green):       #28A745
Danger (Red):          #DC3545
Warning (Orange):      #FFC107
Info (Cyan):           #17A2B8
```

### Typography
- **Font Family:** Segoe UI, Roboto, Arial
- **Base Size:** 14px
- **Titles:** 24-32px bold

### Components
- **Buttons:** Rounded (8px), gradient backgrounds, hover effects
- **Tables:** Alternating rows, hover highlighting, custom headers
- **Forms:** Floating labels, validation styling
- **Cards:** Rounded corners (12px), drop shadows

---

## 🔧 Configuration

### Database
Edit `DatabaseConfig.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/mypms";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

### CSS Customization
Edit `src/main/resources/css/main.css`:
```css
.root {
    -fx-primary: #192A56;        /* Change primary color */
    -fx-font-size-base: 14px;    /* Change base font size */
}
```

---

## 📸 Screenshots

### Dashboard
```
┌─────────────────────────────────────────────────┐
│      MyPMS - Σύστημα Διαχείρισης               │
│           Κεντρικό Dashboard                     │
└─────────────────────────────────────────────────┘

    ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
    │      👥      │  │      📦      │  │      💰      │
    │   Πελάτες    │  │  Αποστολές   │  │Αντικαταβολές │
    │              │  │              │  │              │
    └──────────────┘  └──────────────┘  └──────────────┘

    ● Σύνδεση ενεργή           Έκδοση 2.0 - JavaFX
```

### Customer Management
```
┌─────────────────────────────────────────────────┐
│         👥 Διαχείριση Πελατών                   │
└─────────────────────────────────────────────────┘

🔍 Αναζήτηση: [_______] Κατηγορία: [όλες ▼] [Αναζήτηση] [Καθαρισμός]

┌─────────────────────────────────────────────────┐
│ # │ Κατ │ Επωνυμία  │ Τηλέφωνο │ Email        │
├───┼─────┼───────────┼──────────┼──────────────┤
│ 1 │  A  │ Εταιρία 1 │ 21012... │ info@...     │
│ 2 │  B  │ Εταιρία 2 │ 21013... │ sales@...    │
└─────────────────────────────────────────────────┘

[➕ Νέος] [✏️ Επεξ.] [🗑️ Διαγρ.] [👁️ Λεπτ.] [🔄 Ανανέωση]
```

---

## 🔐 Security Notes

⚠️ **IMPORTANT:** Credentials are currently hardcoded in source code. For production:

1. **Move to external config:**
   ```properties
   # config.properties
   db.url=jdbc:mysql://localhost:3306/mypms
   db.user=root
   db.password=${DB_PASSWORD}
   ```

2. **Use environment variables:**
   ```bash
   export DB_PASSWORD=your_secure_password
   ```

3. **Implement proper authentication**

---

## 🐛 Known Issues

- ✅ All Swing components migrated to JavaFX
- ⚠️ Forms for add/edit operations are placeholders
- ⚠️ ACS Courier API integration needs testing
- ⚠️ Firebase sync not fully implemented
- ⚠️ Excel export functionality to be added

---

## 📝 TODO / Roadmap

- [ ] Implement full forms for CRUD operations
- [ ] Add input validation με ValidationUtils
- [ ] ACS Courier API integration testing
- [ ] Firebase real-time synchronization
- [ ] Excel export για αντικαταβολές
- [ ] Print functionality
- [ ] Reports & Statistics dashboard
- [ ] User authentication system
- [ ] Multi-user support με roles
- [ ] Backup & restore functionality

---

## 🤝 Contributing

1. Fork το repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 📄 License

This project is private. All rights reserved.

---

## 👨‍💻 Author

**Migration to JavaFX by:** Claude Code
**Original Swing Application by:** Your Team

---

## 🆘 Support

Για υποστήριξη:
- Email: support@mypms.gr
- Documentation: [Wiki](https://github.com/dimimeimar/mypms_migration/wiki)
- Issues: [GitHub Issues](https://github.com/dimimeimar/mypms_migration/issues)

---

## 🎓 Learning Resources

### JavaFX
- [JavaFX Documentation](https://openjfx.io/)
- [JavaFX Tutorial](https://docs.oracle.com/javafx/)
- [ControlsFX](https://github.com/controlsfx/controlsfx)

### CSS for JavaFX
- [JavaFX CSS Reference](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html)
- [Modena CSS](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html)

---

**Made with ❤️ using JavaFX**

*Last updated: 2025-10-23*
