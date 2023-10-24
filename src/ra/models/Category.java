package ra.models;

import java.io.Serial;
import java.io.Serializable;

public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public void disphayCategory() {

        System.out.println("Mã danh mục: " + this.categoryId + " ---- Tên danh mục: " + this.categoryName);

    }

    @Override
    public boolean equals(Object obj) {
        Category o = (Category) obj;
        return this.categoryId == o.categoryId && this.categoryName.equals(o.categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
