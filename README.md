# Accomodation-Mgmt
An application for accommodation listings, in which hotelier can manipulate the information that they want to display on our platforms.

### Acceptance criteria
1. User can get all the items for the given hotelier.
2. User can get a single item.
3. User can create new entries.
4. User can update information of any of my items.
5. User can delete any item.
6. User can book an item whenever is called reduces the accommodation availability, fails if there is no availability.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)

## Prerequisites

Make sure you have the following tools and technologies installed:

1. Java JDK (v11 or higher)
2. Sprinboot (2.7.15-SNAPSHOT)
3. Maven (for building)
4. Kotlin (1.7.10)
5. PostgreSQL database server(15.3)
6. Docker (for Dockerization)
7. Postman (for API testing)
8. IntelliJ IDEA (2023.1.2) or your favorite IDE or text editor

## Installation and Setup

 Step-by-step instructions for setting up the project locally.

1. How to install dependencies.
- use mvn clean command to clean the project
- use mvn install command to install the dependencies

2. How to configure environment variables.
- Set the project structure, its modules by adding required information to the project's environment.
- add the compiler settings 
- add the maven repository path to the project 
- add the jdk path to the project

## Deployment Strategies

Supports,

1. Normal Maven build and run
2. Containerized build and run (via Dockerfile)
3. Containerized build and run (via docker-compose.yml)

## Usage

Application url- http://localhost:8080/api/
Endpoints - 
POST createItem
<details>
  <summary>http://localhost:8080/api/hoteliers/100/items</summary>
  
```
body- raw json
{
    "hotelierId":100,
"name": "Example name",
"rating": 5,
"category": "hotel",
"location": {
"city": "Cuernavaca",
"state": "Morelos",
"country": "Mexico",
"zip_code": "62448",
"address": "Boulevard Díaz Ordaz No. 9 Cantarranas"
        },
"image": "image-url.com" ,
"reputation": 8990 ,
"reputationBadge": "green" ,
"price": 1000 ,
"availability": 10
    }
```
</details>
GET  getItemById
<details>
  <summary>http://localhost:8080/api/items/3</summary>
  
</details>
PUT  updateItem
<details>
  <summary>http://localhost:8080/api/items/1</summary>
  
```
body- raw json
{
  "hotelierId": 100,
  "name": "Latest Updated",
  "rating": 4,
  "category": "hotel",
  "location": {
    "city": "Delhi",
    "state": "Morelos",
    "country": "India",
    "zip_code": "62448",
    "address": "Boulevard Díaz Ordaz No. 9 Cantarranas"
  },
  "item_image": "image-url.com",
  "item_reputation": 8990,
  "item_reputation_badge": "green",
  "item_price": 1000,
  "item_availability": 10
}

```
</details>
GET  getAllItemsForHotelier
<details>
  <summary>http://localhost:8080/api/hoteliers/100/items</summary>
  

</details>
POST bookItem
<details>
  <summary>http://localhost:8080/api/items/6/bookItem</summary>
  

</details>
DELETE deleteItem
<details>
  <summary>http://localhost:8080/api/items/1</summary>
  

</details>


## API Documentation

API documentation is generated using Swagger OpenAPI. You can access the documentation by navigating to http://localhost:8080/api/swagger-ui/index.html in your browser.

## Testing

## Build and deploy through Dockerfile 

1. Navigate to terminal or command prompt
2. Pull two Docker images one for postgresql and another for app image using Docker pull command (commands mentioned below in collapsible section)
3. In web browser, enter the following address to get a sample JSON response http://localhost:8080/api/


<details>
  <summary>Docker Commands</summary>

Below the handy commands to bring this application up in Linux distribution environment using Docker

```
// Docker login
docker login
```

```
// Pull application image from DockerHub
docker pull nishikapanchal28/accommodation:v1
```

```
// List all the images 
docker images
```

```
// List all the containers 
docker ps -a 
```

```
// Run container 
docker-compose up 
```

</details>

## Container images

Application image - [Click here to see this image in DockerHub](https://hub.docker.com/repository/docker/nishikapanchal28/accommodation/tags?page=1&ordering=last_updated)

## Troubleshooting

If you encounter any issues during setup or execution and not able to setup after referring to this README file, please drop an email to nishikapanchal@gmail.com for solutions to problems.



