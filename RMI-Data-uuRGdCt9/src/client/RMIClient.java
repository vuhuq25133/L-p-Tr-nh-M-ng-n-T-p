package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import RMI.DataService;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN372";
        String qCode = "uuRGdCt9";
        String serviceName = "RMIDataService";
        String host = "203.162.10.109";

        Registry registry = LocateRegistry.getRegistry(host, 1099);
        DataService stub = (DataService) registry.lookup(serviceName);

        Object response = stub.requestData(studentCode, qCode);
        String inputStr = response.toString().trim();
        System.out.println("Chuỗi dữ liệu nhận từ server: " + inputStr);

        List<Integer> numbers = parseStringToList(inputStr);
        System.out.println("Danh sách số: " + numbers);

        List<Integer> next = nextPermutation(numbers);
        String resultStr = listToString(next);

        System.out.println("Kết quả gửi lại: " + resultStr);
        stub.submitData(studentCode, qCode, resultStr);
        System.out.println("✅ Đã gửi kết quả về server!");
    }

    private static List<Integer> parseStringToList(String input) {
        List<Integer> list = new ArrayList<>();
        for (String s : input.split("[,\\s]+")) {
            if (!s.isEmpty()) list.add(Integer.parseInt(s.trim()));
        }
        return list;
    }

    private static List<Integer> nextPermutation(List<Integer> nums) {
        int n = nums.size();
        int i = n - 2;
        while (i >= 0 && nums.get(i) >= nums.get(i + 1)) i--;
        if (i >= 0) {
            int j = n - 1;
            while (nums.get(j) <= nums.get(i)) j--;
            Collections.swap(nums, i, j);
        }
        int left = i + 1, right = n - 1;
        while (left < right) {
            Collections.swap(nums, left, right);
            left++;
            right--;
        }
        return nums;
    }

    private static String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(",");
        }
        return sb.toString();
    }
}
