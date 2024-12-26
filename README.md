# ConsomiTounsi

Ce projet utilise une architecture Maven avec des modules parent et sous-modules. Le core API contiendra les queries, commands et events de tous les microservices.

## Structure du projet

- **Parent Module**: Contient les configurations et dépendances communes.
- **Core API**: Contient les queries, commands et events partagés entre les microservices.
- **Sous-modules**: Chaque microservice est un sous-module Maven.
- **Config-Files**: Répertoire utilisé pour stocker les fichiers de configuration YAML de chaque microservice.

## Création d'un nouveau microservice

Pour créer un nouveau microservice, par exemple `ventes`, utilisez la commande suivante :

```sh
mvn archetype:generate "-DgroupId=com.example" "-DartifactId=ventes" "-Dpackage=com.example.ventes" "-DinteractiveMode=false"
```

Cette commande génère un nouveau sous-module Maven pour le microservice `ventes`.

Chaque microservice doit inclure un fichier Dockerfile pour créer une image Docker. 

Ajoutez ensuite le microservice au fichier docker-compose.yml sous services pour l’orchestration.

