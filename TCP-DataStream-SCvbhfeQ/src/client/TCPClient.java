package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) throws Exception {
// ---- a. Kết nối tới server ---- 
        String serverHost = "203.162.10.109";
        int serverPort = 2207;
        Socket socket = new Socket(serverHost, serverPort);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
// ---- b. Gửi chuỗi studentCode;qCode ---- 
        String studentCode = "B22DCCN372";
        String qCode = "SCvbhfeQ";
        String sendStr = studentCode + ";" + qCode;
        dos.writeUTF(sendStr);
        dos.flush();
// ---- c. Nhận số nguyên từ server ----
        int number = dis.readInt();
        System.out.println("Số nhận được từ server: " + number);
// ---- d. Chuyển sang nhị phân ---- 
        String binary = Integer.toBinaryString(number);
        System.out.println("Dạng nhị phân: " + binary);
// ---- e. Gửi chuỗi nhị phân lại cho server ---- 
        dos.writeUTF(binary);
        dos.flush();
// ---- f. Đóng kết nối ---- dis.close(); 
        dos.close();
        socket.close();
        System.out.println("✅ Đã gửi kết quả nhị phân về server thành công!");
    }
}
