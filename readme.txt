RESTFul webservice is created using Spring Boot, Cassandra 2 and Tomcat

Application uses Spring Data Cassandra to access the Cassandra 2 Data store and table scripts are included with resources in the project

To start the application simply use the command - mvn spring-boot:run 

GET Request - localhost:8080/myretail/products/{product-id}

and Response is 
{
  "id": 123456
  "name" : "product name"
  "currentPrice" : 
	{
	  "value" : 100.00
	  "currencyCode" : "USD"
	}
}

POST Request - localhost:8080/myretail/products/123456

Request Body
{
  "id": 123456,
  "name" : "product name",
  "currentPrice" : 
	{
	  "value" : 100.00,
	  "currencyCode" : "USD"
	}
}
