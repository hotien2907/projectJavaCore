package ra.controllers;
import ra.controllers.fileservice.IoFile;
import ra.models.User;
import ra.repository.IShop;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ra.config.ConsoleColor.*;

public class UserService implements IShop<User> {
    private IoFile ioFile;
    private List<User> allUser = new ArrayList<>();

    public UserService(IoFile ioFile) {
        this.ioFile = ioFile;
    }


    public int autoInc() {
        int max = 0;
        for (User us : getAll()) {
            if (max < us.getId()) {
                max = us.getId();
            }
        }
        return max + 1;
    }


    @Override
    public void save(User user) {
        List<User> allUser = ioFile.getAll();
        boolean updated = false;

        for (int i = 0; i < allUser.size(); i++) {
            if (allUser.get(i).getUsername().equals(user.getUsername())) {
                allUser.set(i, user);  // Cập nhật thông tin người dùng
                updated = true;
                break;
            }
        }

        if (!updated) {
            allUser.add(user);
        }

        ioFile.saveToFile(allUser);
    }


    @Override
    public List<User> getAll() {
        List<User> allUser = ioFile.getAll();
        return allUser;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(List<User> t) {
        ioFile.saveToFile(t);
    }

    @Override
    public int findByIndex(int id) {
        return 0;
    }

    public User getUserByUsename(String userName) {
        allUser = ioFile.getAll();
        for (User us : allUser) {
            if (us.getUsername() != null && us.getUsername().equals(userName)) {
                return us;
            }
        }
        return null;
    }

    public User login(String userName, String pass) {
        User user = getUserByUsename(userName);

        if (user != null && user.getPassword().equals(pass)) {
            return user;
        }


        return null;
    }

    public List<User> getSortedUserList() {
        List<User> sorteUsers = ioFile.getAll();
        Collections.sort(sorteUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });

        return sorteUsers;

    }

    public List<User> getUserListByUsername(String username) {
        List<User> allUsers = ioFile.getAll();
        List<User> filteredUsers = new ArrayList<>();
        for (User us : allUsers
        ) {
            if (us.getUsername().toLowerCase().contains(username.toLowerCase())) {
                filteredUsers.add(us);
            }
        }
        return filteredUsers;
    }

    public void setStatusLogin(String userName, Boolean newStatus) {
        List<User> allUser = ioFile.getAll();
        for (User user : allUser) {
            if (user.getUsername().equals(userName)) {
                user.setStatus(newStatus);
                break;
            }
        }
        ioFile.saveToFile(allUser);
    }

    public void updateImportance(boolean status, String username) {
        allUser = ioFile.getAll();
        for (User user : allUser) {
            if (user.getUsername().equals(username)) {
                user.setImportance(status);
            }
        }

        ioFile.saveToFile(allUser);

    }

    public User userAcitive() {
        List<User> allUser = ioFile.getAll();

        for (User u : allUser) {
            if (u != null && u.isStatus()) {
                return u;
            }
        }
        return null;
    }

    public boolean isValidName(String name) {


        if (name.isEmpty()) {
         printlnError("Tên không được để trống");
            return false;
        }else if (getUserByUsename(name) !=null){
            printlnError("Tên đã tồn tại.Vui lòng nhập tên khác !!.");
            return false;
        }
        return true;
    }


}