package client;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClient {
    public static void main(String[] args) {
        String serverHost = "203.162.10.109";
        int serverPort = 2208;
        String studentCode = "B22DCCN372";
        String qCode = "MTX3PJYG";

        DatagramSocket socket = null;

        try {
            // 1️⃣ Tạo socket UDP
            socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            InetAddress address = InetAddress.getByName(serverHost);

            // 2️⃣ Gửi chuỗi xác thực
            String auth = ";" + studentCode + ";" + qCode;
            byte[] sendData = auth.getBytes();
            DatagramPacket authPacket = new DatagramPacket(sendData, sendData.length, address, serverPort);
            socket.send(authPacket);
            System.out.println("✅ Sent: " + auth);

            // 3️⃣ Nhận phản hồi từ server
            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);

            String received = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            System.out.println("📩 Received: " + received);

            // 4️⃣ Phân tách requestId và data
            int idx = received.indexOf(';');
            if (idx == -1) {
                System.err.println("⚠️ Dữ liệu nhận không hợp lệ!");
                return;
            }
            String requestId = received.substring(0, idx);
            String data = received.substring(idx + 1);

            // 5️⃣ Chuẩn hóa chuỗi
            String normalized = normalizeText(data);
            System.out.println("🧮 Normalized: " + normalized);

            // 6️⃣ Tạo chuỗi gửi lại
            String result = requestId + ";" + normalized;
            byte[] resultData = result.getBytes();
            DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, address, serverPort);
            socket.send(resultPacket);
            System.out.println("✅ Sent result to server: " + result);

            // 7️⃣ Đóng socket
            socket.close();
            System.out.println("🔚 Socket closed.");

        } catch (SocketTimeoutException e) {
            System.err.println("⏰ Timeout: Server không phản hồi trong 5s.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) socket.close();
        }
    }

    // Hàm chuẩn hóa chuỗi theo yêu cầu đề
    private static String normalizeText(String input) {
        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                  .append(w.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
