package com.example.demo.order;

import com.example.demo.order.grpc.GetOrderRequest;
import com.example.demo.order.grpc.OrderResponse;
import com.example.demo.order.grpc.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.role", havingValue = "order-service")
public class OrderGrpcService extends OrderServiceGrpc.OrderServiceImplBase {

	@Override
	public void getOrder(GetOrderRequest request, StreamObserver<OrderResponse> responseObserver) {
		OrderResponse response = OrderResponse.newBuilder()
				.setOrderId(request.getOrderId())
				.setStatus("CONFIRMED")
				.setMessage("Ответ получен от orderService")
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}