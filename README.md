# Client MicroService

The **Client MicroService** is the frontend-facing service of the Complaint Management System. It uses **Spring MVC + Thymeleaf** to handle browser requests and acts as an intermediary between the frontend and the backend microservices.

Instead of the browser communicating directly with the User, Complaint, or Admin MicroService, requests are received by the Client MicroService and then forwarded to the appropriate backend service.

---

## Architecture

```text
                    Browser / Thymeleaf
                           |
                           v
                  +-------------------+
                  |  Client MicroService |
                  +-------------------+
                    |       |       |
                    |       |       |
                    v       v       v
                  User   Complaint  Admin
                   MS       MS       MS
                    |       |
                    v       v
                 User DB  Complaint DB
```

### Current request flow

```text
Browser
   |
   v
FrontController
   |
   v
Service Layer
   |
   v
RestTemplate
   |
   v
Backend MicroService
```

---

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Thymeleaf
- RestTemplate
- Maven
- Lombok
- Multipart/form-data
- Java Time API

---

## Project Structure

```text
src/main/java/com/tausif/
│
├── beans/
│   ├── Admin.java
│   ├── Complaint.java
│   └── User.java
│
├── controller/
│   └── FrontController.java
│
└── service/
    ├── AdminService.java
    ├── ComplaintService.java
    └── UserService.java
```

---

# Current Implementation

## 1. FrontController

`FrontController` is the main Spring MVC controller for the Client MicroService.

It currently handles:

- Home page
- User registration
- User home page
- Complaint registration

### Home

```java
@RequestMapping(value = {"/", "/home", "/index"})
public String home(){
    return "index";
}
```

### User Registration

```java
@PostMapping("/userRegister")
public String userRegister(@ModelAttribute User user, Model m){
    boolean result = userService.createUser(user);

    if(result){
        m.addAttribute("msg", "User Registered Successfully! ✌️");
    }else{
        m.addAttribute("msg", "User Already Exist! 😒");
    }

    return "signup";
}
```

The flow is:

```text
Registration Form
      |
      v
FrontController
      |
      v
UserService
      |
      v
RestTemplate
      |
      v
User MicroService
```

---

# 2. UserService

`UserService` communicates with the User MicroService using `RestTemplate`.

The User MicroService URL is configured using:

```java
@Value("${microWebService.user}")
private String userUrl;
```

User registration is forwarded using:

```java
public boolean createUser(User user) {
    Boolean result =
            restTemplate.postForObject(
                    userUrl + "/register",
                    user,
                    Boolean.class
            );

    return result;
}
```

### Current responsibility

- Receive `User` data from the controller
- Send the user data to User MicroService
- Receive the registration result
- Return the result to the controller

---

# 3. ComplaintService

`ComplaintService` handles communication between the Client MicroService and Complaint MicroService.

Complaint registration is slightly more complex because it contains both:

- Normal complaint fields
- Uploaded evidence files

The request is therefore sent as:

```text
multipart/form-data
```

### Complaint flow

```text
Complaint Form
      |
      v
FrontController
      |
      v
ComplaintService
      |
      v
MultiValueMap
      |
      v
HttpEntity
      |
      v
RestTemplate
      |
      v
Complaint MicroService
```

---

## Complaint Data

The current `Complaint` bean contains:

- `id`
- `username`
- `title`
- `description`
- `category`
- `incidentDate`
- `createdAt`
- `latitude`
- `longitude`
- `location`
- `zipCode`
- `evidence1`
- `evidence2`
- `evidence3`

---

## Multipart File Handling

The Client MicroService receives uploaded files as `MultipartFile` objects.

```java
@RequestPart(required = true) MultipartFile e1,
@RequestPart(required = false) MultipartFile e2,
@RequestPart(required = false) MultipartFile e3
```

The first evidence file is required, while the second and third files are optional.

The files are converted to `ByteArrayResource` objects before being sent to the Complaint MicroService.

Example:

```java
new ByteArrayResource(e1.getBytes()){
    @Override
    public String getFilename(){
        return e1.getOriginalFilename();
    }
}
```

### Why `ByteArrayResource`?

`MultipartFile` represents the uploaded file received by the Client MicroService.

`ByteArrayResource` allows the file's bytes to be represented as a Spring `Resource` when constructing the outgoing multipart request.

The overridden `getFilename()` preserves the original uploaded filename.

---

## MultiValueMap

The outgoing multipart request is built using:

```java
MultiValueMap<String, Object> body =
        new LinkedMultiValueMap<>();
```

Normal complaint fields are added to the map:

