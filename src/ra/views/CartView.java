package ra.views;

import ra.controllers.CartService;
import ra.models.Cart;

import java.util.List;

import static ra.config.ConsoleColor.*;
import static ra.config.Inputmethods.*;

public class CartView {
    private CartService cartService;

    public CartView(CartService cartService) {
        this.cartService = cartService;
    }

    public void displayMenuCart() {
        int selectCart;
        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║              Menu-Gio hang           ║");
            System.out.println("╟────────┬─────────────────────────────╢");
            System.out.println("║   1    │   Xem danh sach gio hang    ║");
            System.out.println("║   2    │   Chinh sua so luong        ║");
            System.out.println("║   3    │   Xoa 1 san pham            ║");
            System.out.println("║   4    │   Xoa toan bo san pham      ║");
            System.out.println("║   5    │   Thanh toan                ║");
            System.out.println("║   6    │   Thoat                     ║");
            System.out.println("╚════════╧═════════════════════════════╝");
            System.out.println("Nhap lua chon cua ban : ");
            selectCart = getInteger();
            switch (selectCart) {
                case 1:

                     displayAllCart();
                    break;
                case 2:
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:
                    return;

                default:
                    System.err.println("--->> Lua chon khong phu hop. Vui long chon lai ❤ ");
                    break;
            }
        }
    }

    private void displayAllCart() {
     List<Cart> carts= cartService.displayAll();
     if(carts.isEmpty()){
         printlnError("Danh sách rỗng");
     }
        for (Cart ca:carts
             ) {
            System.out.println(ca);
        }
    }
}
