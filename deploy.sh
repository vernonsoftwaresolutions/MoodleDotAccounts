#!/bin/bash

set -e

S3_BUCKET=$1
VERSION=$2
ENV=$3
DNSNAME=$4
HOSTEDZONENAME=$5
HANDLER_1=$6
API_NAME=$7
GATEWAY_REF=$8
DYNAMO_TABLE=$9
echo "Packing assets"
##
# Package API Gateway Assets
##
aws cloudformation package --template-file \
    formation_assets.yaml --output-template-file \
    formation_assets_output.yaml --s3-bucket $S3_BUCKET

echo "Deploying assets"
##
# Deploy Assets
##
aws cloudformation deploy --template-file \
    formation_assets_output.yaml --capabilities CAPABILITY_IAM \
    --stack-name ${API_NAME}  --parameter-overrides DNSName="${DNSNAME}" HostedZoneName="${HOSTEDZONENAME}" UsersTableBaseName="${DYNAMO_TABLE}"

echo "Retrieving API Ref"
##
# get the api gateway ref
##
apiGatewayApiRef=`aws cloudformation describe-stacks \
                    --stack-name "${API_NAME}" --query \
                    "Stacks[0].[Outputs[? starts_with(OutputKey, '${GATEWAY_REF}')]][0][*].{OutputValue:OutputValue}" \
                    --output text`

echo "api gateway ref ${apiGatewayApiRef}"
##
# get the current deploymentId
##
deploymentId=`aws apigateway get-stages \
                 --rest-api-id "${apiGatewayApiRef}" \
                 --query "[item[?stageName=='Latest']][0][0].deploymentId" \
                 --output text`
echo "deploymentId ${deploymentId}"

##
# Get the lambda function names and store them as a file
##
#aws cloudformation describe-stacks --stack-name "${API_NAME}" \
#    --query "Stacks[0].[Outputs[?starts_with(OutputValue, 'arn:aws:lambda')]][0][*].{OutputValue:OutputValue}" \
#    --output text > names
#
#echo "retrieved lambda functions, now publish versions"
##
# Now read each lambda function and publish a new version
##
#while read name; do
#    echo "publishing version ${VERSION} to lambda function $name"
#    LAMBDAVERSION=`aws lambda publish-version --function-name ${name} \
#               --description ${VERSION} \
#               --query '{Version:Version}' \
#               --output text`
#    echo "Version ${LAMBDAVERSION} published"
#done < names

HANDLER_1_NAME=`aws cloudformation describe-stacks --stack-name  "${API_NAME}" \
  --query "Stacks[0].[Outputs[?ends_with(OutputValue, '${HANDLER_1}')]][0][*].{OutputValue:OutputValue}" --output text`


echo "retrieved lambda function ${HANDLER_1_NAME}"


LAMBDAVERSION_1=`aws lambda publish-version --function-name ${HANDLER_1_NAME} \
               --description ${VERSION} \
               --query '{Version:Version}' \
               --output text`

##
# Publish new lambda version
##
#LAMBDANAME=`aws cloudformation describe-stacks \
#            --stack-name "${API_NAME}"  \
#            --query "Stacks[0].[Outputs[?starts_with(OutputValue, 'arn:aws:lambda')]][0][*].{OutputValue:OutputValue}" --output=text`

#echo "retrieved lambda name ${LAMBDANAME}"
#LAMBDAVERSION=`aws lambda publish-version \
#                --function-name "${LAMBDANAME}" \
#                --description "${VERSION}" \
#                --query '{Version:Version}' --output=text`

##
# Package the environment template
##
aws cloudformation package --template-file \
    formation_env.yaml --output-template-file \
    formation_env_output.yaml --s3-bucket $S3_BUCKET

##
# Deploy to dev
##
echo "about to deploy environment with variables ${apiGatewayApiRef} ${ENV} ${deploymentId} ${LAMBDAVERSION_1} ${DNSNAME}"

aws cloudformation deploy --template-file \
    formation_env_output.yaml --capabilities CAPABILITY_IAM \
    --stack-name "${API_NAME}-${ENV}" \
    --parameter-overrides ApiGateway="${apiGatewayApiRef}" \
     StageName="${ENV}" DeploymentId="${deploymentId}" DomainName="${DNSNAME}" \
     PostUsersVersion="${LAMBDAVERSION_1}" DomainName="${DNSNAME}"
##
# Deploy to stage
##
ENV="stage"

echo "about to deploy environment with variables ${apiGatewayApiRef} ${ENV} ${deploymentId} ${LAMBDAVERSION_1} ${DNSNAME}"

aws cloudformation deploy --template-file \
    formation_env_output.yaml --capabilities CAPABILITY_IAM \
    --stack-name "${API_NAME}-${ENV}" \
    --parameter-overrides ApiGateway="${apiGatewayApiRef}" \
     StageName="${ENV}" DeploymentId="${deploymentId}" DomainName="${DNSNAME}" \
     PostUsersVersion="${LAMBDAVERSION_1}" DomainName="${DNSNAME}"
