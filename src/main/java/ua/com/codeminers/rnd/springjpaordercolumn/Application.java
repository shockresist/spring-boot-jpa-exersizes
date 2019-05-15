package ua.com.codeminers.rnd.springjpaordercolumn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner runStatementTransactionService(TestParentService service) {
        // return args -> service.doAction();
        return args -> {
            service.clear();
            service.addEntities();
            service.removeChild();
            //service.addChild();
        };
    }

}
