THHS
This is a JAVA spring boot application which was developed for a Hackathon competition held in my company. We were team of 3 in this project.

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Overview - 
This is a simple eCommerce application which has below functionalities -
	1. View all products available
	2. Add products
	3. User login
	4. Add to Cart (Add new product to Cart)
	5. Modify cart (Increase/Decrease items in cart)
	6. Delete from Cart (Delete a product from Cart)
	7. View products in cart (Lists all products in cart)
	8. Submit your cart (Writes all cart details into a csv file)
	
The initial input data csv file is mandatory. All the details from input csv file is written into separate csv files for further operations.
We have used opencsv for csv operations.

Limitations -
A user can login and view products, add and view items in cart, modify & delete cart items and submit his cart details through Browser.
However, to add new products you should call the /product/add API.

Prerequisites
Java 1.8, Maven
Port 7000 should not be in use

How to Build?
1. Download the project and unzip.
2. Open command prompt -> Navigate to project directory thhs
3. Run "mvn package" command
4. If the build executes successfully then it should create a jar by name thhs<version>.jar in target folder
5. Navigate to target folder and double check if the jar exists

To start application
This is springboot application and has a embedded server (Tomcat) in it
The csv file should be passed as a parameter for the project

1. Run below command
	java -jar thhs<version>.jar ProHactive-DataModel.csv

Test application
Open Browser and type localhost:7000/thhs/api
This should open up the application

Authors
Roopa Lakshmanaiah

Acknowledgments
Vasant Skare
Nagesh MH
