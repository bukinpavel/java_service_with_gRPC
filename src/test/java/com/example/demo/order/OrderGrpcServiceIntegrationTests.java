package com.example.demo.order;

import com.example.demo.order.grpc.GetOrderRequest;
import com.example.demo.order.grpc.OrderResponse;
import com.example.demo.order.grpc.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.grpc.test.autoconfigure.LocalGrpcServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
		"app.role=order-service",
		"spring.grpc.server.port=0",
		"spring.main.web-application-type=none"
})
class OrderGrpcServiceIntegrationTests {

	@LocalGrpcServerPort
	private int port;

	@Test
	void returnsOrderThroughGrpc() throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
				.usePlaintext()
				.build();

		try {
			OrderResponse response = OrderServiceGrpc.newBlockingStub(channel)
					.getOrder(GetOrderRequest.newBuilder().setOrderId("order-42").build());

			assertThat(response.getOrderId()).isEqualTo("order-42");
			assertThat(response.getStatus()).isEqualTo("CONFIRMED");
			assertThat(response.getMessage()).contains("orderService");
		}
		finally {
			channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
		}
	}
}