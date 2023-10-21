package ra.controllers;

import ra.controllers.fileservice.IoFile;
import ra.models.User;
import ra.repository.IShop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserService implements IShop<User> {
    private IoFile ioFile;
    private List<User> allUser = new ArrayList<>();

    public UserService(IoFile ioFile) {
        this.ioFile = ioFile;
    }


    private int countAllUser() {
        return ioFile.getAll().size();
    }

    public int autoInc() {
        return countAllUser() + 1;
    }


    @Override
    public void save(User user) {
        allUser = ioFile.getAll();
        allUser.add(user);
        ioFile.saveToFile(allUser);
    }

    @Override
    public List<User> displayAll() {
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

    public void setStatusLogin( String userName,Boolean newStatus) {
        List<User> allUser = ioFile.getAll();
        for (User user : allUser) {
            if (user.getUsername().equals(userName)) {
                user.setStatus(newStatus);
                break;
            }
        }
        ioFile.saveToFile(allUser);
    }

//    public void setStatus(boolean status, String username) {
//        allUser = ioFile.getAll();
//        for (User user : allUser) {
//            if (user.getUsername().equals(username)) {
//                user.setStatus(status);
//            }
//        }
//
//        ioFile.saveToFile(allUser);
//
//    }

    public User userAcitive() {
        List<User> allUser = ioFile.getAll();

        for (User u : allUser) {
            if (u.isStatus()) {
                return u;
            }
        }
        return null;
    }

//    public boolean isValidName(String name) {
//
//
//        if (name.isEmpty()) {
//            System.out.println("Tên không được để trống");
//            return false;
//        }else if (ioFile.getUserByUsename(name) !=null){
//            System.out.println("Tên đã tồn tại.");
//            return false;
//        }
//        return true;
//    }


}