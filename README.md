# 🎮 GameHub Pro

A full-stack game library web application built using **Spring Boot, Thymeleaf, and MySQL**.

GameHub Pro allows users to explore games, watch trailers, save favorites, and manage a personal game collection — just like a mini Steam platform.

---

## 🚀 Features

### 👤 User Features

* Register & Login system 🔐
* Profile dashboard 👤
* Add / Remove favorite games ❤️
* Personal game library 📚

### 🎮 Game Features

* Browse games
* Game details page
* Trailer popup modal 🎬
* Related games section
* Search & filter system 🔍

### 🛠 Admin Features

* Admin dashboard
* Add new games
* Edit existing games
* Delete games

---

## 🛠 Tech Stack

* **Backend:** Spring Boot (Java 17)
* **Frontend:** Thymeleaf, HTML, CSS, JavaScript
* **Database:** MySQL
* **Security:** Spring Security (Login system)
* **ORM:** Spring Data JPA / Hibernate

---

## 📸 Screenshots

### 🏠 Home Page

![Home](docs/images/home.png)

### 🎮 Games Page

![Games](docs/images/games.png)

### 📄 Game Details

![Details](docs/images/details.png)

### 📚 My Library

![Library](docs/images/library.png)

### 👤 Profile

![Profile](docs/images/profile.png)

### 🛠 Admin Dashboard

![Admin](docs/images/admin.png)

---

## ⚙️ How to Run

1. Clone the repository:

```bash
git clone https://github.com/DulithThenuka/gamehub-pro.git
```

2. Open project in IntelliJ or VS Code

3. Create MySQL database:

```
gamehub_db
```

4. Update `application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

5. Run the project:

```bash
mvnw.cmd spring-boot:run
```

6. Open in browser:

```
http://localhost:8080
```

---

## 🔑 Default Admin Login

```
Email: admin@gamehub.com
Password: admin123
```

---

## 🧠 What I Learned

* Full-stack development with Spring Boot
* Authentication & security using Spring Security
* MVC architecture design
* Database relationships (User ↔ Favorites ↔ Games)
* Building real-world UI/UX features

---

## 📌 Future Improvements

* Game reviews & ratings system ⭐
* Dark/light theme toggle 🌗
* REST API version (React frontend)
* Deployment (AWS / Render)

---

## 👤 Author

**Dulith Thenuka**
Software Engineering Student
Aspiring Game Developer 🎮
