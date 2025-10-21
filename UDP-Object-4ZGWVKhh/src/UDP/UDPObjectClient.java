package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPObjectClient {
    public static void main(String[] args) {
        String serverHost = "203.162.10.109";
        int serverPort = 2209;
        String studentCode = "B22DCCN372";
        String qCode = "4ZGWVKhh";

        DatagramSocket socket = null;

        try {
            // 1️⃣ Tạo socket UDP
            socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            InetAddress address = InetAddress.getByName(serverHost);

            // 2️⃣ Gửi xác thực
            String auth = ";" + studentCode + ";" + qCode;
            byte[] sendData = auth.getBytes();
            DatagramPacket authPacket = new DatagramPacket(sendData, sendData.length, address, serverPort);
            socket.send(authPacket);
            System.out.println("✅ Sent: " + auth);

            // 3️⃣ Nhận phản hồi
            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            System.out.println("📩 Received " + receivePacket.getLength() + " bytes from server");

            // 4️⃣ Tách requestId và dữ liệu object
            byte[] data = Arrays.copyOfRange(receivePacket.getData(), 0, receivePacket.getLength());
            String requestId = new String(Arrays.copyOfRange(data, 0, 8));
            byte[] objectBytes = Arrays.copyOfRange(data, 8, data.length);

            // 5️⃣ Giải tuần tự hóa đối tượng Employee
            ByteArrayInputStream bis = new ByteArrayInputStream(objectBytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Employee emp = (Employee) ois.readObject();
            ois.close();

            System.out.println("👤 Received Employee: " + emp);

            // 6️⃣ Chuẩn hóa name
            emp.setName(formatName(emp.getName()));

            // 7️⃣ Tăng lương theo tổng chữ số của năm hireDate
            String year = emp.getHireDate().substring(0, 4);
            int sumDigits = year.chars().map(Character::getNumericValue).sum();
            double newSalary = emp.getSalary() * (1 + sumDigits / 100.0);
            emp.setSalary(newSalary);

            // 8️⃣ Đổi định dạng ngày hireDate -> dd/MM/yyyy
            String[] parts = emp.getHireDate().split("-");
            if (parts.length == 3) {
                emp.setHireDate(parts[2] + "/" + parts[1] + "/" + parts[0]);
            }

            System.out.println("🧮 Modified Employee: " + emp);

            // 9️⃣ Tuần tự hóa lại đối tượng
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(emp);
            oos.flush();
            byte[] empBytes = bos.toByteArray();

            // 10️⃣ Ghép 8 byte requestId + object bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(requestId.getBytes());
            out.write(empBytes);
            byte[] sendBack = out.toByteArray();

            // 11️⃣ Gửi lại cho server
            DatagramPacket resultPacket = new DatagramPacket(sendBack, sendBack.length, address, serverPort);
            socket.send(resultPacket);
            System.out.println("✅ Sent modified Employee back to server.");

            // 12️⃣ Đóng socket
            socket.close();
            System.out.println("Socket closed.");

        } catch (SocketTimeoutException e) {
            System.err.println("⏰ Timeout: Server không phản hồi.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) socket.close();
        }

    }

    // Viết hoa chữ cái đầu mỗi từ
    private static String formatName(String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
              .append(w.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }
}
