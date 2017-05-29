# wm-jug-jwt-demo
Code for the securing microservices with JWT WM JUG talk in May.

There are two projects that are used in this demo, AuthenticationService and PreferredFilmsService. Load them both into 
your IDE and start AuthenticationService first. This is because PreferredFilmsService when it starts up attempts to get 
the AuthenticationService's public key which it will need to verify the JWT that the AuthenticationService.

AuthenticationService runs from port 8080
PreferredFilmsService runs from port 8181

When both of the microservices are up you will need to call the authentication service e.g. localhost:8080/authenticate?username=marc.thomas
this will return a JWT which is then required by PreferredFilmsService.

Next call localhost:8181/preferred-films?jwt=<JWT_INSERTED_HERE> and you should see the JSON returned.

This code is far from finished and doesn't have any real error/exception handling in it but you can use it to explore JWTs and the JJWT
library that helps generate and process JWTs.
