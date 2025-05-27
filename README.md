BilAbonnement
Indhold

   - [Contributors](#contributors)  
- [Installation](#installation)  
- [Folder Struktur](#folder-struktur)  
- [Kørsel](#kørsel)  
- [Rapport](#rapport)  
    
Contributors

    Hjalte Kappel Larsen

    Mathias Bendix Mortensen

    Valdemar Støvring Sørensen

    Nikodem Kondratowicz 

Installation

    Klon repository
    git clone https://github.com/brugernavn/BilAbonnement.git
    cd BilAbonnement

    Konfigurer database

        - Opret en MySQL-database (fx via Azure).

        - Kør BilAbonnement-sql-ddl-gruppe3.txt og BilAbonnement-sql-insert-ddl-gruppe3.txt i SQL fra src/main/resources/sql.

        - Tilpas src/main/resources/application.properties:
        - spring.datasource.url=jdbc:mysql://<HOST>:3306/<DB_NAME>
        - spring.datasource.username=<USERNAME>
        - spring.datasource.password=<PASSWORD>

    Byg og kør applikationen

    Kør disse i terminal
    
        - mvn clean package
        - mvn spring-boot:run

    Åbn i browser
    Besøg http://localhost:8080 for at se applikationen, eller benyt linket i rapporten.


Rapport

    Rapport kan tilgås her:
    link

  
