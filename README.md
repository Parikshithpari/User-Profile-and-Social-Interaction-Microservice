# User-Profile-and-Social-Interaction-Microservice
This Spring Boot microservice enables user profile creation and follow/unfollow functionality in a modular microservice-based system. Designed for social platforms or any application requiring user interactions, it depends on a separate user registration service for authentication and basic user data retrieval.


# 🧪 User Profile Service & Controller Test Suite

This test suite ensures robust behavior for core functionalities in the `UserService` and `UserController` layers of a Spring Boot application managing user profiles and social features (e.g., follow/unfollow).

---

## 📁 Test Coverage

### ✅ Service Layer Tests (`UserServiceTest.java`)
| Method Tested             | Description                                                        |
|---------------------------|--------------------------------------------------------------------|
| `createUserProfile(...)`  | Validates user creation flow and handles non-registered users      |
| `getUsersByUserName(...)` | Fetches a user profile by username                                |
| `followTheUser(...)`      | Simulates following another user and updates relationships         |
| `unFollowTheUser(...)`    | Simulates unfollowing and ensures relationships are removed        |

---

### 🌐 Controller Layer Tests (`UserControllerTest.java`)
| Endpoint                  | Method  | Description                                                  |
|---------------------------|---------|--------------------------------------------------------------|
| `/create`                 | POST    | Creates a new user profile via request parameters            |
| `/follow/{current}/{target}` | POST | Establishes a follow relationship between users              |
| `/getUser/{userName}`     | GET     | Retrieves user profile by username                           |

---

## 🛠️ Tech Stack

- **Spring Boot** `@WebMvcTest`, `@ExtendWith(MockitoExtension.class)`
- **Mockito** for mocking repository and service behavior
- **JUnit 5** for assertions and lifecycle management
- **MockMvc** for HTTP request simulation
- **Optional**: `jsonPath()` for verifying response payloads

---

## 🚀 How to Run Tests

You can run all tests via:

```bash
./mvnw test
