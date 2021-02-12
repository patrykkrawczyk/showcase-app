#!/bin/bash -e

#631572178930.dkr.ecr.eu-west-1.amazonaws.com/showcase-service-prod
#Usage: IMAGE=631572178930.dkr.ecr.eu-west-1.amazonaws.com/showcase-service-prod ./deploy.sh [create|update]

mkdir -p generated

# register task-definition
sed <task-definition.json -e "s,\"@@VERSION@@\",\"$IMAGE\",">generated/TASKDEF.json
aws ecs register-task-definition --cli-input-json file://generated/TASKDEF.json > generated/REGISTERED_TASKDEF.json
TASKDEFINITION_ARN=$( < generated/REGISTERED_TASKDEF.json jq .taskDefinition.taskDefinitionArn )

# create or update service
sed "s,\"@@TASKDEFINITION_ARN@@\",$TASKDEFINITION_ARN," <service-$1.json >generated/SERVICEDEF.json
aws ecs $1-service --cli-input-json file://generated/SERVICEDEF.json | tee generated/SERVICE.json
