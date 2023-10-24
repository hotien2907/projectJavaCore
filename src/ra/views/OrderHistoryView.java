package ra.views;

import static ra.config.Inputmethods.getInteger;

public class OrderHistory {
    private void OrderHistory() {

        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║          Don hang-Lich su mua han    ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │  Hien thi tat ca don hang   ║");
            System.out.println("║   2    │  Dơn hang dang cho          ║");
            System.out.println("║   3    │  Don hang da xac nhan       ║");
            System.out.println("║   4    │  Don hang da huy            ║");
            System.out.println("║   5    │  Chi tiet don hang          ║");
            System.out.println("║   6    │  Thoat                      ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            int choice = getInteger();


            switch (choice) {
                case 1:
                    showAllOrder();
                    break;
                case 2:
                    orderController.showOrderByCode((byte) 0);
                    break;
                case 3:
                    orderController.showOrderByCode((byte) 1);
                    break;
                case 4:
                    orderController.showOrderByCode((byte) 2);
                    break;
                case 5:
                    orderController.showOrderDetail();
                    break;
                case 6:
                    return;
                default:
                    System.err.println("--->> Lua chon khong phu hop. Vui long chon lai ❤ ");
            }
        }

    }
}
