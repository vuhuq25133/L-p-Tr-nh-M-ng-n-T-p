package client;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        // ===== Thông tin server =====
        String host = "203.162.10.109";
        int port = 2208;
        String studentCode = "B22DCCN372";
        String qCode = "loaggcst";

        // ===== Kết nối tới server =====
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 5000);
        socket.setSoTimeout(5000);
        System.out.println("✅ Đã kết nối tới " + host + ":" + port);

        // ===== Tạo luồng ký tự =====
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // ===== a. Gửi "studentCode;qCode" =====
        String msg = studentCode + ";" + qCode;
        writer.write(msg);
        writer.newLine(); // gửi dấu xuống dòng để server biết hết chuỗi
        writer.flush();
        System.out.println(">> Đã gửi: " + msg);

        // ===== b. Nhận chuỗi domain từ server =====
        String data = reader.readLine();
        System.out.println(">> Nhận từ server: " + data);

        // ===== c. Lọc các domain .edu =====
        String[] domains = data.split(",");
        List<String> eduDomains = new ArrayList<>();
        for (String d : domains) {
            d = d.trim();
            if (d.toLowerCase().endsWith(".edu")) {
                eduDomains.add(d);
            }
        }

        String result = String.join(", ", eduDomains);
        System.out.println(">> Kết quả lọc: " + result);

        // ===== d. Gửi kết quả lại cho server =====
        writer.write(result);
        writer.newLine();
        writer.flush();
        System.out.println(">> Đã gửi kết quả lên server.");

        // ===== e. Đóng kết nối =====
        reader.close();
        writer.close();
        socket.close();
        System.out.println("🔒 Kết thúc kết nối.");
    }
}
