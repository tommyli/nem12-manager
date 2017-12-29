// Tommy Li (tommy.li@firefire.co), 2017-12-25

package co.firefire.fenergy.nem12

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class);
  }
}
