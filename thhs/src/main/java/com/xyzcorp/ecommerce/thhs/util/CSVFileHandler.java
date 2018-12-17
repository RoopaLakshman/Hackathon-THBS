package com.xyzcorp.ecommerce.thhs.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.xyzcorp.ecommerce.thhs.model.User;
import com.xyzcorp.ecommerce.thhs.vo.CartDetailVO;
import com.xyzcorp.ecommerce.thhs.vo.OrderDetailsVO;
import com.xyzcorp.ecommerce.thhs.vo.ProductDetailVO;
import com.xyzcorp.ecommerce.thhs.vo.TokenVO;

@Component
public class CSVFileHandler {
	private static final Path path = Paths.get("temp");

	public boolean separateDataFile(String fileName) {

		FileReader fileReader = null;
		boolean anyFailed = false;

		try {
			fileReader = new FileReader(fileName);
			CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

			List<String[]> dataFileContent = csvReader.readAll();
			int lineCount = 0;
			int usersCount = 0;
			boolean writingFirstElementInUser = true;
			boolean writingFirstElementInCart = true;
			boolean writingFirstElementInProduct = true;

			for (String[] line : dataFileContent) {
				lineCount++;
				//System.out.println();

				TokenVO tokenVO = new TokenVO();
				if (writeToken(tokenVO, false)) {

				} else {
					anyFailed = true;
				}

				if (!line[0].trim().isEmpty()) {
					//System.out.println("Line - " + lineCount + " has user details");
					usersCount++;

					User user = new User();
					user.setUserId(String.valueOf(usersCount));
					user.setUserName(line[0]);
					user.setPassword(line[1]);
					user.setName(line[2]);
					user.setAddress(line[3]);
					user.setPhoneNumber(line[4]);
					user.setPreference(line[5]);

					user.toString();

					if (writeUser(user, !writingFirstElementInUser)) {
						//System.out.println("User written to userDetails.csv");

					} else {
						anyFailed = true;
						//System.out.println("Failed to write user to userDetails.csv");

					}
					writingFirstElementInUser = false;

				}

				// System.out.println("line[6]: " + line[6]);
				if (!line[6].trim().isEmpty()) {
					//System.out.println("Line - " + lineCount + " has cart details");

					CartDetailVO cart = new CartDetailVO();
					cart.setCartId(line[6]);
					cart.setUserName(line[7]);
					cart.setProductId(line[8]);
					cart.setQuantity(line[9]);
					cart.toString();

					if (writeCart(cart, !writingFirstElementInCart)) {
						//System.out.println("Cart details written to cartDetails.csv");
					} else {
						anyFailed = true;
						//System.out.println("Failed to write cart details to cartDetails.csv");

					}
					writingFirstElementInCart = false;

				}

				// System.out.println("line[10]: " + line[10]);
				if (!line[10].trim().isEmpty()) {
					//System.out.println("Line - " + lineCount + " has product details");
					ProductDetailVO product = new ProductDetailVO();

					product.setProductId(line[10]);
					product.setName(line[11]);
					product.setDescription(line[12]);
					product.setQtyAvailable(line[13]);
					product.setCurrency(line[14]);
					product.setCost(line[15]);
					product.setCategory(line[16]);

					product.toString();

					if (writeProduct(product, !writingFirstElementInProduct)) {
						//System.out.println("Product details written to productDetails.csv");
					} else {
						anyFailed = true;
						//System.out.println("Failed to write Product details to productDetails.csv");

					}
					writingFirstElementInProduct = false;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return !anyFailed;
	}

	public boolean writeUser(User user, boolean append) {
		Writer writer = null;

		if (!Files.isDirectory(path)) {
			System.out.println(path.getFileName() + " directory not found");
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			writer = new FileWriter("./temp/userDetails.csv", append);

			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

			beanToCsv.write(user);

		} catch (IOException e) {
			e.printStackTrace();
			return false;

		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
			return false;

		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
			return false;

		} finally {
			if (writer != null) {
				try {
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;

	}

	public boolean writeCart(CartDetailVO cart, boolean append) {
		Writer writer = null;

		if (!Files.isDirectory(path)) {
			System.out.println(path.getFileName() + " directory not found");
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			writer = new FileWriter("./temp/cartDetails.csv", append);

			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

			beanToCsv.write(cart);

		} catch (IOException e) {
			e.printStackTrace();
			return false;

		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
			return false;

		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
			return false;

		} finally {
			if (writer != null) {
				try {
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;

	}

	public boolean writeProduct(ProductDetailVO product, boolean append) {
		Writer writer = null;

		if (!Files.isDirectory(path)) {
			System.out.println(path.getFileName() + " directory not found");
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			writer = new FileWriter("./temp/productDetails.csv", append);

			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

			beanToCsv.write(product);

		} catch (IOException e) {
			e.printStackTrace();
			return false;

		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
			return false;

		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
			return false;

		} finally {
			if (writer != null) {
				try {
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public boolean writeToken(TokenVO token, boolean append) {
		Writer writer = null;

		if (!Files.isDirectory(path)) {
			System.out.println(path.getFileName() + " directory not found");
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			writer = new FileWriter("./temp/tokens.csv", append);

			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

			beanToCsv.write(token);

		} catch (IOException e) {
			e.printStackTrace();
			return false;

		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
			return false;

		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
			return false;

		} finally {
			if (writer != null) {
				try {
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public boolean writeTokens(List<TokenVO> tokens, boolean append) {
		int writeSuccessCount = 0;
		boolean writingFirstElement = true;
		boolean anyFailed = false;
		for (TokenVO token : tokens) {
			if (writeToken(token, !writingFirstElement)) {
				System.out.println("User details written to userDetails.csv: " + token.toString());
				writeSuccessCount++;

			} else {
				anyFailed = true;
				System.out.println("Failed to write User details to userDetails.csv: " + token.toString());

			}
			writingFirstElement = false;
		}

		System.out.println("Total no of records failed: " + (tokens.size() - writeSuccessCount));

		return !anyFailed;
	}

	public boolean writeOrderDetails(OrderDetailsVO orderdetailsVO, boolean append) {
		Writer writer = null;

		if (Files.notExists(Paths.get("./orderDetails.csv"))) {
			append = false;
		}

		try {
			writer = new FileWriter("./orderDetails.csv", append);

			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

			beanToCsv.write(orderdetailsVO);

		} catch (IOException e) {
			e.printStackTrace();
			return false;

		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
			return false;

		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
			return false;

		} finally {
			if (writer != null) {
				try {
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public boolean writeUsers(List<User> users) {

		System.out.println("Total no of users to be written: " + users.size());
		int writeSuccessCount = 0;
		boolean writingFirstElement = true;
		boolean anyFailed = false;

		for (User user : users) {
			if (writeUser(user, !writingFirstElement)) {
				System.out.println("User details written to userDetails.csv: " + user.toString());
				writeSuccessCount++;

			} else {
				anyFailed = true;
				System.out.println("Failed to write User details to userDetails.csv: " + user.toString());

			}
			writingFirstElement = false;
		}

		System.out.println("Total no of records failed: " + (users.size() - writeSuccessCount));

		return !anyFailed;
	}

	public boolean writeCarts(List<CartDetailVO> carts) {

		System.out.println("Total no of carts to be written: " + carts.size());
		int writeSuccessCount = 0;
		boolean writingFirstElement = true;
		boolean anyFailed = false;

		for (CartDetailVO cart : carts) {
			if (writeCart(cart, !writingFirstElement)) {
				System.out.println("Cart details written to cartDetails.csv: " + cart.toString());
				writeSuccessCount++;

			} else {
				anyFailed = true;
				System.out.println("Failed to write Cart details to cartDetails.csv: " + cart.toString());

			}
			writingFirstElement = false;
		}

		System.out.println("Total no of records failed: " + (carts.size() - writeSuccessCount));

		return !anyFailed;
	}

	public boolean writeProducts(List<ProductDetailVO> products) {

		System.out.println("Total no of products to be written: " + products.size());
		int writeSuccessCount = 0;
		boolean writingFirstElement = true;
		boolean anyFailed = false;

		for (ProductDetailVO product : products) {
			if (writeProduct(product, !writingFirstElement)) {
				System.out.println("Product details written to productDetails.csv: " + product.toString());
				writeSuccessCount++;

			} else {
				anyFailed = true;
				System.out.println("Failed to write Product details to productDetails.csv: " + product.toString());

			}
			writingFirstElement = false;
		}

		System.out.println("Total no of records failed: " + (products.size() - writeSuccessCount));

		return !anyFailed;
	}

	public boolean writeOrders(List<OrderDetailsVO> orders) {

		System.out.println("Total no of Orders to be written: " + orders.size());
		int writeSuccessCount = 0;
		boolean writingFirstElement = true;
		boolean anyFailed = false;

		for (OrderDetailsVO order : orders) {
			if (writeOrderDetails(order, !writingFirstElement)) {
				System.out.println("Order details written to orderDetails.csv: " + order.toString());
				writeSuccessCount++;

			} else {
				anyFailed = true;
				System.out.println("Failed to write Order details to orderDetails.csv: " + order.toString());

			}
			writingFirstElement = false;
		}

		System.out.println("Total no of records failed: " + (orders.size() - writeSuccessCount));

		return !anyFailed;
	}

	public List<User> readUsers() {
		List<User> beans = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("./temp/userDetails.csv");
			beans = new CsvToBeanBuilder(fileReader).withType(User.class).build().parse();

		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (beans == null || beans.size() == 0) {
			System.out.println("No records found in userDetails.csv");
		}

		return beans;
	}

	public List<CartDetailVO> readCarts() {
		List<CartDetailVO> beans = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader("./temp/cartDetails.csv");
			beans = new CsvToBeanBuilder(fileReader).withType(CartDetailVO.class).build().parse();

		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (beans == null || beans.size() == 0) {
			System.out.println("No records found in cartDetails.csv");
		}

		List<CartDetailVO> responseObj = new ArrayList<CartDetailVO>();

		return beans;
	}

	public List<ProductDetailVO> readProducts() {
		List<ProductDetailVO> beans = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader("./temp/productDetails.csv");
			beans = new CsvToBeanBuilder(fileReader).withType(ProductDetailVO.class).build().parse();

		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (beans == null || beans.size() == 0) {
			System.out.println("No records found in productDetails.csv");
		}

		return beans;
	}

	public List<TokenVO> readTokens() {
		List<TokenVO> beans = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader("./temp/tokens.csv");
			beans = new CsvToBeanBuilder(fileReader).withType(TokenVO.class).build().parse();

		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (beans == null || beans.size() == 0) {
			System.out.println("No records found in token.csv");
		}

		return beans;
	}

	public List<String[]> readOrderDetails() {
		List<String[]> beans = null;
		FileReader fileReader = null;
		CSVReader reader = null;

		if (Files.notExists(Paths.get("./orderDetails.csv"))) {
			try {
				Files.createFile(Paths.get("./orderDetails.csv"));

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed to create ./orderDetails.csv");
			}
		}

		try {
			fileReader = new FileReader("./orderDetails.csv");

			reader = new CSVReader(fileReader);

			beans = reader.readAll();

		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
				
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (beans == null || beans.size() == 0) {
			System.out.println("No records found in orderDetails.csv");
		}

		return beans;
	}
}
