# Detailed description and steps for run project found here: 
https://jarmx.blogspot.com/2022/10/microservices-security-patterns-and.html

# jwt-oauth2.0-spring-security

Ldap user : <br />
Username: henry <br />
Password: 123 <br />

POSTMAN <br />

Get access token  <br />

POST: http://localhost:9000/oauth/token <br />
Type: Basic Auth <br />
Username: henry <br />
Password: secret <br />

![alt text](https://github.com/HenryXiloj/jwt-oauth2.0-spring-security/blob/master/kotlin1.PNG?raw=true)

![alt text](https://github.com/HenryXiloj/jwt-oauth2.0-spring-security/blob/master/kotlin2.PNG?raw=true)

![alt text](https://github.com/HenryXiloj/jwt-oauth2.0-spring-security/blob/master/kotlin3.PNG?raw=true)

GET: headers with   <br />

Key: Authorization  <br />
value:  Bearer {token} <br />

http://localhost:9000/rest/hello <br />

![alt text](https://github.com/HenryXiloj/jwt-oauth2.0-spring-security/blob/master/kotlin4.PNG?raw=true)










