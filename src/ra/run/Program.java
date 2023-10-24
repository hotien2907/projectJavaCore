package ra.run;

import ra.controllers.*;
import ra.controllers.fileservice.IoFile;;
import ra.models.*;
import ra.views.*;
import java.util.Scanner;
import static ra.constant.Contant.FilePath.*;
public class Program {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //user
        IoFile<User> ioFile = new IoFile<>(USER_FILE);
        UserService userService = new UserService(ioFile);
        //menu
        IoFile<Product> ioFile1 = new IoFile<>(PRODUCT_FILE);
        MenuService menuService = new MenuService(ioFile1);
        //cart
        //oder
        IoFile < Order> ioFile4 = new IoFile<>(ORDER_FILE);
        OrderService orderService = new OrderService(ioFile4);
        //----------
        CartService cartService = new CartService(userService);
        //oderHistory
        OrderHistoryView orderHistoryView = new OrderHistoryView(orderService,userService,null);
        //category
        IoFile<Category> ioFile2 = new IoFile<>(CATEGORY_FILE);
        CategoryService categoryService = new CategoryService(ioFile2);
        CartView cartView = new CartView(cartService,null,userService,orderService,menuService,orderHistoryView);

        CategoryView categoryView = new CategoryView(categoryService, null,menuService);

        MenuView menuView = new MenuView(cartView, menuService, cartService, categoryService,null);
        //user
        UserView userView = new UserView(userService, menuView, cartView, categoryView,orderHistoryView);

        //dung chung
        categoryView.setUserView(userView);
        menuView.setUserView(userView);
        cartView.setUserView(userView);
        userView.loginOrRegister(scanner);
        orderHistoryView.setUserView(userView);

    }
}
