package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderStatus;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.RET;
import org.hibernate.exception.DataException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO){

        userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()->new NoSuchElementException("user not found"));

        //Dùng thư viện model mapper
        modelMapper.typeMap(OrderDTO.class,Order.class) //khi map từ orderDTO sang Order thì skip Id
                .addMappings(mapper->mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO,order);
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingDate(orderDTO.getShippingDate());
        order.setActive(true);
        Order newOrder = orderRepository.save(order);
        return modelMapper.map(newOrder,OrderResponse.class);
    }

    @Override
    public OrderResponse getOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Order.class,OrderRepository.class);
        OrderResponse orderResponse = new OrderResponse();
        modelMapper.map(order,orderResponse);
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrder(long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderResponse newOrder = new OrderResponse();

        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper->mapper.skip(Order::setId));
        modelMapper.map(orderDTO,order);
        orderRepository.save(order);

        modelMapper.typeMap(Order.class,OrderResponse.class);
        modelMapper.map(order,newOrder);
        return newOrder;
    }

    @Override
    public List<OrderResponse> findByUserId(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Order.class,OrderRepository.class);

        for(Order o : orders){
            OrderResponse orderResponse = new OrderResponse();
            modelMapper.map(o,orderResponse);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    @Override
    public void deleteOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setActive(false);
        orderRepository.save(order);
    }
}
