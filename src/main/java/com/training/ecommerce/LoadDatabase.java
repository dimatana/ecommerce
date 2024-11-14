package com.training.ecommerce;

import com.training.ecommerce.Repository.ProductRepository;
import com.training.ecommerce.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
@Bean
    CommandLineRunner initDatabase(UserRepository userRepository){
    return args -> {
        User user = new User();
        user.setName("dima");
        user.setEmail("dddd@gmail.com");
        user.setPassword("1234");
        user.setAddress("stefan cel mare");
        userRepository.save(user);
    };
}
}
