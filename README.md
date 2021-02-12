# Showcase App

## TBD
* auditing
* cache
* auth
* test containers
* constraints
* TLS
* move to api first development
* test relationships
* increase unit test coverage (IT should be ok)
* add profiles and profile based bean creation
* split infrastructure and deployment terraform modules

## Information

API: `http://localhost:8080/api`

Swagger: `http://localhost:8080/v2/api-docs`

Swagger UI: `http://localhost:8080/swagger-ui/`

Health: `http://localhost:8080/actuator/health`

## Development

*Requires Java 11*

Start DB: `docker-compose -f docker/postgresql.yml up -d`

Stop DB: `docker-compose -f docker/postgresql.yml down`

Run app: `./mvnw`

## Testing

`./mvnw verify`

## Running locally

```
./mvnw clean package
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker-compose -f docker/app.yml up -d --build

docker-compose -f docker/app.yml down
```

## Deploying to AWS
This is done by CI automatically.

To deploy from local machine, make sure you have AWS CLI credentials configured then:

```
cd terraform/s3_backend
terraform apply
```

Create SSM parameters at keys:
```
/showcase/db/username
/showcase/db/password
/showcase/db/host
```

Execute:
```
cd terraform
terraform plan -input=false -var-file=prod.tfvars  -out=planned.tfplan
terraform apply planned
```

Replace value of SSM key with actual RDS host:
```
/showcase/db/host
```
