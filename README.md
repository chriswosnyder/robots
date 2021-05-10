###### Project stack technologies
- java 8 
- Maven
- Lombok
- Junit 5


###### Project launch

**Build** 
`mvn clean install`

**Run Junit Tests**
`mvn test`

**Spring boot start maven**
`mvn spring-boot:run`


**Api Rest**
`curl -v localhost:8080/events/all
curl -v localhost:8080/events/all/1
curl -X POST localhost:8080/events/1 -H 'Content-type:application/json' -d 
curl -X POST localhost:8080/events/robotsCount?timeIntervalMilliseconds=?3600 -H 'Content-type:application/json' -d 
`

**Swagger api doc**
localhost:8080/swagger-ui/
http://localhost:8080/v2/api-docs
