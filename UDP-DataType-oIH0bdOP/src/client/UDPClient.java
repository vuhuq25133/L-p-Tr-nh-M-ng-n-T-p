package client;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClient  {
    public static void main(String[] args) {
        String serverHost = "203.162.10.109";
        int serverPort = 2207;
        String studentCode = "B22DCCN372";
        String qCode = "oIH0bdOP";

        DatagramSocket socket = null;

        try {
            // 1. Mở socket UDP
            socket = new DatagramSocket();
            socket.setSoTimeout(5000); // timeout 5s
            InetAddress serverAddr = InetAddress.getByName(serverHost);

            // 2. Gửi chuỗi xác thực
            String auth = ";" + studentCode + ";" + qCode;
            byte[] sendData = auth.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddr, serverPort);
            socket.send(sendPacket);    
            System.out.println("✅ Sent: " + auth);

            // 3. Nhận phản hồi từ server
            byte[] receiveBuf = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            System.out.println("📩 Received: " + response);

            // 4. Phân tích dữ liệu
            String[] parts = response.split(";");
            if (parts.length < 4) {
                System.err.println("⚠️ Dữ liệu không hợp lệ!");
                return;
            }

            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);
            int k = Integer.parseInt(parts[2]);
            String[] numStrs = parts[3].split(",");
            int[] arr = Arrays.stream(numStrs).mapToInt(Integer::parseInt).toArray();

            // 5. Tính giá trị max trong từng cửa sổ trượt kích thước k
            List<Integer> maxList = new ArrayList<>();
            for (int i = 0; i <= n - k; i++) {
                int max = arr[i];
                for (int j = i; j < i + k; j++) {
                    if (arr[j] > max) max = arr[j];
                }
                maxList.add(max);
            }

            // 6. Chuẩn bị chuỗi kết quả
            String result = requestId + ";" + maxList.toString().replaceAll("[\\[\\] ]", "");
            System.out.println("🧮 Result: " + result);

            // 7. Gửi kết quả lại cho server
            byte[] resultData = result.getBytes();
            DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, serverAddr, serverPort);
            socket.send(resultPacket);
            System.out.println("✅ Sent result to server.");

            // 8. Đóng socket
            socket.close();
            System.out.println("🔚 Socket closed.");

        } catch (SocketTimeoutException e) {
            System.err.println("⏰ Timeout - Server không phản hồi trong 5s.");
        } catch (IOException e) {
            System.err.println("❌ Lỗi I/O: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) socket.close();
        }
    }
}

