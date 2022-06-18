## Microservices "ms-customer", "ms-account" and "ms-mailSender" written in Java

ms-customer methods:
- getting all customers from the database.
- getting a customer by id from the database.
- adding a new customer to the database.
- updating customer data by id in the database.
- deleting a customer by id.

ms-account methods:
- getting all existing accounts or all accounts of one customer by customer_id from the database.
- getting customer account by id from the database.
- getting customer account by account_no from the database.
- creating a new customer account in the database.
- updating customer account data by id in the database.
- updating customer account data by account_no in the database.
- deleting a customer account by id.
- deleting a customer account by account_no.

With the method of deleting a customer, if he had accounts, then they are also deleted. With the deletion methods, the data about the customer or the customer account 
remains in the database with the status "deleted" and will not be taken into account when using any methods in the future. With the methods of adding, updating and 
deleting data, letters are sent to the "Mail_Sender_Q" queue.

ms-mailSender methods:
- listening to messages from the "Mail_Sender_Q" queue and sending them to the customer's e-mail.

To run RabbitMQ, a container is created in Docker.  

### Technologies used in the project:
- *Spring Boot, Spring Data JPA, Spring WEB MVC, Spring AMQP, Spring Validation*
- *PostgreSQL*
- *RabbitMQ*
- *Swagger*
- *Liquibase*

Microservices can be tested using the Postman application.
