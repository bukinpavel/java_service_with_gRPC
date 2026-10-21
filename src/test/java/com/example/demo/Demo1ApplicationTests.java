package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.grpc.server.port=0")
class Demo1ApplicationTests {
	
	@Test
	void contextLoads() {
	}
	
}
