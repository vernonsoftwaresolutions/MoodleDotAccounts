#!/bin/bash

#Get output values, this is a somewhat naive approach since it is a lot of api calls

newman run integration/Moodle_Users.postman_collection.json --timeout-request 15000