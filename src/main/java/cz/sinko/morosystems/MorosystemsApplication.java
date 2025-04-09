package cz.sinko.morosystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Morosystems application.
 *
 * @author Radovan Šinko
 */
@SuppressWarnings("PMD")
@SpringBootApplication
public class MorosystemsApplication {

    /**
     * Main method to run the Morosystems application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(MorosystemsApplication.class, args);
    }
}
