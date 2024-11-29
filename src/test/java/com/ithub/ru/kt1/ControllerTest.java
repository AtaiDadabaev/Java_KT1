package com.ithub.ru.kt1;

import com.ithub.ru.kt1.controller.OrderController;
import com.ithub.ru.kt1.model.Order;
import com.ithub.ru.kt1.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService.testSaveProduct();
    }

    @Test
    public void shouldReturnAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    public void shouldReturnOrderById(long id) throws Exception {
        Order order = new Order();
        order.setId(id);
        order.setProduct("Test Order");

        when(orderService.getById(id)).thenReturn(order);
        mockMvc.perform(get("/api/order/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.product").value("Test Order"));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2})
    public void shouldDeleteOrderById(long id) throws Exception {
        doNothing().when(orderService).deleteById(id);

        mockMvc.perform(delete("/api/order/" + id))
                .andExpect(status().isNoContent());
    }
}