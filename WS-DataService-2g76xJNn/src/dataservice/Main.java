package dataservice;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import vn.medianews.DataService;
import vn.medianews.DataService_Service;

public class Main {
    public static void main(String[] args) {
        // ---- THÔNG TIN SINH VIÊN ----
        String studentCode = "B22DCCN372";   // Mã sinh viên của bạn
        String qCode = "2g76xJNn";          // Mã câu hỏi

        try {
            // a) Tạo service và port
            URL wsdlUrl = new URL("http://203.162.10.109:8080/JNPWS/DataService?wsdl");
            QName qname = new QName("http://medianews.vn/", "DataService");
            DataService_Service service = new DataService_Service(wsdlUrl, qname);
            DataService port = service.getDataServicePort();

            // a) Gọi getData để nhận danh sách số nguyên
            List<Integer> numbers = port.getData(studentCode, qCode);
            System.out.println("Danh sách số nhận được: " + numbers);

            // =================================
            // b) XỬ LÝ CHUYỂN ĐỔI HỆ CƠ SỐ
            // =================================
            List<String> results = new ArrayList<>();
            for (Integer num : numbers) {
                // Chuyển đổi sang hệ bát phân
                String octal = Integer.toOctalString(num);
                // Chuyển đổi sang hệ thập lục phân (viết hoa)
                String hex = Integer.toHexString(num).toUpperCase();
                // Thêm vào danh sách kết quả dạng "octal|hex"
                results.add(octal + "|" + hex);

                // In ra màn hình để kiểm tra
                System.out.println("Số " + num + " -> Bát phân: " + octal + ", Thập lục phân: " + hex);
            }

            // =================================
            // c) Gửi kết quả về server
            // =================================
            System.out.println("Đang gửi kết quả về server...");
            port.submitDataStringArray(studentCode, qCode, results);
            System.out.println("Đã gửi dữ liệu về server thành công!");

            // d) Kết thúc chương trình
            System.out.println("Chương trình đã hoàn thành!");

        } catch (Exception e) {
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }
    }
}