package groupbee.elec_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackages = "groupbee.elec_app.approve.data")
@SpringBootApplication(scanBasePackages = {"groupbee.elec_app"})
public class ElecAppApplication {

    public static void main(String[] args)
    {

        SpringApplication.run(ElecAppApplication.class, args);


    }

}
