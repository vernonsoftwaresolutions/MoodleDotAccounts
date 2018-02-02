#!/bin/bash

set -e

S3_BUCKET=$1
ENV=$2
DNSNAME=$3
HOSTEDZONENAME=$4
API_NAME=$5
DYNAMO_TABLE=$6
POOL_NAME=$7

echo "Packing assets"
##
# Package API Gateway Assets
##
aws cloudformation package --template-file \
    lambda_api.yml --output-template-file \
    lambda_api_output.yaml --s3-bucket $S3_BUCKET

echo "Deploying assets"
##
# Deploy Assets
##
aws cloudformation deploy --template-file \
    lambda_api_output.yaml --capabilities CAPABILITY_IAM \
    --stack-name ${API_NAME}  --parameter-overrides \
    DNSName=${DNSNAME} HostedZoneName=${HOSTEDZONENAME} TableBaseName=${DYNAMO_TABLE} Stage=${ENV} PoolName=${POOL_NAME}

