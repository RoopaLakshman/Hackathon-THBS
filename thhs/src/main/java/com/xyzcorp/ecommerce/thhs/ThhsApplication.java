package com.xyzcorp.ecommerce.thhs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xyzcorp.ecommerce.thhs.helper.CSVFileHelper;

@SpringBootApplication
public class ThhsApplication {
/** Adding a comment*/
	public static void main(String[] args) {

		SpringApplication.run(ThhsApplication.class, args);

		CSVFileHelper csvFileHelper = new CSVFileHelper();
		csvFileHelper.separateDataFile(args[0]);

	}
}

