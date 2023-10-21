package ra.views;

import ra.controllers.CategoryService;
import ra.models.Category;

import java.util.List;
import java.util.Scanner;

import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.*;

public class CategoryView {
    private CategoryService categoryService;

    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    static Scanner scanner = new Scanner(System.in);

    public void displayAdminCategory() {
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
                    System.out.println("Thoát chương trình");
                    return;
                case 6:

                    break;
                default:
                    break;
            }

        } while (true);

    }

    private void editCategory() {
        System.out.println("Nhập vào id danh mục cần sửa");
        int id = getInteger();
        List<Category> allCategory = categoryService.displayAll();

        int index = categoryService.findByIndex(id);
        if (index != -1) {
          Category newCategory=  allCategory.get(index);
            System.out.println("Nhập vào tên danh mục mới");
            String inputNewCategory = getString();
            newCategory.setCategoryName(inputNewCategory);
            categoryService.update(allCategory);
        }else {
            System.out.println("Không tìm thấy mã danh mục cần sửa !!!");
        }



    }

    private void deleteCategory() {
        System.out.println("nhâp vào mã danh mục cần xóa");
        int id = getInteger();
        Category category = categoryService.findById(id);
        if (category != null) {
            categoryService.delete(category);
            System.out.println("Done!");
        }
    }

    private void addCategory() {
        Category category = inputCategory();
        categoryService.save(category);
        System.out.println(" Thêm danh mục thành công !");
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
        List<Category> allCategory = categoryService.displayAll();
        if (allCategory == null) {
            System.out.println("Danh mục rỗng");
        }
        assert allCategory != null;

        for (Category ca : allCategory) {
            ca.disphayCategory();
        }
    }


}
