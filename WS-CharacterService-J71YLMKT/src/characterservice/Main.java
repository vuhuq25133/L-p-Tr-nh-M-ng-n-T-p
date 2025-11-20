package characterservice;

import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String studentCode = "B22DCCN372";
        String qCode = "J71YLMKT";

        // 1. Tạo service và lấy port
        CharacterService_Service service = new CharacterService_Service();
        CharacterService port = service.getCharacterServicePort();

        // 2. Gọi requestString để lấy chuỗi xử lý
        String input = port.requestString(studentCode, qCode);

        // 3. Xử lý chuỗi → PascalCase, camelCase, snake_case
        String pascal = toPascalCase(input);
        String camel = toCamelCase(input);
        String snake = toSnakeCase(input);

        // 4. Tạo mảng gửi lại server
        List<String> results = new ArrayList<>();
        results.add(pascal);
        results.add(camel);
        results.add(snake);

        // 5. Submit kết quả
        port.submitCharacterStringArray(studentCode, qCode, results);

        System.out.println("DONE");
    }

    // ===== Các hàm xử lý chuỗi =====
    private static List<String> splitWords(String s) {
        // Tách theo khoảng trắng hoặc gạch dưới
        String[] arr = s.trim().toLowerCase().split("[ _]+");
        List<String> words = new ArrayList<>();
        for (String w : arr) {
            words.add(w);
        }
        return words;
    }

    private static String toPascalCase(String s) {
        List<String> words = splitWords(s);
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
                    .append(w.substring(1));
        }
        return sb.toString();
    }

    private static String toCamelCase(String s) {
        List<String> words = splitWords(s);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            if (i == 0) {
                sb.append(w);
            } else {
                sb.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1));
            }
        }
        return sb.toString();
    }

    private static String toSnakeCase(String s) {
        return String.join("_", splitWords(s));
    }
}
