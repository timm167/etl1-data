package com.example.etl1;
import com.example.etl1.service.Scraping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Etl1Application {

	public static void main(String[] args) {
		SpringApplication.run(Etl1Application.class, args);
	}

	@Bean
	public CommandLineRunner runScrape(Scraping scrape) {
		return args -> {
			scrape.runNeweggProcess();
		};
	}

}
