package ra.views;



import ra.controllers.UserService;
import ra.models.User;

import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.*;

import java.util.List;
import java.util.Scanner;


import static ra.config.Validation.*;
import static ra.constant.Contant.ADMIN_CODE;
import static ra.constant.Contant.Role.*;
import static ra.constant.Contant.Status.*;
import static ra.views.CategoryView.scanner;

public class UserView {
    private UserService userService;
    private MenuView menuView;
  private  CartView cartView;
    private CategoryView categoryView;
   static String userName;

    public UserView(UserService userService, MenuView menuView, CategoryView categoryView,CartView cartView) {
        this.userService = userService;
        this.menuView = menuView;
        this.categoryView = categoryView;
        this.cartView =cartView;
    }

    public void loginOrRegister(Scanner scanner) {

        print(BLUE);
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       😍🧡  QUẢN LÝ QUÁN CAFE 😍😍  ║");
        System.out.println("╟────────┬─────────────────────────────╢");
        System.out.println("║   1    │    Đăng nhập                ║");
        System.out.println("║   2    │    Đăng ký                  ║");
        System.out.println("║   3    │    Thoát                    ║");
        System.out.println("╚════════╧═════════════════════════════╝");
        System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
        printFinish();

        int choice = getInteger();


        switch (choice) {
            case 1: //login

                String pass;

                System.out.println("Hay thuc hien dang nhap");
                while (true) {
                    printlnSuccess(" userName: ");
                    userName = scanner.nextLine();
                    if (isValidFullName(userName)) {
                        break;
                    }
                }
                while (true) {
                    System.out.println("Password: ");
                    pass = scanner.nextLine();
                    if (isValidPassword(pass)) {
                        break;
                    }
                }
                User user;
                user = userService.login(userName, pass);
                if (user != null) {
                    userService.setStatusLogin(userName, ACTIVE);
                    if (user.getRole() == ADMIN) {

                        displayAdminMenu(scanner);

                    } else {
                        displayUserMenu(scanner);
                    }

                } else {
                    printlnError("Đăng nhập thấy bại,Mật khâu hoặc UserName ko trùng hợp!!! ");
                    loginOrRegister(scanner);

                }

                break;
            case 2://register
                User user1 = registerUser(scanner);
                userService.save(user1);
                printlnSuccess("Đăng ký thành công !");
                loginOrRegister(scanner);
                break;
            default:
        }
    }

    private void displayUserMenu(Scanner scanner) {
        int choice;
      do {
          print(PURPLE);
          System.out.println("╔══════════════════════════════════════╗");
          System.out.println("║       😍🧡  QUẢN LÝ USER 😍😍       ║");
          System.out.println("╟────────┬─────────────────────────────╢");
          System.out.println("║   1    │    Trang chủ                ║");
          System.out.println("║   2    │    Giỏi hàng                ║");
          System.out.println("║   4    │    Đặt bàn                  ║");
          System.out.println("║   5    │    Đăng xuất                ║");
          System.out.println("╚════════╧═════════════════════════════╝");
          System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
          printFinish();

    choice = getInteger();
          switch (choice) {
              case 1:
                  menuView.displayUserMenuProduct();
                  break;

              case 2:
                  cartView.displayMenuCart();
                  break;
              case 5:
                  logout();
                  break;
          }
      }while (choice !=5);
    }


