#!/bin/bash

ENV=$1
#Get output values, this is a somewhat naive approach since it is a lot of api calls

newman run integration/moodle_users.postman_collection.json -e "moodle_users_${ENV}.postman_environment.json" --timeout-request 15000