package com.example.demo.order;

import com.example.demo.order.grpc.GetOrderRequest;
import com.example.demo.order.grpc.OrderResponse;
import com.example.demo.order.grpc.OrderServiceGrpc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/orders")
@ConditionalOnProperty(name = "app.role", havingValue = "client", matchIfMissing = true)
public class OrderController {

	private final OrderServiceGrpc.OrderServiceBlockingStub orderService;

	public OrderController(OrderServiceGrpc.OrderServiceBlockingStub orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/{orderId}")
	public OrderView getOrder(@PathVariable String orderId) {
		OrderResponse response = orderService
				.withDeadlineAfter(3, TimeUnit.SECONDS)
				.getOrder(GetOrderRequest.newBuilder().setOrderId(orderId).build());

		return new OrderView(response.getOrderId(), response.getStatus(), response.getMessage());
	}

	public record OrderView(String orderId, String status, String message) {
	}
}