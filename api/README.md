# Java-API
Java API provide back-end functionality for a front end React application.

You need to authenticate (POST /user/login) to get a JWT token.

Initially built in Kotlin, using:
* <https://www.callicoder.com/kotlin-spring-boot-mysql-jpa-hibernate-rest-api-tutorial/>
* <https://github.com/callicoder/kotlin-spring-boot-jpa-rest-api-demo>

For OAuth/JWT see:
* <https://github.com/quangIO/spring-kotlin-jwt-sample>
* <https://auth0.com/blog/how-to-create-a-kotlin-app-and-secure-it-using-jwt/>

Authenticate
```
curl -i -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password": "admin"}' http://localhost:8080/user/login

Returns a token like: eyJhbGciOiJIUzI1NiJ9.ey...
```

Get all users/articles:
```
curl -i -H "Content-Type: application/json" -H "Authorization: <token>" -X GET http://localhost:8080/api/user
curl -i -H "Content-Type: application/json" -H "Authorization: <token>" -X GET http://localhost:8080/api/article
```

Create/Update Article
```
curl -i -H "Content-Type: application/json" -H "Authorization: <token>" -X POST \
-d '{"title": "How to learn Spring framework", "content": "Resources to learn Spring framework"}' \
http://localhost:8080/api/articles

curl -i -H "Content-Type: application/json" -H "Authorization: <token>" -X PUT \
-d '{"title": "Learning Spring Boot", "content": "Some resources to learn Spring Boot"}' \
http://localhost:8080/api/articles/1
```

