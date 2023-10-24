package ra.views;

import ra.controllers.CartService;
import ra.controllers.CategoryService;
import ra.controllers.MenuService;
import ra.models.Cart;
import ra.models.Category;
import ra.models.Product;
import java.util.List;
import java.util.Scanner;
import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.*;


public class MenuView {
    private CartView cartView;
    private MenuService menuService;
    private CartService cartService;
    private CategoryService categoryService;
    private UserView userView;

    public MenuView(CartView cartView, MenuService menuService, CartService cartService, CategoryService categoryService, UserView userView) {
        this.cartView = cartView;
        this.menuService = menuService;
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.userView = userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    static Scanner scanner = new Scanner(System.in);

    public void displayUserMenuProduct() {
        int choice;

        do {

            print(BLACK);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          😍🧡USER-PRODUCT😍😍       ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Hiển thị ds thực đơn     ║");
            System.out.println("║   2    │    Tìm kiếm thực đơn        ║");
            System.out.println("║   3    │    Thêm vào  giỏ hàng       ║");
            System.out.println("║   4    │    Giỏ hàng                 ║");
            System.out.println("║   5    │    Quay lại menu trước      ║");
            System.out.println("║   6    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡: ");
            printFinish();
            choice = getInteger();

            switch (choice) {
                case 1:
                    displayProductList();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    addToCart();
                    break;
                case 4:
                    cartView.displayMenuCart();
                case 5:
                    return;
                case 6:
                    userView.logout();
                default:
                    break;
            }

        } while (choice != 5);

    }

    private void searchProduct() {
        List<Product>products = menuService.getAll();
        if(products.isEmpty()){
            printlnError("Chưa có sản phẩm");
            return;
        }
        System.out.println("Nhập vào ký tự tên muốn tìm kiếm: ");
        String keyName = getString().toLowerCase();
        for (Product pr:products
             ) {
            if(pr.getProductName().toLowerCase().contains(keyName)){
                System.out.println(pr);
            }
        }
    }

    public void displayMenuAdminMenuProduct() {
        int choice;
        do {


            print(BLACK);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          😍🧡ADMIN-PRODUCT😍😍      ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Thêm mới thực đơn        ║");
            System.out.println("║   2    │    Hiển thị ds thực đơn     ║");
            System.out.println("║   3    │    Xoá thực đơn             ║");
            System.out.println("║   4    │    Sửa thực đơn             ║");
            System.out.println("║   5    │    Tìm kiếm thực đơn        ║");
            System.out.println("║   7    │    Quay lại menu trước      ║");
            System.out.println("║   8    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡: ");
            printFinish();

            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addProduct();
                    break;

                case 2:
                    displayProductList();
                    break;
                case 3:

                    break;
                case 7:
                    return;
                case 8:
                    userView.logout();
                    break;
                default:
                    break;
            }

        } while (choice != 5);
    }

    private void displayProductList() {
        List<Product> sortedProducts = menuService.getSortedPriceProducts();
        if (sortedProducts.isEmpty()) {
            System.out.println("Danh sách rỗng !!!");
        } else {
            System.out.println("Danh sách đã được sắp xếp theo giá:");
            for (Product pr : sortedProducts) {
                System.out.println(pr);
            }
        }
    }


    private void addProduct() {
        Product product = inputProduct();
        menuService.save(product);
        System.out.println(" Thêm sản phẩm thành công !");
    }


    public Product inputProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        System.out.println("Nhập thông tin sản phẩm.");

        // Nhập ID sản phẩm (nếu cần)
        product.setProductId(menuService.autoInc());

        // Nhập tên sản phẩm
        System.out.println("Nhập tên sản phẩm:");
        String productName = getString();
        product.setProductName(productName);

        // Nhập giá sản phẩm
        System.out.println("Nhập giá sản phẩm:");
        double price = getDouble();
        product.setPrice(price);

        // Nhập số lượng
        System.out.println("Nhập số lượng:");
        int quantity = getInteger();
        product.setQuantity(quantity);
        printlnMess("DANH SÁCH DANH MỤC");
        List<Category> categories = categoryService.getAll();
        if(categories.isEmpty()){
            printlnError("Danh sách danh mục rỗng.Vui lòng thêm danh mục trước !!.");
            userView.displayAdminMenu(scanner);
        }
        for (Category ca : categories
        ) {
            ca.disphayCategory();
        }
        System.out.println("hãy chọn id category:");
        int categoryId = getInteger();

        for (Category ca : categories
        ) {
            if (ca.getCategoryId() == categoryId) {
                product.setCategory(ca);

            }
        }

        return product;
    }


    public void addToCart() {

        List<Product> products = menuService.getAll();
        if (products.isEmpty()) {
            printlnError("Chưa có sản phẩm");
            return;
        }

        for (Product pr : products) {
            System.out.println("ID: " + pr.getProductId() + ", Name: " + pr.getProductName());
        }

        System.out.println("Nhập vào ID sản phẩm để thêm vào giỏ hàng: ");
        int idPro = getInteger();
        Product product = menuService.findById(idPro);

        if (product == null) {
            System.err.println("Không tìm thấy sản phẩm với ID " + idPro);
            return;
        }

        Cart cart = new Cart();
        cart.setProduct(product);

        cart.setCartId(cartService.autoInc());

        while (true) {
            System.out.println("Nhập vào số lượng muốn thêm vào giỏ hàng: ");
            int count = getInteger();

            if (count > product.getStock()) {
                printlnError("Số lượng này lớn hơn hàng chúng tôi có sẵn. Vui lòng giảm số lượng xuống.");
            } else {
                cart.setQuantity(count);
                break;
            }
        }

        printlnSuccess("Thêm vào  giỏ hàng thành công🎈🎈!!");
        cartService.save(cart);
    }



}
