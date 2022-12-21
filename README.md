# CheckGenerator

[//]: # (## Requirements )

[//]: # (* Java 17)

[//]: # (* Gradle 7.5)

[//]: # (* PostgreSQL 15)

## Used technologies
* Java 17
* Gradle 7.5
* PostgreSQL 15
* Spring Boot
* Spring Data JPA2

## How to use:
1. clone this repository
```git clone https://github.com/KiryaHandsome/CheckGenerator.git```
2. go to directory with project 
```cd CheckGenerator```
3. run
```gradle bootRun```

You can pass arguments in command line in the next format:
```prodId-qty ... card-discCardId```
Example:
```gradle bootRun --args="12-3 5-9"``` or with discount ```gradle bootRun --args="12-3 card-123"```

Last created check always save to file check.txt.

## RESTful api
Implemented rest api with next methods:
| Address | HTTP method |Description | Parameter |
| --- | --- | --- | --- |
| `/products` | `GET` | return all products |  |
| `/product/{id}` | `GET` | return product by `id` |  |
| `/products/{id}` | `DELETE` | delete product by `id` |  |
| `/check?args` | `GET` |  |
| `/product/create` | `POST` | add product |  |
| `/discount-card/{id}` | `GET` | return discount card by id |  |
| `/discount-cards` | `GET` | return all discount cards |  |
| `/discount-card/{id}` | `DELETE` | delete discount card by id |  |
| `/discount-card/create` | `POST` | add discount card |  |