package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN372";
        String qCode = "xgLPytb3";
        String host = "203.162.10.109";
        int port = 2206;

        Socket socket = new Socket(host, port);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // ---- Gửi mã sinh viên & qCode ----
        String sendMsg = studentCode + ";" + qCode + "\n";
        out.write(sendMsg.getBytes(StandardCharsets.UTF_8));
        out.flush();

        // ---- Nhận dữ liệu ----
        byte[] buffer = new byte[8192];
        int bytesRead = in.read(buffer);
        String received = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
        System.out.println("Input: " + received);

        // ---- Xử lý LIS ----
        String[] parts = received.split(",");
        int n = parts.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(parts[i].trim());

        int[] dp = new int[n];
        int[] prev = new int[n];
        int maxLen = 0, lastIndex = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // ---- Truy vết chuỗi tăng dần ----
        List<Integer> seq = new ArrayList<>();
        for (int i = lastIndex; i != -1; i = prev[i]) {
            seq.add(0, arr[i]);
        }

        // ---- Chuẩn bị kết quả ----
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seq.size(); i++) {
            sb.append(seq.get(i));
            if (i < seq.size() - 1) sb.append(",");
        }
        sb.append(";").append(maxLen);

        String result = sb.toString();
        System.out.println("Output: " + result);

        // ---- Gửi lại kết quả ----
        out.write(result.getBytes(StandardCharsets.UTF_8));
        out.flush();

        in.close();
        out.close();
        socket.close();
        System.out.println("✅ Đã gửi kết quả về server!");
    }
}
