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

 private  CartView cartView;
    private MenuService menuService;
    private CartService cartService;
    private CategoryService categoryService;

    public MenuView(CartView cartView, MenuService menuService, CartService cartService, CategoryService categoryService) {
        this.cartView = cartView;
        this.menuService = menuService;
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    static  Scanner scanner = new Scanner(System.in);
    public void displayUserMenuProduct() {
        int choice ;

        do {

            System.out.println("********************USER-PRODUCT***********************");
            System.out.println("1.Hiển thị danh sách món ăn và đồ uống");
            System.out.println("2.Xem thông tin chi tiết của sản phẩm");
            System.out.println("3.Thêm vào gỏi hàng");
            System.out.println("4.Xem giỏ hàng");
            System.out.println("5.Quay lại menu trước");
            choice = getInteger();

              switch (choice) {
                  case 1:
                   displayProductList();
                      break;
                  case 2:
                      break;
                  case 3:
                  addToCart();
//                      cartView.displayMenuCart();
                      break;
                  case 4:

                  case 5:
                      return;
                  default:
                      break;
              }

        }while (choice !=5);

    }

    public  void displayMenuAdminMenuProduct() {
        int choice ;
        do {
            System.out.println("******************ADMIN-PRODUCT*******************");
            System.out.println("1.Thêm mới thực đơn vào menu");
            System.out.println("2.Hiển thị danh sách menu");
            System.out.println("3.Xóa");
            System.out.println("4.Sửa");
            System.out.println("5.Thoát");
            System.out.println("6.Đăng xuất");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    addProduct();
                    break;

                case 2:
                    displayProductList();
                    break;
                case 3:

                    break;
                default:
                    break;
            }

        }while (choice !=5);
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



    public  Product inputProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        System.out.println("Nhập thông tin sản phẩm.");

        // Nhập ID sản phẩm (nếu cần)
        product.setProductId(menuService.autoInc());

        // Nhập tên sản phẩm
        System.out.println("Nhập tên sản phẩm:");
        String productName = scanner.nextLine();
        product.setProductName(productName);

        // Nhập giá sản phẩm
        System.out.println("Nhập giá sản phẩm:");
        double price = Double.parseDouble(scanner.nextLine());
        product.setPrice(price);

        // Nhập số lượng
        System.out.println("Nhập số lượng:");
        int quantity = Integer.parseInt(scanner.nextLine());
        product.setQuantity(quantity);
        System.out.println("DANH SÁCH DANH MỤC");
      List<Category> categories=   categoryService.displayAll();
        for (Category ca:categories
             ) {
            ca.disphayCategory();
        }
        System.out.println("hãy chọn id category:");
         int categoryId = getInteger();
        for (Category ca:categories
        ) {
            if(ca.getCategoryId() ==categoryId){
                product.setCategory(ca);
            }
        }

        return product;
    }



    public void addToCart() {
        List<Product> products = menuService.displayAll();
        if (products.isEmpty()) {
            printlnError("Chưa có sản phẩm");
            return; // Rời khỏi phương thức nếu không có sản phẩm nào
        }

        // Hiển thị danh sách sản phẩm
        for (Product pr : products) {
            System.out.println("ID: " + pr.getProductId() + ", Name: " + pr.getProductName());
        }

        System.out.println("Nhập vào ID sản phẩm để thêm vào giỏ hàng: ");
        int idPro = getInteger();
        Product product = menuService.findById(idPro);

        if (product == null) {
            System.err.println("Không tìm thấy sản phẩm với ID " + idPro);
            return; // Rời khỏi phương thức nếu không tìm thấy sản phẩm
        }

        // Khởi tạo giỏ hàng
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setCartId(cartService.getNewId());

        while (true) {
            System.out.println("Nhập vào số lượng muốn thêm vào giỏ hàng: ");
            int count = getInteger();

            if (count > product.getStock()) {
                System.err.println("Số lượng này lớn hơn hàng chúng tôi có sẵn. Vui lòng giảm số lượng xuống.");
            } else {
                cart.setQuantity(count);
                break;
            }
        }
    }


}
