package ra.controllers;

import ra.controllers.fileservice.IoFile;
import ra.models.Order;

import ra.repository.IShop;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IShop<Order> {
    IoFile ioFile;
    private List<Order> listOdrder = new ArrayList<>();
    public OrderService(IoFile ioFile) {
        this.ioFile = ioFile;
    }

    @Override
    public void save(Order order) {
        listOdrder = ioFile.getAll();
        listOdrder.add(order);
    }

    @Override
    public List<Order> displayAll() {
        return null;
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public void update(List<Order> t) {

    }

    @Override
    public int findByIndex(int id) {
        return 0;
    }

    public int getNewId() {
        int max = 0;
        for (Order o : listOdrder) {
            if (o.getId() > max) {
                max = o.getId();
            }
        }
        return max + 1;
    }
}
