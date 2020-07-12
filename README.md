# Blog Post REST API Prototype
using Spring Boot + Spring Data JPA + H2 + HATEOAS

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/hashnet/blogpostapi.git
```

*2. CD into the folder**

```bash
cd blogpostapi
```

**2. Run the app using maven**

```bash
./mvnw spring-boot:run
```

## API Explorer
### Sample curls
```bash
curl -v localhost:8080/posts -u devuser:devpass
curl -v localhost:8080/posts -H 'Accept:application/xml' -u devuser:devpass | tidy -xml -iq
curl -v localhost:8080/posts -u devuser:devpass | json_pp
curl -v  -u devuser:devpass localhost:8080/postsbypage?page=1&size=3&sort=title,desc
curl -vX POST localhost:8080/posts -H 'Content-type:application/json' -d '{"title": "Some Title", "text": "Some Text"}' -u devuser:devpass
curl -vX PUT localhost:8080/posts/3 -H 'Content-type:application/json' -d '{"title": "Some Updated Title", "text": "Some Updated Text"}' -u devuser:devpass
curl -vX DELETE localhost:8080/posts/3 -u devuser:devpass
curl -v localhost:8080/posts/3/enable -u devuser:devpass
curl -v localhost:8080/posts/3/disable -u devuser:devpass
curl -v localhost:8080/users -u devuser:devpass
curl -v localhost:8080/users/5/posts -u devuser:devpass
```
### Postman
A Postman collection for easy testing is available here: https://www.getpostman.com/collections/d58e8c741797b865f437

HTTP Authentication: Basic
User: devuser
Password: devpass

## APP Links
### DB administration
Embedded H2 database can be acceessed at: http://localhost:8080/h2-console/

Credentials:
JDBC URL: jdbc:h2:mem:testdb
User Name: sa

### Swagger Documentation
Swagger api docs are available at: http://localhost:8080/v2/api-docs
Swagger UI IS published at; http://localhost:8080/swagger-ui.html


## APP Features

1. The controller classes are annotated with @CrossOrigin [CORS (Cross-Origin Resource Sharing)] so that the client request originated from separate host and/or port from the API can be served. This helps in running the client in separate docker container connected through user defined network.

2. Implemented hyperlinks in JSON/XML responses based on HAL flavour of HATEOAS for easy discoverability of the apis.

3. Implemented pagination and sorting for findAll().

4. Both JSON and XML is allowed as response and request and can be negotiated through HTTP Header based Content Negotiation.

5. Project name, description and version is loaded from pom.xml to application.properteis to swagger configuration so that consistent info can be presented in Swagger UI.

6. Implemented Swagger documentation for easy

## Future Plan
1. Logging of all controller method either through manual logging or through AOP.
2. Unit testing to test DB operations and HTTP Response Status along with Json body.
3. plitting of properties files for PROD and DEV.
4. Move the username/password/role definition into databse.
