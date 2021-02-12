# Showcase App

## Information

Hello, this is a simple Java 11 + Spring Boot 2 application allowing for CRUD operations on entities:
* BankAccount
* Person
* Profession

Relationships:
* Single BankAccount belongs to a single Person
* Single Person can have many BankAccounts
* Many People can have many Professions

The app is deployed on AWS using Docker on ECS.

It connects to Postgres DB using RDS.

Infrastructure managed with Terraform.

CI/CD using Circle CI

Whole infrastructure utilizes ECS cluster with custom EC2 instances.

It's using Load Balancers to ditribute load between tasks running in the cluster, so we can scale it up if needed.

## Endpoints
AWS: `http://prod-showcase-2011392909.eu-west-1.elb.amazonaws.com/swagger-ui/`

Circle CI: `https://app.circleci.com/pipelines/github/patrykkrawczyk/showcase-app?invite=true`

API: `http://localhost:8080/api`

Swagger: `http://localhost:8080/v2/api-docs`

Swagger UI: `http://localhost:8080/swagger-ui/`

Health: `http://localhost:8080/actuator/health`

## Running locally

```
./mvnw clean package
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker-compose -f docker/app.yml up -d --build

docker-compose -f docker/app.yml down
```

## Development

Start DB: `docker-compose -f docker/postgresql.yml up -d`

Stop DB: `docker-compose -f docker/postgresql.yml down`

Run app: `./mvnw`

## Testing

`./mvnw verify`


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
terraform plan -input=false -var-file=prod.tfvars -out=planned.tfplan
terraform apply planned
```

Replace value of SSM key with actual RDS host:
```
/showcase/db/host
```

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
