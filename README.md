# Docker / Kubernetes / Microservice Workshop

## Pour lancer le projet

### Lancer minikube avec les commandes suivantes
```
minkube start
eval $(minikube docker-env)
```
### Lancer le service héro

Dans le fichier heros et executer ces commandes :
```
mvn package 
docker build -f src/main/docker/Dockerfile.jvm -t workshop/heros .
kubectl delete deployment heros
kubectl apply -f kubernetes/
```

Ou alors vous pouvez directement lancer cette commande :
```
mvn package; docker build -f src/main/docker/Dockerfile.jvm -t workshop/heros .; kubectl delete deployment heros; kubectl apply -f kubernetes/
```

Vous pouvez également récupérer l'image sur dockerhub via cette commande :
```
docker pull m2gibonatov/heroes_services
```

### Lancer le service villain

Dans le fichier villain et executer ces commandes :
```
mvn package 
docker build -f src/main/docker/Dockerfile.jvm -t workshop/villain .
kubectl delete deployment villain
kubectl apply -f kubernetes/
```

Ou alors vous pouvez directement lancer cette commande :
```
mvn package; docker build -f src/main/docker/Dockerfile.jvm -t workshop/villain .; kubectl delete deployment villain; kubectl apply -f kubernetes/
```

Vous pouvez également récupérer l'image sur dockerhub via cette commande :
```
docker push m2gibonatov/villains_services
```

### Lancer fight

Dans le fichier fight et executer ces commandes :
```
mvn package 
docker build -f src/main/docker/Dockerfile.jvm -t workshop/fight .
kubectl delete deployment fight
kubectl apply -f kubernetes/
```

Ou alors vous pouvez directement lancer cette commande :
```
mvn package; docker build -f src/main/docker/Dockerfile.jvm -t workshop/fight .; kubectl delete deployment fight; kubectl apply -f kubernetes/
```

Vous pouvez également récupérer l'image sur dockerhub via cette commande :
```
docker push m2gibonatov/fight_services
```

## Chaos

Vous pouvez lancer le ChaosCommand.java avec la commande suivante en vous mettant dans le fichier chaos.
```
mvn quarkus:dev -Dquarkus.args='--deployment=...'
```

En remplacent les 3 petits points par le nom du service "heros", "villain" ou "fight"

Cette commande va supprimer aléatoirement 1,2 ou 3 pods du nom que vous avez mis dans deployment.
