// File: client/RMIClient.java
package client;

import RMI.ProductX;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RMI.ObjectService;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN372";
        String qCode = "WeX7EyrZ";
        String host = "203.162.10.109";
        String serviceName = "RMIObjectService";

        // ===== B1: Kết nối Registry =====
        Registry registry = LocateRegistry.getRegistry(host, 1099);
        ObjectService stub = (ObjectService) registry.lookup(serviceName);

        // ===== B2: Nhận đối tượng ProductX từ server =====
        Serializable obj = stub.requestObject(studentCode, qCode);
        ProductX product = (ProductX) obj;

        System.out.println("Đối tượng nhận từ server:");
        System.out.println(product);

        // ===== B3: Tính discount từ discountCode =====
        int total = calcDiscount(product.getDiscountCode());
        product.setDiscount(total);

        System.out.println("Sau khi cập nhật giảm giá:");
        System.out.println(product);

        // ===== B4: Gửi đối tượng đã cập nhật trở lại server =====
        stub.submitObject(studentCode, qCode, product);

        System.out.println("✅ Đã gửi ProductX cập nhật về server!");
    }

    // ===== Hàm tính tổng các chữ số trong discountCode =====
    private static int calcDiscount(String code) {
        int sum = 0;
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) sum += c - '0';
        }
        return sum;
    }
}
