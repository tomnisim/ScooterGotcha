package gotcha.server;

import gotcha.server.Service.MainSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ServerApplication {
	private static String server_config_path = "C:\\Users\\Amit\\Desktop\\ScooterGotcha\\server\\src\\main\\java\\gotcha\\server\\Config\\server_config.txt";


	public static void main(String[] args) {
		try
		{
			MainSystem mainSystem = new MainSystem(server_config_path);
			System.out.println("Finished init System");
		}
		catch (Exception e){
			System.out.println("Cant init System");
		}

		SpringApplication.run(ServerApplication.class, args);
	}

}
