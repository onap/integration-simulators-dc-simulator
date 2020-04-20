## DC SIMULATOR

Project consists of two submodules:
1. msasimulator
2. tapisimulator

Detailed information about submodules can be found in ```README.md``` in their directories.

## Project building
```
mvn clean package
```
## Building Docker images
```
mvn clean package -P docker -DskipTests=true

## To bring up two instances of TAPI and one instance of MSA
The configurations are present in  docker-compose.yml. Execute the following
docker-compose up

## To bring it up in the onap cluster chart configurations are present in charts folder
