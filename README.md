# Favourite Recipes
Favourite recipes is a spring boot application provides RESTFUL APIs which allow users 
to do a fully CRUD operations like create and update their recipes with ingredients.

### 1- How to BUILD & RUN
#### _Prerequisites:_
* Docker, Docker Compose, Maven and JDK 11 installed and properly configured.
* Ensure ports [8080, 5432] are free during running
  build-and-run-local.sh.

> __I assume you have linux or mac machine like I do.__ 
* Clone the project into your machine.
* Open terminal.
* Change directory (**cd**) to the project folder.
* Give execute permission to **chmod 777 build-app-image-local.sh** 
(this is bash script file which build the project docker image inside your local docker environment).
* Run the next command in your terminal `./build-app-image-local.sh && docker compose up`

Now you can go to welcome page from that link [http://localhost:8080/api/v1/public/welcome](http://localhost:8080/api/v1/public/welcome)
and from the welcome page you can do login and get access which you will need after opening [Swagger-UI](http://localhost:8080/swagger-ui/index.html) page to authorize yourself with the access code and play with RESTFUL APIs.

Enjoy :)
