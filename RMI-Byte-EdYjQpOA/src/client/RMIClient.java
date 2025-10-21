package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RMI.ByteService;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        // ---- a. Kết nối đến RMI Server ----
        String host = "203.162.10.109";   // ✅ IP nơi đăng nhập vào bài thi
        int port = 1099;                 // ✅ Cổng registry mặc định của RMI
        Registry registry = LocateRegistry.getRegistry(host, port);

        // ---- b. Lấy đối tượng RMIByteService ----
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        // ---- c. Nhận dữ liệu nhị phân từ server ----
        String studentCode = "B22DCCN372";
        String qCode = "EdYjQpOA";
        byte[] data = service.requestData(studentCode, qCode);

        System.out.println("🔹 Dữ liệu gốc từ server:");
        System.out.println(new String(data, "US-ASCII"));

        // ---- d. Mã hóa Caesar ----
        int shift = data.length;
        byte[] encoded = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            encoded[i] = (byte) (data[i] + shift);
        }

        System.out.println("🔹 Dữ liệu sau mã hóa Caesar:");
        System.out.println(new String(encoded, "US-ASCII"));

        // ---- e. Gửi dữ liệu đã mã hóa về server ----
        service.submitData(studentCode, qCode, encoded);

        System.out.println("✅ Đã gửi dữ liệu mã hóa về server thành công!");
    }
}
