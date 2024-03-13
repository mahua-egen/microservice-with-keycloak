keyCloak: 
http://localhost:8080

admin login:

username: admin
password: admin

need some configuration:
1. First need to create realm.
2. To call admin rest api, use admin-cli client. enable "Client authentication" and "Service accounts roles"
3. Assign realm-admin role to admin-cli, click admin-cli-> click Service accounts roles tab -> click Assign role button -> select Filter by clients -> search realm-admin role -> check it -> save 
4. Use mailhog to send email
5. Install mailhog. http://localhost:8025/
6. Update email configuration from Realm Settings
7. Host=localhost, port=1025, From=test@gmail.com, From display name=Keycloak Admin, Reply to=test@gmail.com, Reply to display name=Keycloak Admin
8. Test connection and then save


# generate token
http://localhost:8765/authentication/token/generate
POST
{
"username":"test-001",
"password": "123"
}

# logout
http://localhost:8765/authentication/token/logout
POST
{
"refreshToken":"${refreshToken}"
}

# create user
http://localhost:8765/authentication/employee/save
Authorization: Bearer ${token}
POST
{
"username":"sunny",
"name":"Mahua Majid Sunny",
"gender":"Female",
"phone_number":"1010101010",
"birthdate":"01/01/2020",
"address":"Dhaka",
"given_name":"Mahua",
"middle_name":"Majid",
"family_name":"Talukder",
"nickname":"Sunny",
"password":"345",
"email":"mahuasunny@gmail.com",
"enabled":true
}

# user list
http://localhost:8765/authentication/employee/list
Authorization: Bearer ${token}
GET

# create role
http://localhost:8765/authentication/role/save
Authorization: Bearer ${token}
POST
{
"name":"role1"
}

# assign role to user
http://localhost:8765/authentication/role/assign-role
Authorization: Bearer ${token}
POST
{
"userId":"bee810f6-fa31-4266-8f2b-8122f470164f",
"roleName":"role1"
}

# create group
http://localhost:8765/authentication/group/save
Authorization: Bearer ${token}
POST
{
"name":"group1"
}

# create group
http://localhost:8765/authentication/group/save
Authorization: Bearer ${token}
POST
{
"name":"group1"
}

# assign role to group
http://localhost:8765/authentication/group/assign-role-group
Authorization: Bearer ${token}
POST
{
"groupId":"dbc656ae-b059-47c6-b45e-c82bce8738b8",
"roleNameList":["role1","role2"]
}

# assign user to group
http://localhost:8765/authentication/group/assign-user-group
Authorization: Bearer ${token}
POST
{
"groupId":"dbc656ae-b059-47c6-b45e-c82bce8738b8",
"userId":"bee810f6-fa31-4266-8f2b-8122f470164f"
}

   
# update password
http://localhost:9004/authentication/employee/update-password
Authorization: Bearer ${token}
PUT

# call microservice 1
http://localhost:8765/currency-exchange/exchange-rate/from/USD/to/BDT
Authorization: Bearer ${token}
GET

# call microservice 2
http://localhost:8765/currency-conversion/conversion/from/USD/to/BDT/quantity/2
Authorization: Bearer ${token}
GET
