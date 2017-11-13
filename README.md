# Serverless Spring example
A basic Accounts RESTful API written with the [Spring framework](https://projects.spring.io/spring-framework/). The `LambdaHandler` object is the main entry point for Lambda.

The application uses the [Serverless Application Model](https://github.com/awslabs/serverless-application-model) for deployment. 

This takes the form in two files; formation_assets.yaml and formation_env.yaml.  The 'assets' are all AWS resources that only need to be created once.  That is, API Gateway, Lambda function (not Alias' or versions), AWS certificates, and DNS name
The "env" are the environment specific resources such as Lambda aliases, Lambda versions, Lambda/Gateway Permissions, API Gateway URL Mappings, etc  

## Installation
To build and install the sample application you will need [Maven](https://maven.apache.org/) and the [AWS CLI](https://aws.amazon.com/cli/)

Use maven to build a deployable jar.
```
$ mvn package
```

This command should generate a `moodle-accounts-1.0.jar` in the `target` folder. This is then referenced in the cloudformation template to  
