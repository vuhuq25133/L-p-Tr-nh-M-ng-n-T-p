package objectservice;

import vn.medianews.ObjectService;
import vn.medianews.ObjectService_Service;
import vn.medianews.Student;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String studentCode = "B22DCCN372";
        String qCode = "6M3ZtnTQ";

        // 1. Tạo service và lấy port
        ObjectService_Service service = new ObjectService_Service();
        ObjectService port = service.getObjectServicePort();

        // 2. Gọi requestListStudent để lấy danh sách sinh viên
        List<Student> students = port.requestListStudent(studentCode, qCode);

        // 3. Lọc giữ lại sinh viên nhóm A và D:
        //    A: score >= 8.0
        //    D: score < 5.0
        List<Student> filtered = new ArrayList<Student>();
        for (Student st : students) {
            float score = st.getScore();
            if (score >= 8.0f || score < 5.0f) {
                filtered.add(st);
            }
        }

        // 4. Gửi danh sách đã lọc lên server
        port.submitListStudent(studentCode, qCode, filtered);

        System.out.println("DONE");
    }
}
