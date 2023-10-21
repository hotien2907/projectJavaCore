package ra.controllers;
import ra.controllers.fileservice.IoFile;
import ra.models.Category;
import ra.repository.IShop;
import java.util.List;

public class CategoryService implements IShop<Category> {

    private final IoFile ioFile;

    public CategoryService(IoFile ioFile) {
        this.ioFile = ioFile;
    }

    private int countAllCategory() {
        return ioFile.getAll().size();
    }

    public int autoInc() {
        return countAllCategory() + 1;
    }


    @Override
    public void save(Category category) {
        List<Category> categories = displayAll();
        categories.add(category);
        ioFile.saveToFile(categories);
    }

    @Override
    public void delete(Category category) {
        List<Category> categories = displayAll();
        categories.remove(category);
        ioFile.saveToFile(categories);
    }

    @Override
    public void update(List<Category> t) {
        ioFile.saveToFile(t);
    }

    @Override
    public int findByIndex(int id) {
        List<Category> categories = displayAll();
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).equals(id)){
                return i;
            }
        }

        return -1;
    }


    @Override
    public List<Category> displayAll() {
        List<Category> allCategory = ioFile.getAll();
        return allCategory;

    }

    @Override
    public Category findById(int id) {
        for (Category ca : displayAll()) {
            if (ca.getCategoryId()==id) {
                return ca;
            }
        }
        return null;
    }
}
