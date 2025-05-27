BilAbonnement er en webapplikation til håndtering af bilabonnementer, der tilbyder brugervenlig administration af biler, kunder og abonnementer med integration til MySQL og Spring Boot.

Indhold
- [Teknologier](#teknologier) 
- [Contributors](#contributors)  
- [Installation](#installation)  
- [Rapport](#rapport)  

## Teknologier

    Java 17

    Spring Boot

    Maven

    MySQL

    Thymeleaf

    CSS 
    
## Contributors

    Hjalte Kappel Larsen

    Mathias Bendix Mortensen

    Valdemar Støvring Sørensen

    Nikodem Kondratowicz 

## Installation

    Klon repository
    git clone https://github.com/brugernavn/BilAbonnement.git
    cd BilAbonnement

    Konfigurer database

        -  Opret en MySQL-database.

        -  Kør BilAbonnement-sql-ddl-gruppe3.txt og 
           BilAbonnement-sql-insert-ddl-gruppe3.txt 
           i SQL fra src/main/resources/sql.

        -  Tilpas src/main/resources/application.properties:
        -  spring.datasource.url=jdbc:mysql://<HOST>:3306/<DB_NAME>
        -  spring.datasource.username=<USERNAME>
        -  spring.datasource.password=<PASSWORD>

    Byg og kør applikationen

    Kør disse i terminal
    
        - mvn clean package
        - mvn spring-boot:run

    Åbn i browser
    Besøg http://localhost:8080 for at se applikationen, eller benyt linket i rapporten.


## Rapport

    Rapport kan tilgås her:
    link

  
