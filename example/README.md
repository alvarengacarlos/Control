# Example
## Description
This example will provision an Amazon HTTP API Gateway (V2) and an AWS Lambda.

## Requirements
- AWS Account
- AWS CLI 2.23.4
- SAM CLI 1.132.0
- Java 21
- Maven 3.9.9

## Provisioning
> **Warning:** All commands below expect you to be in the `example` directory.

Package the project:
```bash
sam build
```

Create the resources:
```bash
sam deploy --capabilities CAPABILITY_NAMED_IAM
```

Using a browser, access your stage URL appending `/hello`.

> **Tip:** The stage URL can be found in the Amazon API Gateway Console.

## Destroying
Remove the resources:
```bash
sam delete
```

Delete the Amazon CloudWatch Lambda Log Group.

Delete the AWS CloudFormation Stack created by SAM.
