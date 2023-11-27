package com.vvt.inventoryservice;

import com.vvt.inventoryservice.model.Inventory;
import com.vvt.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
//            Inventory inventory1 = new Inventory();
//            inventory1.setSkuCode("iphone_15");
//            inventory1.setQuantity(10);
//            inventoryRepository.save(inventory1);
//
//            Inventory inventory2 = new Inventory();
//            inventory2.setSkuCode("iphone_14");
//            inventory2.setQuantity(0);
//            inventoryRepository.save(inventory2);
        };
    }
}
