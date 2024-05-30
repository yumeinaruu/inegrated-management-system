![Integrated Management System](pics/Integrated_Management_System.png)

[![Release Date](https://img.shields.io/badge/Released%20In-May-blue)](https://img.shields.io/badge/Released%20In-December-blue)   [![Made For](https://img.shields.io/badge/Made%20For-C71%20Group-blue)](https://img.shields.io/badge/Made%20For-C71%20Group-blue)

My `Integrated Management System` is the most secure and useful application for universities.

Developed for macOS, Windows, Linux, iOS and Android.

**Usage**
---

```
Usage: By endpoints in ui made by Swagger framework
  Developed by Lisavy Stanislau -> (Github: yumeinaruu)


Options:
  1          Endpoints dedicated to security start with /security
  2          Endpoints dedicated to users start with /user
  3          Endpoints dedicated to subjects start with /subject
  4          Endpoints dedicated to speciality start with /speciality
  5          Endpoints dedicated to schedule start with /schedule
  6          Endpoints dedicated to marks start with /marks
  7          Endpoints dedicated to groups start with /group
  8          Endpoints dedicated to files start with /file
  9          Endpoints dedicated to faculties start with /faculty
  10         Endpoints dedicated to email start with /mail
  11         Endpoints dedicated to departments start with /department
  12         Endpoints dedicated to info by roles start with /find

  Some endpoints are protected with JWT security and you'll not be able to reach them without a token
```

**Installation Options**
---
 On local machine:
  1. Install docker
  2. git clone https://github.com/yumeinaruu/integrated-management-system
  3. docker-compose up -d
  4. go to http://localhost:8080/swagger-ui/index.html#/
  5. go to security/token endpoint and get your JWT token
  6. insert token on swagger page in form
  
 On remote server:
 1. create a droplet on DigitalOcean
  2. connect to server by root@ip_adress_of_droplet through concole using wsl (or just concole if using linux)
  3. curl -fsSL https://get.docker.com -o get-docker.sh
  4. sudo sh get-docker.sh
  5. git clone https://github.com/yumeinaruu/integrated-management-system
  6. docker compose up --build
  7. go to http://{YOUR_DROPLET_IP}:8080/swagger-ui/index.html#/
  8. go to security/token endpoint and get your JWT token
  9. insert token on swagger page in form

WARNING: If you want to use your own database remove/make volume in docker-compose.yml, then make database for yourself. SQL commands are written in db-info.sql file


**What's the project about?**
---

1. What You Need to Know Before Executing

    + Where all the info is stored?
        - Beforehand, the program contains a data/db package with db info, but you can create a volume by yourself. Files are located in /data package 
    + Where info for .properties file is?
        - In .env file.
2. What Technologies Were Used?
    + PostgreSQL as Database
    + Spring Framework 
        - Spring Boot
        - Spring MVC
        - Spring Security
        - Spring AOP
    + Redis for caching
    + Slf4g for logging
    + Swagger
    + Docker

**How to Contribute**
---
1. Clone repo and create a new branch: `$ git checkout https://github.com/yumeinaruu/integrated-management-system -b name_for_new_branch`.
2. Make changes and test
3. Submit Pull Request with comprehensive description of changes


**Acknowledgements**
---

+ [Banner Maker](https://banner.godori.dev/) for logo.

**Donations**
---

Software is distributed by [GNU GPL](https://www.gnu.org/licenses/gpl-3.0.txt)
