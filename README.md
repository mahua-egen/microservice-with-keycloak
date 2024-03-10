keyCloak: 
http://localhost:8080

admin login:

username: admin
password: admin

need some configuration:
1. First need to create realm.
2. To call admin rest api, use admin-cli client. enable "Client authentication" and "Service accounts roles"
3. Assign realm-admin role to admin-cli 
4. Use mailhog to send email: http://localhost:8025/
   
