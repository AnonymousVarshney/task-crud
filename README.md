Pre-requisites :
1. maven should be installed. Following article can be referred to setup maven :
   https://mkyong.com/maven/how-to-install-maven-in-windows/

2. Java 8 should be available.
   It can be installed by following the process here :
   https://mkyong.com/java/how-to-set-java_home-on-windows-10/

3. Go to main folder in which pom.xml is located :

mvn spring-boot:run
This starts the server at port 8080 and CRUD API's are hosted

GET ALL TASKS :
http://localhost:8080/rest/tasks/getall

GET TASK BY ID :
http://localhost:8080/rest/tasks/get/{id}

CREATE TASK (POST CALL):
http://localhost:8080/rest/tasks/create
{
"id":2,
"title":"task-2",
"description":"task-2-description",
"status":"inprogress",
"deadline":"2020-05-20"

}

UPDATE TASK (PUT) :
http://localhost:8080/rest/tasks/update
{
"id":2,
"title":"task-2",
"description":"task-2-description-updated",
"status":"inprogress",
"deadline":"2020-05-20"

}

DELETE TASK (DELETE) :
http://localhost:8080/rest/tasks/delete/{id}

4. For status field valid values :
   "pending", "inprogress", or "completed"

5. 


