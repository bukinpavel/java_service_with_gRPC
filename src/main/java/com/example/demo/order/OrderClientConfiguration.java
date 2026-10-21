package com.example.demo.order;

import com.example.demo.order.grpc.OrderServiceGrpc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "app.role", havingValue = "client", matchIfMissing = true)
public class OrderClientConfiguration {

	@Bean
	OrderServiceGrpc.OrderServiceBlockingStub orderServiceStub(GrpcChannelFactory channelFactory) {
		return OrderServiceGrpc.newBlockingStub(channelFactory.createChannel("orderService"));
	}
}