```java
body.add("username", complaint.getUsername());
body.add("title", complaint.getTitle());
body.add("category", complaint.getCategory());
body.add("description", complaint.getDescription());
body.add("incidentDate", complaint.getIncidentDate());
body.add("latitude", complaint.getLatitude());
body.add("longitude", complaint.getLongitude());
body.add("location", complaint.getLocation());
body.add("zipCode", complaint.getZipCode());
```

Evidence files are added as multipart resources.

The request is then configured with:

```java
HttpHeaders headers = new HttpHeaders();

headers.setContentType(
        MediaType.MULTIPART_FORM_DATA
);
```

and wrapped in:

```java
HttpEntity<MultiValueMap<String, Object>> request =
        new HttpEntity<>(body, headers);
```

Finally, the Client MicroService sends the request:

```java
restTemplate.postForObject(
        complaintUrl + "/registerComplain",
        request,
        Boolean.class
);
```

---

# 4. AdminService

`AdminService` has been created as the Client-side service for future Admin MicroService communication.

Currently it contains:

```java
@Value("${microWebservice.admin}")
private String adminUrl;

private RestTemplate restTemplate = new RestTemplate();
```

Admin-side Client integration is **not yet implemented**.

---

# Beans / Models

## User

The Client-side `User` bean represents the data exchanged with the User MicroService.

```text
User
├── id
├── name
├── email
├── username
├── password
├── country
├── state
└── location
```

---

## Complaint

The Client-side `Complaint` bean represents complaint information exchanged with the Complaint MicroService.

```text
Complaint
├── id
├── username
├── title
├── description
├── category
├── incidentDate
├── createdAt
├── latitude
├── longitude
├── location
├── zipCode
├── evidence1
├── evidence2
└── evidence3
```

---

## Admin

The Client-side `Admin` bean represents Admin data for future Client/Admin integration.

```text
Admin
├── email
├── name
├── username
├── password
├── createdAt
└── isActive
```

---

# Configuration

Backend microservice URLs are externalized using Spring configuration properties.

The Client currently uses properties similar to:

```properties
microWebService.user=...
microWebservice.complaint=...
microWebservice.admin=...
```

This allows backend URLs to be changed without modifying Java source code.

---

# Current Status

### Completed

- [x] Client MicroService created
- [x] Spring MVC controller created
- [x] Thymeleaf frontend structure created
- [x] User bean created
- [x] Complaint bean created
- [x] Admin bean created
- [x] UserService created
- [x] User registration integrated with User MicroService
- [x] ComplaintService created
- [x] Complaint registration controller created
- [x] Multipart request construction implemented
- [x] Evidence file forwarding implemented
- [x] Optional second and third evidence handling implemented
- [x] AdminService skeleton created

### In Progress

- [ ] Complaint registration HTML integration
- [ ] User login integration
- [ ] My complaints integration
- [ ] Complaint details integration
- [ ] User profile integration
- [ ] Admin login integration
- [ ] Admin dashboard integration
- [ ] Admin complaint management
- [ ] Complete frontend-to-backend integration

---

# Current Architecture Decision

The Client MicroService intentionally acts as an intermediary.

The frontend does **not** directly call backend microservices.

```text
                 +----------------+
                 |    Browser     |
                 +----------------+
                         |
                         v
                 +----------------+
                 | Client Service |
                 +----------------+
                    /      |      \
                   /       |       \
                  v        v        v
              User MS  Complaint MS Admin MS
```

This keeps the browser-facing logic inside the Client MicroService and keeps the backend microservices responsible for their own domains.

---

# Future Improvements

This project is intentionally being built as a learning/practice project. More advanced production-oriented features are planned for a future project.

Potential improvements include:

- DTOs across the Client layer
- Bean validation
- Better exception handling
- Centralized error handling
- Proper HTTP status codes
- Password hashing
- Authentication
- Authorization
- Spring Security
- JWT
- CSRF protection
- OAuth2
- Improved service-to-service communication
- Testing
- Integration testing
- Resilience
- Observability
- Production-level configuration

---

# Learning Goals

The Client MicroService is being developed incrementally to gain practical experience with:

- Spring Boot
- Spring MVC
- Thymeleaf
- Layered architecture
- REST communication
- Microservice-to-microservice communication
- `RestTemplate`
- Multipart requests
- `MultipartFile`
- `ByteArrayResource`
- `MultiValueMap`
- Request/response flow
- DTOs and model separation
- JPA/Hibernate integration through backend services
- Building a multi-service application step by step

The project is intentionally kept simple at this stage so that the fundamentals of communication and request flow are understood before introducing more advanced security and distributed-system concepts.
