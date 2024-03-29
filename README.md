# CheckGenerator

CheckGenerator present a program that can shape the cash-receipt, manipulate data through REST api and store it in database.


## Used technologies
* Java 17-----------|
* Gradle 7.5--------|—— `requirements`
* PostgreSQL 15---|
* Spring Boot
* Spring Data JPA
* Mockito
* JUnit
* AssertJ
* itext7 (for pdf)

## How to use:
1. clone this repository ```git clone https://github.com/KiryaHandsome/CheckGenerator.git```
2. run PostgreSQL process
3. configure `src/main/resources/application.properties` file accordingly your db information
4. go to root directory of project 
5. run ```./gradlew bootRun```
6. connect to port 8080 and use api

You can pass arguments in command line in the next format:
```prodId-qty ... card-discCardId```

Example:
```./gradlew bootRun --args="12-3 5-9"``` or with discount ```./gradlew bootRun --args="12-3 card-123"```

Last created check is always saved to file check.txt in the root directory.

## RESTful api
Implemented rest api with next methods:
| Address | HTTP method |Description | Parameter |
| --- | --- | --- | --- |
| `/products` | `GET` | return all products | `none` |
| `/product/{id}` | `GET` | return product by `id` | `id` |
| `/products/{id}` | `DELETE` | delete product by `id` | `id` |
| `/check?args` | `GET` | shape check according arguments | arguments in format id=qty and card=id(optional) |
| `/check/pdf?args` | `GET` | return check in pdf | arguments in format id=qty and card=id(optional) |
| `/product/create` | `POST` | add product | request body with fields: `name`, `price`, `isPromotional` |
| `/discount-card/{id}` | `GET` | return discount card by id | `id` |
| `/discount-cards` | `GET` | return all discount cards | `none` |
| `/discount-card/{id}` | `DELETE` | delete discount card by id | `id` |
| `/discount-card/create` | `POST` | add discount card | request body with field `discount` |

Also, you may get product information in xml format. To do it just add `/xml`
to begin of url path like this `localhost:8080/xml/products`

## Cache
Two cache algorithms were implemented: `LRU` (The Least Recently Used) and `LFU` (The Least Frequency Used).
`@Cacheable`, `@CachePut` and `@CacheEvict` are used to manipulate elements in cache. 

## Output examples
![TextFileOutput](images/pdfCheckExample.jpg)
![PdfFileOutput](images/txtCheckExample.jpg)
