package ra.views;
import ra.controllers.OrderService;
import ra.controllers.UserService;
import ra.models.Cart;
import ra.models.Order;
import java.util.List;
import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.getInteger;
import static ra.config.OderStatus.*;
public class OrderHistoryView {
    private OrderService orderService;
    private UserService userService;
    private UserView userView;

    public OrderHistoryView(OrderService orderService, UserService userService, UserView userView) {
        this.orderService = orderService;
        this.userService = userService;
        this.userView = userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public void OrderMenuHistory() {

        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║            LỊCH SỬ ĐẶT HÀNG          ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │  Hiển thị tất cả đơn hàng   ║");
            System.out.println("║   2    │  Đơn hàng đang chờ          ║");
            System.out.println("║   3    │  Đơn hàng đã xác nhận       ║");
            System.out.println("║   4    │  Hủy đơn hàng               ║");
            System.out.println("║   5    │  Chi tiết đơn hàng          ║");
            System.out.println("║   6    │  Quay lại menu trước        ║");
            System.out.println("║   7    │  Đămg xuất                  ║");
            System.out.println("╚════════╧═════════════════════════════╝");

            int choice = getInteger();


            switch (choice) {
                case 1:
                    showOrder();
                    break;
                case 2:
                    showOrderByStatus( WAITING);
                    break;
                case 3:
                    showOrderByStatus( ACCEPT);
                    break;
                case 4:
                    cancelOrder();
                    break;
                case 5:
                    System.out.print("Nhập ID đơn hàng: ");
                    int orderId = getInteger();
                    showOrderDetail(orderId);
                    break;
                case 6:
                    return;
                default:
                    printlnError("--->> Lua chon khong phu hop. Vui long chon lai ❤ ");
            }
        }

    }

    private void cancelOrder() {
        List<Order> orders = orderService.getAll();
        System.out.println("Nhập vào id đơn hàng bạn muốn hủy.");
        int id = getInteger();
        int index = orderService.findByIndex(id);

        if (index != -1) {
            Order order = orders.get(index);
            if (order.getStatus() == WAITING) {
                order.setStatus(CANCEL);
                orders.set(index, order);
                orderService.update(orders);
                printlnMess("Đã hủy đơn hàng.");
            } else {
                printlnMess("Đơn hàng của bạn đã được chấp nhận nên không thể hủy.");
            }
        } else {
            printlnError("Không tìm thấy id đơn hàng bạn muốn hủy.");
        }
    }

    private void showOrder() {
        printlnMess("________________________ LỊCH SỬ ĐẶT HÀNG________________________");
        for (Order order : orderService.getAll()) {

            if (order.getIdUser() == userService.userAcitive().getId()) {
                order.display();
                printlnMess("══════════════════════════════════════════════════════════════");
            }

        }

    }

    private void showOrderByStatus(byte statusCode) {
        for (Order order : orderService.getOrdersByStatus(statusCode)) {
            order.display();
        }
    }

    private void showOrderDetail(int orderId) {
        Order order = orderService.findById(orderId);
        if (order != null) {
            System.out.println("______________________ CHI TIẾT ĐƠN HÀNG ______________________");

            System.out.println("Người nhận       : " + order.getReceiver());
            System.out.println("Số điện thoại    : " + order.getNumberPhone());
            System.out.println("Địa chỉ          : " + order.getAddress());
            System.out.println("Thời gian        : " + order.getBuyDate());
            System.out.println("______________________ THÔNG TIN ĐƠN HÀNG _______________________");

            for (Cart ca : order.getOrderDetail()) {
                ca.display();
            }
            System.out.println("Tong tien : " + order.getTotal());
        } else {
            System.out.println("Không tìm thấy đơn hàng.");


        }

    }

    public void menuAdminOrder() {
        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          ADMIN-QUẢN LÝ ĐƠN HÀNG      ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │  Hiển thị tất cả đơn hàng   ║");
            System.out.println("║   2    │  xác nhập các đơn hàng      ║");
            System.out.println("║   6    │  Quay lại menu trước        ║");
            System.out.println("║   7    │  Đămg xuất                  ║");
            System.out.println("╚════════╧═════════════════════════════╝");

            int choice = getInteger();
            switch (choice) {
                case 1:
                    showAllOrder();
                    break;
                case 2:
                    orderConfirmation();
                    break;
                case 3:
                    return;
            }

        }
    }

    private void orderConfirmation() {
        List<Order> orders = orderService.getAll();
        showAllOrder();
        System.out.println("Nhập vào id order cần xác nhận: ");
        int id = getInteger();
        Order order = orderService.findById(id);

        if (order != null) {
            if (order.getStatus() == WAITING) {
                System.out.println("Xác nhận:     1.Chấp nhận      . 2.Hủy :");
                int x = getInteger();


                order.setStatus((byte) x);
                int index = orderService.findByIndex(id);
                orders.set(index, order);
                orderService.update(orders);
                printlnMess("Xác thực đơn hàng thành công");

            } else if (order.getStatus() == CANCEL) {
                printlnError("Đơn hàng đã bị hủy không thể xác thực !!.");
            } else {
                printlnError("Đơn hàng đã được xác nhận, không thể thay đổi trạng thái.");
            }
        } else {
            printlnError("Không tìm thấy đơn hàng.");
        }
    }


    private void showAllOrder() {

        for (Order order : orderService.getAll()) {
            order.display();


        }
    }
}
