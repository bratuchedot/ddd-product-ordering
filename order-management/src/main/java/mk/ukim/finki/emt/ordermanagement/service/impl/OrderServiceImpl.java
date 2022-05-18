package mk.ukim.finki.emt.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.models.Order;
import mk.ukim.finki.emt.ordermanagement.domain.models.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.models.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.repository.OrderRepository;
import mk.ukim.finki.emt.ordermanagement.service.OrderService;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm, "order must not be null");
        var constraintViolations = validator.validate(orderForm);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The order form is not valid", constraintViolations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return this.orderRepository.findById(orderId);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistsException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        order.addItem(orderItemForm.getProduct(), orderItemForm.getQuantity());
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistsException, OrderItemIdNotExistsException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        order.removeItem(orderItemId);
        this.orderRepository.saveAndFlush(order);
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(), orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getProduct(), item.getQuantity()));
        return order;
    }
}
