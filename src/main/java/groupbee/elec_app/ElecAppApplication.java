package groupbee.elec_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableFeignClients(basePackages = "groupbee.elec_app.approve.feign")
@EnableMongoRepositories(basePackages = "groupbee.elec_app.approve.repository")
@SpringBootApplication(scanBasePackages = {"groupbee.elec_app"})
public class ElecAppApplication {

    public static void main(String[] args)
    {

        SpringApplication.run(ElecAppApplication.class, args);


    }

}
