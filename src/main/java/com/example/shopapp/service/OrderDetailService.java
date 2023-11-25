package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.OrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) {
        orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(NoSuchElementException::new);
        OrderDetail newOrderDetail = new OrderDetail();
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        newOrderDetail.setOrder(order);
        newOrderDetail.setProduct(product);
        newOrderDetail.setColor(orderDetailDTO.getColor());
        newOrderDetail.setPrice(orderDetailDTO.getPrice());
        newOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        newOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        OrderDetail orderDetail=orderDetailRepository.save(newOrderDetail);

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setId(orderDetail.getId());
        orderDetailResponse.setOrderId(order.getId());
        orderDetailResponse.setProductId(product.getId());
        orderDetailResponse.setColor(orderDetail.getColor());
        orderDetailResponse.setPrice(orderDetail.getPrice());
        orderDetailResponse.setTotalMoney(orderDetail.getTotalMoney());
        orderDetailResponse.setNumberOfProducts(orderDetail.getNumberOfProducts());
        return orderDetailResponse;
    }

    @Override
    public OrderDetailResponse getOrderDetail(long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setId(orderDetail.getId());
        orderDetailResponse.setOrderId(orderDetail.getOrder().getId());
        orderDetailResponse.setProductId(orderDetail.getProduct().getId());
        orderDetailResponse.setColor(orderDetail.getColor());
        orderDetailResponse.setPrice(orderDetail.getPrice());
        orderDetailResponse.setTotalMoney(orderDetail.getTotalMoney());
        orderDetailResponse.setNumberOfProducts(orderDetail.getNumberOfProducts());
        return orderDetailResponse;
    }

    @Override
    public OrderDetailResponse updateOrderDetail(long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail newOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        newOrderDetail.setOrder(order);
        newOrderDetail.setProduct(product);
        newOrderDetail.setColor(orderDetailDTO.getColor());
        newOrderDetail.setPrice(orderDetailDTO.getPrice());
        newOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        newOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());

        OrderDetail orderDetail = orderDetailRepository.save(newOrderDetail);

        OrderDetailResponse orderDetailResponse = OrderDetailResponse.transformOrderDetail(orderDetail);
        return orderDetailResponse;
    }

    @Override
    public void deleteOrderDetail(long id) {
        OrderDetail newOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new NoSuchElementException("Order not found"));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        return orderDetails;
    }
}
