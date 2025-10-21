package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import TCP.Customer;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        // ---- a. Kết nối đến server ----
        String host = "203.162.10.109";
        int port = 2209;
        Socket socket = new Socket(host, port);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        // ---- b. Gửi chuỗi "studentCode;qCode" ----
        String studentCode = "B22DCCN372";
        String qCode = "PuODlALs";
        oos.writeObject(studentCode + ";" + qCode);
        oos.flush();

        // ---- c. Nhận đối tượng Customer từ server ----
        Customer customer = (Customer) ois.readObject();
        System.out.println("🔹 Dữ liệu gốc nhận từ server:");
        System.out.println(customer);

        // ---- d. Chuẩn hóa dữ liệu ----
        // 1️⃣ Chuẩn hóa tên: "nguyen van hai duong" -> "DUONG, Nguyen Van Hai"
        String fullName = customer.getName().trim();
        String[] parts = fullName.toLowerCase().split("\\s+");
        String lastName = parts[parts.length - 1].toUpperCase();
        StringBuilder first = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            first.append(Character.toUpperCase(parts[i].charAt(0)))
                 .append(parts[i].substring(1)).append(" ");
        }
        String formattedName = lastName + ", " + first.toString().trim();
        customer.setName(formattedName);

        // 2️⃣ Đổi định dạng ngày sinh: mm-dd-yyyy -> dd/mm/yyyy
        String dob = customer.getDayOfBirth();
        String[] dobParts = dob.split("-");
        if (dobParts.length == 3)
            customer.setDayOfBirth(dobParts[1] + "/" + dobParts[0] + "/" + dobParts[2]);

        // 3️⃣ Sinh userName: chữ cái đầu của các từ + họ cuối, tất cả in thường
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++)
            username.append(parts[i].charAt(0));
        username.append(parts[parts.length - 1]);
        customer.setUserName(username.toString().toLowerCase());

        System.out.println("✅ Sau chuẩn hóa:");
        System.out.println(customer);

        // ---- e. Gửi lại đối tượng Customer đã chỉnh sửa ----
        oos.writeObject(customer);
        oos.flush();

        // ---- f. Đóng kết nối ----
        ois.close();
        oos.close();
        socket.close();

        System.out.println("✅ Đã gửi Customer đã chuẩn hóa về server thành công!");
    }
}
