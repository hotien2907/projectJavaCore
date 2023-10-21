package ra.run;

import ra.controllers.CartService;
import ra.controllers.CategoryService;
import ra.controllers.MenuService;
import ra.controllers.UserService;
import ra.controllers.fileservice.IoFile;;
import ra.models.Cart;
import ra.models.Category;
import ra.models.Product;
import ra.models.User;
import ra.views.CartView;
import ra.views.CategoryView;
import ra.views.MenuView;
import ra.views.UserView;

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
        IoFile ioFile3 = new IoFile<>();
        CartService cartService = new CartService(ioFile3,userService,menuService);
        //category
        IoFile<Category> ioFile2 = new IoFile<>(CATEGORY_FILE);
        CategoryService categoryService = new CategoryService(ioFile2);
        CartView cartView = new CartView(cartService);

        CategoryView categoryView = new CategoryView(categoryService);

        MenuView menuView = new MenuView(cartView, menuService,cartService,categoryService);
        //user
        UserView userView = new UserView(userService,menuView,categoryView,cartView);
        userView.loginOrRegister(scanner);

    }
}
