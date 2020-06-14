package com.curtisvanzandt.vaadinshoeinventory;

import com.curtisvanzandt.vaadinshoeinventory.model.Shoe;
import com.curtisvanzandt.vaadinshoeinventory.repository.ShoeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VaadinShoeInventoryApplication {

    private static final Logger log = LoggerFactory.getLogger(VaadinShoeInventoryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VaadinShoeInventoryApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ShoeRepository repository) {
        return (args) -> {
            repository.save(new Shoe("Brooks", 15));
            repository.save(new Shoe("Chacos", 14));
            repository.save(new Shoe("Evolv", 15));

            log.info("Shoes found with findAll():");
            log.info("----------------------------");

            for (var shoe : repository.findAll())
                log.info(shoe.toString());

            var shoe = repository.findById(1L).get();
            log.info("Shoe found with findOne(1L):");
            log.info("----------------------------------");
            log.info(shoe.toString());
            log.info("");

            log.info("Shoe found with findByBrandStartsWithIgnoreCase('Brooks'):");
            log.info("-------------------------------");
            for (var brooks : repository.findByBrandStartsWithIgnoreCase("Brooks"))
                log.info(brooks.toString());
            log.info("");
        };
    }
}
