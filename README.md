# Project 1

## Description
Set up the backend for an online store to buy items. Users can register and login to add items to their cart. Then they can place orders. Registered users are are stored in the Users table. Items that 
users can buy are stored in the Items table. User's carts are stored in the Cart table. Users order's are stored in the Orders table.

## Features Implemented
* Register - register new users
* Login/Logout - login and logout users
* Get Users - get all users and their cart
* Get Items - gets all items that are available in store
* Add Item to Cart - adds selected item to cart

## Technologies Used
* Spring Framework: Spring Boot, Spring Data
* Loki
* Grafana
* Fluentd
* DBeaver
* Kubernetes
* AWS

## How to set up / get started using it
* Create or change namespace christopher-gonzalez
* Create a configmap using the file in the config folder inside of the manifest folder
* Apply yaml files in manifest in a kubernetes cluster with an ingress object
* Create a Deployment for Loki and Grafana in the default namespace
* Create a Service for Loki and Grafana in the deafault namespace
* Create an Ingress-nginx Controller object in the default namespace and use its external IP and HTTP Verbs to make the requests in Postman
* Set up a database tables in PostgreSQL using the following statements
* CREATE TABLE projectzero.users (
	id serial PRIMARY KEY,
	email varchar (250) NOT NULL UNIQUE CHECK (length(email) > 0),
	password varchar (250) NOT NULL CHECK (length(password) > 0)
);
* CREATE TABLE projectzero.items(
	id serial PRIMARY KEY,
	name varchar (250) NOT NULL,
	price NUMERIC (40, 2) NOT NULL DEFAULT 0
);
* CREATE TABLE projectzero.carts(
	id serial PRIMARY KEY,
	item_id integer NOT NULL REFERENCES projectzero.items (id),
	user_id integer NOT NULL REFERENCES projectzero.users (id)
);
* CREATE TABLE projectzero.orders(
	id serial PRIMARY KEY,
	item_id integer NOT NULL REFERENCES projectzero.items (id),
	user_id integer NOT NULL REFERENCES projectzero.users (id)
);
* Create a secret for the DB_URL, DB_USERNAME, and DB_PASSWORD
* Methods that require JSON in the Body of the request: Regsiter, Login
* Register: POST http://a62d60162057149549617360016d2e38-542496291.us-east-1.elb.amazonaws.com/online-store/p1/register
{
    "email": "user email",
    "password": "password"
}
* Login: POST http://a62d60162057149549617360016d2e38-542496291.us-east-1.elb.amazonaws.com/online-store-rp3/p1/register
{
    "email": "user email",
    "password": "password"
}
* Logout: POST http://a62d60162057149549617360016d2e38-542496291.us-east-1.elb.amazonaws.com/online-store/p1/logout
* Get Users: GET http://a62d60162057149549617360016d2e38-542496291.us-east-1.elb.amazonaws.com/online-store/p1/users
* Get Items: GET http://a62d60162057149549617360016d2e38-542496291.us-east-1.elb.amazonaws.com/online-store/p1/items
* Add Item to Cart: POST http://a62d60162057149549617360016d2e38-542496291.us-east-1.elb.amazonaws.com/online-store/p1/items
