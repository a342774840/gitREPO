package lumber;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ApplicationLumber{
	public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationLumber.class, args);
    }
	
}
