package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;
import RMI.CharacterService;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN372";
        String qCode = "jlsJxcrN";
        String host = "203.162.10.109";
        String serviceName = "RMICharacterService";

        // ====== B1: Kết nối Registry ======
        Registry registry = LocateRegistry.getRegistry(host, 1099);

        // ====== B2: Lấy stub từ server ======
        CharacterService stub = (CharacterService) registry.lookup(serviceName);

        // ====== B3: Nhận chuỗi từ server ======
        String input = stub.requestCharacter(studentCode, qCode);
        System.out.println("Chuỗi nhận từ server: " + input);

        // ====== B4: Xử lý đếm tần số ký tự ======
        String result = countFrequency(input);
        System.out.println("Chuỗi sau khi xử lý: " + result);

        // ====== B5: Gửi kết quả về server ======
        stub.submitCharacter(studentCode, qCode, result);
        System.out.println("✅ Đã gửi kết quả về server!");
    }

    // ====== Hàm đếm tần số ký tự (giữ thứ tự xuất hiện đầu tiên) ======
    private static String countFrequency(String str) {
        if (str == null || str.isEmpty()) return "";
        Map<Character, Integer> freq = new LinkedHashMap<>();

        for (char c : str.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            sb.append(e.getKey()).append(e.getValue());
        }
        return sb.toString();
    }
}
