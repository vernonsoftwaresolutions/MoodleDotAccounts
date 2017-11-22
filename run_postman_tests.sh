#!/bin/bash

ENV=$1
#Get output values, this is a somewhat naive approach since it is a lot of api calls

newman run integration/Moodle_Accounts.postman_collection.json -e integration/moodle_accounts_${ENV}.postman_environment.json --timeout-request 15000