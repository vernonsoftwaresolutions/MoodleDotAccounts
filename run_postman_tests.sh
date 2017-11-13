#!/bin/bash

ENV=$1
#Get output values, this is a somewhat naive approach since it is a lot of api calls

newman run integration/moodle_accounts.postman_collection.json -e integration/moodle_accounts_${ENV}.postman_environment.json --timeout-request 15000