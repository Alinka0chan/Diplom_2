package api.util;

import api.model.Orders;

import java.util.List;

public class OrdersGenerator {
    public static Orders getOrders(){
        return new Orders(List.of("61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa75"));
    }
    public static Orders getIncorrectOrders(){
        return new Orders(List.of("61c0c5a71d1"));
    }
}
