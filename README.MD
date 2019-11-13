# Spring Boot with JWT and Jersey integrated Starter Kit.

### Description

<pre>
This Application is built for those who want to develop an spring-boot jwt auth based application.this can be used as a starter kit for them.
 1. It is configured with JWT.
 2. Added support of jersey.
 3. Configured with Mysql(You can use any database as per your requirement).
</pre>


### Follow these step to configure and run application.

1. <b><u>application.yml</u></b>
    <pre>
    a.As this application is configure with Mysql.so if you have mysql   database then just url(datababse name) and username and password as  per your configuration.
    if you have any other database please change url, username and password accordingly.
    b. In <b><u>jwt</u></b> section please change expires_in value(currently it is 5 minutes. it means jwt will be expired on every 5 minutes)
    c. you can also change your secret.
    </pre>
    <b>Now Your application is ready to start.</b>

2. Once your application is up. do following things.

    <pre>
        a. <b><u>SIGNUP USER </u></b>
            from postman(or web) call with this data.
            Endpoint: <a>http://localhost:8080/users/save</a>
            Method: POST
            Request_body : { "id":1, 
                            "username": "anishjha93@gmail.com", 
                            "password": "anish", 
                            "first_name": "Anish", 
                            "last_name": "Jha", 
                            "email": "anishjha93@gmail.com", 
                            "phone_number": "1234567890", 
                            "enabled": true, 
                            "created_by": "anish", 
                            "updated_by": "anish"
                            }
            headers: {'Content-Type': 'application/json'}

        response: {status:200} //if every thing goes smoothly
    2. <b><u>SIGNIN USER </u></b>
         Endpoint:  <a>http://localhost:8080/auth/login</a>
         Method: POST
         Request_body : {
            "username":"anishjha93@gmail.com",
            "password":"anish"
         }
         headers: {'Content-Type': 'application/json'}
         Response: here you will get access_token if every thing goes well.
         copy that access token pastes in other apis.
    3.  <b><u>GET USERS</u></b>
        Endpoint: <a>http://localhost:8080/users/all</a>
        Method: GET
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'access token which you will get in sign in api response'
            }
        Response: List of users which is stored in database.
    </pre>


    <p>That's it. if you want to check more api create your own endpoint</p>


