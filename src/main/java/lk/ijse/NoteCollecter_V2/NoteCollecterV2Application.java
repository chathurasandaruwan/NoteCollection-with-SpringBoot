package lk.ijse.NoteCollecter_V2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NoteCollecterV2Application {

	public static void main(String[] args) {
		SpringApplication.run(NoteCollecterV2Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper() { return new ModelMapper(); }
}
