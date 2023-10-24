package ra.views;
import ra.controllers.CategoryService;
import ra.controllers.MenuService;
import ra.models.Category;
import ra.models.Product;
import java.util.List;
import java.util.Scanner;
import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.*;

public class CategoryView {
    private CategoryService categoryService;
    public UserView userView;
    private MenuService menuService;

    public CategoryView(CategoryService categoryService, UserView userView, MenuService menuService) {
        this.categoryService = categoryService;
        this.userView = userView;
        this.menuService = menuService;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public UserView getUserView() {
        return userView;
    }

    static Scanner scanner = new Scanner(System.in);

    public  void displayAdminCategory() {
        int choice;

        do {

            print(YELLOW);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       😍🧡  ADMIN-CATEGORY 😍😍     ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Thêm mới danh mục        ║");
            System.out.println("║   2    │    Hiển thị danh mục        ║");
            System.out.println("║   3    │    Xóa danh mục             ║");
            System.out.println("║   4    │    Sửa danh mục             ║");
            System.out.println("║   5    │    Quay lại menu trước      ║");
            System.out.println("║   6    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
            printFinish();

            choice = getInteger();

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    displayAllCategorys();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    editCategory();
                    break;
                case 5:
                    return;
                case 6:
                    if (userView != null) {
                        userView.logout();

                    }

                    break;
                default:
                    break;
            }

        } while (true);

    }

    private void editCategory() {
        System.out.println("Nhập vào id danh mục cần sửa: ");
        int id = getInteger();
        List<Category> allCategory = categoryService.getAll();

        int index = categoryService.findByIndex(id);
        if (index != -1) {
            Category newCategory = allCategory.get(index);
            System.out.println("Nhập vào tên danh mục mới: ");
            String inputNewCategory = getString();
            newCategory.setCategoryName(inputNewCategory);
            categoryService.update(allCategory);

            List<Product> products = menuService.getAll();
            for (Product pr : products
            ) {
                if (pr.getCategory().getCategoryId() == id) {
                    pr.setCategory(new Category(id, inputNewCategory));
                }
            }
           menuService.update(products);
        } else {
            printlnError("Không tìm thấy mã danh mục cần sửa !!!");
        }


    }

    private void deleteCategory() {
        System.out.println("Nhâp vào mã danh mục cần xóa: ");
        int id = getInteger();
        Category category = categoryService.findById(id);
        if (category != null) {
            categoryService.delete(category);
            printlnSuccess("Done!");

            List<Product> products = menuService.getAll();

            for (Product pr:products
                 ) {
                if(pr.getCategory().equals(category)){
                   menuService.delete(pr);
                }
            }

        } else {
            printlnError("Không tìm thấy mã danh mục cần xóa !!.");
        }
    }

    private void addCategory() {
        Category category = inputCategory();
        categoryService.save(category);
        printlnSuccess(" Thêm danh mục thành công !");
    }

    private Category inputCategory() {
        Category category = new Category();

        System.out.println("Nhập tên danh mục:");
        String categoryName = scanner.nextLine();
        category.setCategoryName(categoryName);
        category.setCategoryId(categoryService.autoInc());
        return category;
    }

    private void displayAllCategorys() {
        List<Category> allCategory = categoryService.getAll();
        if (allCategory == null) {
            System.out.println("Danh mục rỗng");
        }
        assert allCategory != null;

        for (Category ca : allCategory) {
            ca.disphayCategory();
        }
    }


}
