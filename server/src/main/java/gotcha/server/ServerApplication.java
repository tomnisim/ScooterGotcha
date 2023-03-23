package gotcha.server;

import gotcha.server.Service.MainSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ServerApplication implements CommandLineRunner {
	private final MainSystem mainSystem;

	@Autowired
	public ServerApplication(MainSystem mainSystem) {
		this.mainSystem = mainSystem;
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainSystem.init_server();
	}
}
