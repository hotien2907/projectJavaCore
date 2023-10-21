package ra.controllers;

import ra.controllers.fileservice.IoFile;
import ra.models.Cart;
import ra.models.User;
import ra.repository.IShop;

import java.util.ArrayList;
import java.util.List;

public class CartService implements IShop<Cart> {
    private IoFile ioFile;

    private UserService userService;
    private MenuService menuService;
    private List<Cart> carts = new ArrayList<>();

    public CartService(IoFile ioFile, UserService userService, MenuService menuService) {
        this.ioFile = ioFile;
        this.userService = userService;
        this.menuService = menuService;

    }

    public User userLogin() {
        User userLogin = userService.userAcitive();
        return userLogin;
    }

    @Override
    public void save(Cart cart) {

        List<Cart> carts = userLogin().getCart();
        if (findById(cart.getCartId()) == null) {
            // TODO : them moi
           cart = findByProductId(cart.getProduct().getProductId());
            if (cart != null) {
                if (carts.contains(cart)) {
                    // TODO : da co san pham trong gio hang
                   cart.setQuantity(cart.getQuantity() + cart.getQuantity());
                } else {
                    carts.add(cart);
                }
            } else {
                // TODO : chua co san pham trong gio hang
                carts.add(cart) ;
            }
        }
        // lua du lieu vao file
        userService.save(userLogin());
    }

    @Override
    public List<Cart> displayAll() {
        return userLogin().getCart();
    }

    @Override
    public Cart findById(int id) {
        for (Cart ci : userLogin().getCart()) {
            if (ci != null && ci.getCartId() == id) {
                return ci;
            }
        }
        return null;
    }
    public Cart findByProductId(int id) {
        for (Cart ci : userLogin().getCart()) {
            if (ci.getProduct().getProductId() == id) {
                return ci ;
            }
        }
        return null;
    }
    @Override
    public void delete(Cart cart) {

    }

    @Override
    public void update(List<Cart> t) {

    }

    @Override
    public int findByIndex(int id) {
        return 0;
    }

    public int getNewId() {
        int max = 0;
        for (Cart ci : userLogin().getCart()) {
            if (ci.getCartId() > max) {
                max = ci.getCartId();
            }
        }
        return max + 1;
    }


    public List<Cart> cartAll() {
        return userLogin().getCart();
    }


}
