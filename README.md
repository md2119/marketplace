# Marketplace - REST


How to use:
-----------
1. Please download jar,
      marketplace\target\marketplace-0.0.1-SNAPSHOT.jar
2. Copy the jar to temporary folder
3. Execute the jar (Java 8 required)
      java -jar marketplace-0.0.1-SNAPSHOT.jar
4. Use a REST Client to send REST calls (I used Postman)


API documentation
-----------------
1. To create new user
```
      http://localhost:8080/marketplace/api/users/create
      
      Request Body : 
            name
            userType : buyer/seller
            attributes

      example :
            {
            "name": "DummyUser",
            "userType": "buyer",
            "attributes": {
                  "email" : "axy@gmail.com",
                  "contact" : "12233222222222"
                  }
            }
```
2. To create new project
```
      http://localhost:8080/marketplace/api/projects/create?user={userid}
  
      Request Body : 
           name
           desc
           bidEndDate

      example :
            http://localhost:8080/marketplace/api/projects/create?user=1
            {
            "name": "OldProject",
            "desc": "project description",
            "bidEndDate": "2019-01-12T13:15:30"
            }
```
3. To create a bid for project
```
      http://localhost:8080/marketplace/api/projects/bid?user={userid}&project={projectid}&bid={bidvalue}
 
      example : 
            http://localhost:8080/marketplace/api/projects/bid?user=1&project=5&bid=10000
```    
4. To list all users
```
      http://localhost:8080/marketplace/api/users
```  
5. To list all projects
```
      http://localhost:8080/marketplace/api/projects
```  
6. To find user by id
```
      http://localhost:8080/marketplace/api/users/{userid}
  
      example : 
            http://localhost:8080/marketplace/api/users/1
```    
7. To find project by id
```
      http://localhost:8080/marketplace/api/projects/project/{projectid} 
      
      example : 
            http://localhost:8080/marketplace/api/projects/project/5
```  
8. To find project by status
```
      http://localhost:8080/marketplace/api/projects/{status}
      status : open/closed
  
      example : 
            http://localhost:8080/marketplace/api/projects/open
```

Assumptions
-----------
1. Actors are mutually exclusive buyer cannot be a seller and vice-versa
2. Timezone managment ignored


Other metrics
-------------
1. The time the exercise took (after dev environment is set up) : 3 hrs
2. Exercise Difficulty : Moderate 
3. How did you feel about the exercise itself? (1 lowest, 10 highest—awesome way to assess coding ability) : 8
4. How do you feel about coding an exercise as a step in the interview process?  (1 lowest, 10 highest—awesome way to assess coding ability) : 9
5. What would you change in the exercise and/or process?