    private void displayAdminMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("***************Admin **********************");
            System.out.println("1. quan ly nguoi dung");
            System.out.println("2. quan ly danh muc");
            System.out.println("3. quan ly sp");
            System.out.println("4. hoa don");
            System.out.println("5.Quản lý nhân viên");
            System.out.println("6.dang xuat");
            System.out.println("7.Thoát");
            System.out.println("8.Đănng xuất");
            choice = getInteger();
            switch (choice) {
                case 1:
                    userManagement(scanner);
                    break;

                case 2:
                    categoryView.displayAdminCategory();
                    break;
                case 3:
                    menuView.displayMenuAdminMenuProduct();
                    break;
                case 4:
                    break;
                case 7:
                    System.out.println("thoát");
                    return;

                case 8:
                    logout();
                    break;
                default:
                    break;
            }
        } while (choice != 7);
    }

    public void logout() {
        loginOrRegister(scanner);
        userService.setStatusLogin(userName,INACTIVE);
    }

    private void userManagement(Scanner scanner) {
        System.out.println("***************** userManagement********************");
        System.out.println("1.Hiển thị danh sách người dùng");
        System.out.println("2.Tìm kiếm người dùng theo tên");
//        System.out.println("3.Khoá/mở khóa người dùng");
        System.out.println("4.Thoát");
        System.out.println("nhap vao lua chon");
        int choice = getInteger();
        switch (choice) {
            case 1:
                displayUserList();
                break;
            case 2:
                displayUserByUserName(scanner);

                break;
            case 3:
//                changeUserStatus(scanner);
                break;
            default:
                break;
        }

    }

//    private void changeUserStatus(Scanner scanner) {
//        System.out.println("Hãy nhập username bạn muốn thay đổi trạng thái");
//        String username = scanner.nextLine();
//        System.out.println("Hãy nhập vào trạng thái bạn muốn set cho user (0: BLOCK, 1: ACTIVE)");
//        int statusInt = Integer.parseInt(scanner.nextLine());
//        userService.setStatusByUsername(statusInt == 0 ? BLOCK : ACTIVE, username);
//        printlnSuccess("Thay đổi trạng thái thành công!");
//    }

    private void displayUserByUserName(Scanner scanner) {
        System.out.println("nhap vao tu khoa tim kiem");
        String username = scanner.nextLine();
        List<User> fitterUsers = userService.getUserListByUsername(username);
        for (User us : fitterUsers
        ) {
            System.out.println(us);
        }
    }

    private void displayUserList() {
        List<User> sortedUsers = userService.getSortedUserList();
        System.out.println(" danh sach sap xep theo ten");
        for (User us : sortedUsers
        ) {
            us.disphay();
        }
    }


    private User registerUser(Scanner scanner) {
        User user = new User();
        System.out.println("Vui lòng đănng ký tài khoản");
        System.out.println("Hãy chọn role của bạn ( 1: ADMIN            2: USER) : ");
               int role = getInteger();

               if(role==ADMIN){
                   user.setRole(role);

                   System.out.println("Nhập vào mã xác nhận ADMIN");
                   String adminCode = getString();
                   if(!adminCode.equals(ADMIN_CODE)){
                       printlnError("Mã xác nhập không đúng.Vui lòng ");
                       registerUser(scanner);
                   }
               }

        System.out.println();
        while (true) {

            System.out.println("Hãy nhập vào tên đầy đủ");
            String fullName = scanner.nextLine();

            if (isValidFullName(fullName)) {
                user.setFullName(fullName);
                break;
            }
        }

//        while (true) {

//            String name = scanner.nextLine();
//            if (userService.isValidName(name)) {
//                user.setUsername(name);
//                break;
//            }
//
//
//        }
        System.out.println("Hãy nhập vào userName");
        String name = scanner.nextLine();
        user.setUsername(name);

        while (true) {
            System.out.println("Hãy nhập vào Email");
            String email = scanner.nextLine();
            if (isValidEmail(email)) {
                user.setEmail(email);
                break;
            }
        }
        while (true) {
            System.out.println("Hãy nhập vào pass");
            String pass = scanner.nextLine();
            if (isValidPassword(pass)) {
                user.setPassword(pass);
                break;
            }
        }


        while (true) {

            System.out.println("Hãy nhập vào địa chỉ");
            String address = scanner.nextLine();
            if (isValidAddress(address)) {
                user.setAddress(address);
                break;
            }

        }


        while (true) {
            System.out.println("Hãy nhập vào phone");
            String phone = scanner.nextLine();
            if (isValidPhone(phone)) {
                user.setPhone(phone);
                break;
            }

        }
        user.setId(userService.autoInc());
        return user;
    }

}
