# Quiz App Services

A Spring Boot microservices application that allows admins to manage questions and quizzes, while students/programmers can take quizzes, submit answers, and view results. Implemented using RESTful APIs with MySQL for data persistence.

## Features
- Admins can perform CRUD operations on questions.
- Admins can create and manage quizzes.
- Students/programmers can take quizzes and submit answers.
- Students/programmers can view quiz results.
- Services communicate via RESTful APIs.
- Layered architecture with validation and exception handling ensures scalability and maintainability.

## Tech Stack
- Java 21
- Spring Boot
- MySQL
- Spring Data JPA
- Maven
- RESTful APIs
- Microservices architecture

## Setup
```bash
git clone https://github.com/Siva-Bandla/quiz-microservices.git
cd quiz-microservices
mvn spring-boot:run

##API end points
GET /question/allQuestions → view all questions
GET /question/category/{category} → view questions by category
GET /question/difficultyLevel/{diffLevel} → view questions by difficulty level
POST /question/add → Add a new question 
PUT /question/update/{id} → Update a question 
DELETE /question/delete/{id} → Delete a question
POST /quiz/create → Create a new quiz
GET /question/get/{id} → view question by id
POST /quiz/getScore → Submit answers for a quiz, also returns result
