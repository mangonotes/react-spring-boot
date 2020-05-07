# Full stack react application with spring boot #


Images are stored in Database.

## How To run ## 
Clone source into computer 
goto MemberService folder 
run below command in terminal\
mvn spring-boot:run

Database query tool available in  http://localhost:8080/h2-console


## Docker Command ##
Build project 
```
mvn clean install
```

Docker build 
```
sudo docker build -t membership-app .

```
Docker run the project 

```
sudo docker run -p 8181:8181 -e JAVA_OPTS=-Dserver.port=8181 membership-app
```



