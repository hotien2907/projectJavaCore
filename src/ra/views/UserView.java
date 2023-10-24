package ra.views;
import ra.controllers.UserService;
import ra.models.User;
import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.*;
import java.util.List;
import java.util.Scanner;
import static ra.config.Validation.*;
import static ra.constant.Contant.ADMIN_CODE;
import static ra.constant.Contant.Importance.*;
import static ra.constant.Contant.Role.*;
import static ra.constant.Contant.Status.*;
import static ra.views.CategoryView.scanner;

public class UserView {
    private UserService userService;
    private MenuView menuView;
    private CartView cartView;
    private CategoryView categoryView;
    private OrderHistoryView orderHistoryView;

    public UserView(UserService userService, MenuView menuView, CartView cartView, CategoryView categoryView, OrderHistoryView orderHistoryView) {
        this.userService = userService;
        this.menuView = menuView;
        this.cartView = cartView;
        this.categoryView = categoryView;
        this.orderHistoryView = orderHistoryView;
    }

    static String userName;

    public UserService getUserService() {
        return userService;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public CartView getCartView() {
        return cartView;
    }

    public CategoryView getCategoryView() {
        return categoryView;
    }

    public OrderHistoryView getOrderHistoryView() {
        return orderHistoryView;
    }

    public static String getUserName() {
        return userName;
    }

    public void loginOrRegister(Scanner scanner) {
        resetAll();
        print(YELLOW);
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       😍🧡  QUẢN LÝ QUÁN CAFE 😍😍  ║");
        System.out.println("╟────────┬─────────────────────────────╢");
        System.out.println("║   1    │    👩 Đăng nhập             ║");
        System.out.println("║   2    │    🧒 Đăng ký               ║");
        System.out.println("║   3    │       Thoát                 ║");
        System.out.println("╚════════╧═════════════════════════════╝");
        System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
        printFinish();

        int choice = getInteger();


        switch (choice) {
            case 1: //login

                String pass;

                printlnMess("Thực hiện đăng nhập 🧡😍:");
                while (true) {
                    System.out.println("UserName: ");
                    userName = getString();
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
                    if (user.isImportance()) {
                        userService.setStatusLogin(userName, ACTIVE);
                        if (user.getRole() == ADMIN) {

                            displayAdminMenu(scanner);

                        } else {
                            displayUserMenu(scanner);
                        }

                    } else {
                        printlnError("Tài khoản của bạn đã bị khóa😂😂 !!");
                        loginOrRegister(scanner);
                    }


                } else {
                    printlnError("Đăng nhập thấy bại,Mật khẩu hoặc UserName ko trùng hợp!!! ");
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


    private void resetAll() {
        List<User> users = userService.getAll();
        for (User us : users
        ) {
            us.setStatus(INACTIVE);
        }
        userService.update(users);
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
        } while (choice != 5);
    }


    public void displayAdminMenu(Scanner scanner) {
        int choice;


        do {

            print(PURPLE);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          😍🧡  ADMIN 😍😍           ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Quản lý người dùng       ║");
            System.out.println("║   2    │    Quản lý danh mục         ║");
            System.out.println("║   3    │    Quản lý sản phẩm         ║");
            System.out.println("║   4    │    Quản lý Đơn hàng         ║");
            System.out.println("║   5    │    Quản lý nhân viên        ║");
            System.out.println("║   6    │    Quản lý đặt bàn          ║");
            System.out.println("║   7    │    Quay lại menu trước      ║");
            System.out.println("║   8    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
            printFinish();

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
                    orderHistoryView.menuAdminOrder();
                    break;
                case 7:
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
        userService.setStatusLogin(userName, INACTIVE);
        loginOrRegister(scanner);

    }

    private void userManagement(Scanner scanner) {
        int choice;

        do {
            print(CYAN);
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       😍🧡  QUẢN LÝ USER 😍😍       ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │    Danh sách user           ║");
            System.out.println("║   2    │    Tìm kiếm user theo tên   ║");
            System.out.println("║   3    │    Khóa/ mở user            ║");
            System.out.println("║   4    │    Quay lại menu trước      ║");
            System.out.println("║   5    │    Đăng xuất                ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhập vào lựa chọn của bạn 🧡🧡 : ");
            choice = getInteger();
            printFinish();

            switch (choice) {
                case 1:
                    displayUserList();
                    break;
                case 2:
                    displayUserByUserName(scanner);

                    break;
                case 3:
                    changeUserImportance(scanner);
                    break;
                case 4:
                    return;
                case 5:
                    logout();
                    break;
                default:
                    break;
            }
        } while (true);


    }

    private void changeUserImportance(Scanner scanner) {
        System.out.println("Hãy nhập username bạn muốn thay đổi trạng thái:");
        String username = getString();
        User user = userService.getUserByUsename(username);
        if (user == null) {
            printlnError("Không tìm thấy username bạn muốn đổi trạng thái !!");
        } else {
            if (user.getRole() == ADMIN) {
                printlnError("Không thể khóa user ADMIN !!");
            } else {
                userService.updateImportance((user.isImportance() == OPEN ? BLOOK : OPEN), username);
                printlnSuccess("Thay đổi trạng thái thành công!");
            }

        }

    }

    private void displayUserByUserName(Scanner scanner) {
        printlnMess("Nhập vào từ khóa tìm kiếm theo tên: !!");
        String username = scanner.nextLine();
        List<User> fitterUsers = userService.getUserListByUsername(username);
        for (User us : fitterUsers
        ) {
            us.display();
        }
    }

    private void displayUserList() {
        List<User> sortedUsers = userService.getSortedUserList();
        printlnMess("Danh sách user được sắp xếp theo tên !!!");
        for (User us : sortedUsers
        ) {

            us.display();

        }
    }


    private User registerUser(Scanner scanner) {
        User user = new User();
        printlnMess("Vui lòng đăng ký tài khoản !!");
        System.out.println("Hãy chọn role của bạn 1: ADMIN            2: USER: ");
        int role = getInteger();
        user.setId(userService.autoInc());
        if (role == ADMIN) {
            user.setRole(role);

            printlnMess("Nhập vào mã xác nhận ADMIN: ");
            String adminCode = getString();
            if (!adminCode.equals(ADMIN_CODE)) {
                printlnError("Mã xác nhập không đúng.Vui lòng thử lại !! ");
                registerUser(scanner);
            }
        }

        System.out.println();
        while (true) {

            System.out.println("Hãy nhập vào họ tên đầy đủ: ");
            String fullName = scanner.nextLine();

            if (isValidFullName(fullName)) {
                user.setFullName(fullName);
                break;
            }
        }

        while (true) {
            System.out.println("Nhập vào userName: ");
            String name = scanner.nextLine();
            if (userService.isValidName(name)) {
                user.setUsername(name);
                break;
            }


        }


        while (true) {
            System.out.println("Hãy nhập vào email:");
            String email = scanner.nextLine();
            if (isValidEmail(email)) {
                user.setEmail(email);
                break;
            }
        }
        while (true) {
            System.out.println("Hãy nhập vào password:");
            String pass = scanner.nextLine();
            if (isValidPassword(pass)) {
                user.setPassword(pass);
                break;
            }
        }


        while (true) {

            System.out.println("Hãy nhập vào địa chỉ:");
            String address = scanner.nextLine();
            if (isValidAddress(address)) {
                user.setAddress(address);
                break;
            }

        }


        while (true) {
            System.out.println("Hãy nhập vào phone:");
            String phone = scanner.nextLine();
            if (isValidPhone(phone)) {
                user.setPhone(phone);
                break;
            }

        }

        return user;
    }

}
