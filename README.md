Teste Prático Concrete - Java Web Api
Tecnologias Ultilizadas
Java 8
Spring Rest
Spring Injection Dependency
Hibernate
Maven
Tomcat 8.0
BCryptPasswordEncoder ( Spring Security crypto )
JWT Token
Execute Tests Curl :

Save User : curl --header "Content-Type: application/json" --request POST --data '{"name":"xyz","password":"123","email":"dfredmota@gmail.com","phones":[{"number":"9999","ddd":"85"}]}' http://localhost:8080/desafio/usuario

Login : curl --header "Content-Type: application/json" --request POST --data '{"password":"123","email":"dfredmota@gmail.com"}' http://localhost:8080/desafio/login

Profile Sem Token : curl -i -H "Content-Type: application/json" --request GET http://localhost:8080/desafio/profile/1

Profile User com Token : curl -i -H "Content-Type: application/json" -H "Authentication: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ4eXoifQ.fhkfryp6P2UB5OBn8HUqn8QXG6_QTkry5EWZ8lvkV5TW_V8bKCtLM74tQV7tnDxs9-uZFiz1oNKsYdu1Rl67Hw" --request GET http://localhost:8080/desafio/profile/1
