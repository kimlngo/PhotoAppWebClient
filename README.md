# PhotoAppWebClient
## Introduction

PhotoAppWebClient is a Spring Boot Service running on 
Spring Boot **v3.4.1**. It communicates with 
Authorization Server and API Gateway to query the 
Album-Resource-Server.

## System Architecture
![PhotoAppWebClient Architecture](/System%20Architecture.png)

## Operations
### System Start Up
1. KeyCloak Authorization Server starts
2. Eureka Discovery Service starts
3. Album Resource Server starts
4. API Gateway starts and fetches IP addresses of 
Resource Servers via Discovery Service
5. PhotoAppWebClient starts

### Operation Flow
1. PhotoAppWebClient exchanges with KeyCloak Authorization
Server to request Access Token
2. Upon received Access Token, PhotoAppWebClient communicates
with API Gateway and includes Access Token
3. API Gateway checks the Access Token with Authorization Server
and forward the request to Album Resource Server when the token is valid
4. Album Resource Server responds with data to PhotoAppWebClient via 
API Gateway

## Access Token Retrieval Mechanism
There are two ways the PhotoAppWebClient can include the access token:
1. [Using RestTemplate](https://github.com/kimlngo/PhotoAppWebClient/commit/7b1e0a7bd7aa72f1cd845b45db340d00cd2619a6)
2. [Using WebClient](https://github.com/kimlngo/PhotoAppWebClient/commit/fdc59557406e86c08d94a28c738054dc61608d90)