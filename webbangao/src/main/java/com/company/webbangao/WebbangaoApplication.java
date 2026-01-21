package com.company.webbangao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class WebbangaoApplication {

	public static void main(String[] args) {
		// Chạy ứng dụng và lấy Context
		ConfigurableApplicationContext context = SpringApplication.run(WebbangaoApplication.class, args);

		// Lấy thông tin môi trường (Environment) để đọc Port
		Environment env = context.getBean(Environment.class);
		String port = env.getProperty("server.port");

		// Nếu không cấu hình server.port trong properties, mặc định là 8080
		if (port == null) {
			port = "8080";
		}

		// In đường dẫn ra Console
		System.out.println("\n----------------------------------------------------------");
		System.out.println("  Ứng dụng Gạo Ngon Việt đã sẵn sàng!");
		System.out.println("  Truy cập tại: http://localhost:" + port + "/");
		System.out.println("----------------------------------------------------------\n");


	}
}