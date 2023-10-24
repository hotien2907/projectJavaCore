package ra.controllers;

import ra.controllers.fileservice.IoFile;
import ra.models.Product;
import ra.repository.IShop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MenuService implements IShop<Product> {

    private IoFile ioFile;

    private List<Product> allMenu = new ArrayList<>();


    public MenuService(IoFile ioFile) {
        this.ioFile = ioFile;
    }

    @Override
    public void save(Product product) {
        allMenu = ioFile.getAll();
        allMenu.add(product);
        ioFile.saveToFile(allMenu);
    }

    @Override
    public List<Product> getAll() {

        List<Product> allProduct = ioFile.getAll();
        return allProduct;
    }

    @Override
    public Product findById(int id) {
        List<Product> allProduct = ioFile.getAll();
        for (Product pr : allProduct) {
            if (pr.getProductId() == id) {
                return pr;
            }
        }
        return null;
    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public void update(List<Product> t) {
        allMenu = ioFile.getAll();
        ioFile.saveToFile(t);
    }

    public void updateQuantity(Product product) {
        List<Product> allMenu = ioFile.getAll();
        // Tìm sản phẩm trong danh sách
        for (Product existingProduct : allMenu) {
            if (existingProduct.getProductId() == product.getProductId()) {
                // Cập nhật số lượng
                existingProduct.setStock(product.getStock());
                break;
            }
        }
        ioFile.saveToFile(allMenu);
    }



    @Override
    public int findByIndex(int id) {
        allMenu = ioFile.getAll();
        for (int i = 0; i < allMenu.size(); i++) {
            if (allMenu.get(i).equals(id)) {
                return i;
            }
        }

        return -1;
    }

    public List<Product> getSortedPriceProducts() {
        List<Product> sortedProducts = ioFile.getAll();

        sortedProducts.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        return sortedProducts;
    }

    @Override
    public int autoInc() {
        int max = 0;
        for (Product product : getAll()) {
            if (max < product.getProductId()) {
                max = product.getProductId();
            }
        }
        return max + 1;
    }
}