package ra.controllers;

import ra.controllers.fileservice.IoFile;
import ra.models.Category;
import ra.models.Product;
import ra.repository.IShop;

import java.util.List;

public class CategoryService implements IShop<Category> {

    private final IoFile ioFile;

    public CategoryService(IoFile ioFile) {
        this.ioFile = ioFile;
    }


    @Override
    public int autoInc() {
        int max = 0;
        for (Category ca : getAll()) {
            if (max < ca.getCategoryId()) {
                max = ca.getCategoryId();
            }
        }
        return max + 1;
    }


    @Override
    public void save(Category category) {
        List<Category> categories = getAll();
        categories.add(category);
        ioFile.saveToFile(categories);
    }

    @Override
    public void delete(Category category) {
        List<Category> categories = getAll();
        categories.remove(category);
        ioFile.saveToFile(categories);
    }

    @Override
    public void update(List<Category> t) {
        ioFile.saveToFile(t);
    }

    @Override
    public int findByIndex(int id) {
        List<Category> categories = getAll();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId() == id) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public List<Category> getAll() {
        List<Category> allCategory = ioFile.getAll();
        return allCategory;

    }

    @Override
    public Category findById(int id) {
        for (Category ca : getAll()) {
            if (ca.getCategoryId() == id) {
                return ca;
            }
        }
        return null;
    }
}
