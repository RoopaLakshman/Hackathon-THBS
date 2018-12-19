THHS - 
This is a JAVA spring boot application which was developed for a Hackathon competition held in my company. We were team of 3 members and the project was implemented in around 40 hours of timeframe.

Getting Started - 
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Technologies Used - 
JAVA/Springboot
HTML5, CSS3, JQuery
OpenCSV

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
	
The initial input data csv file is mandatory. The data from input csv file is written into separate csv files for further operations.
We have used opencsv for csv operations.

Limitations -
Not all functionalitie are provided in user interface (Such as Search products, filter products, add products)

Prerequisites - 
Java 1.8, Maven
Port 7000 should not be in use

How to Build? -
1. Download the project and unzip.
2. Open command prompt -> Navigate to project directory thhs
3. Run "mvnw package" command
4. If the build executes successfully then it should create a jar by name thhs<version>.jar in target folder
5. Navigate to target folder and double check if the jar exists

To start application - 
This is springboot application and has a embedded server (Tomcat) in it
The csv file should be passed as a parameter for the project (You can use the ProHactive-DataModel.csv)

1. Run below command
	java -jar thhs<version>.jar DataFile.csv

Test application - 
Use any Browser and hit localhost:7000/thhs/api
Login using credentails from csv file.


Team members and Acknowledgements - 
Vasant Sakre,
Nagesh MH,
Torry Harris UX/UI team
