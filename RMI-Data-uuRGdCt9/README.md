# [Mã câu hỏi (qCode): uuRGdCt9].  
Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
Giao diện từ xa:
```java
public interface DataService extends Remote {
    public Object requestData(String studentCode, String qCode) throws RemoteException;
    public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
```
Trong đó:

•	Interface DataService được viết trong package RMI.
    
•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
    
## Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một chuỗi các số nguyên.

b. Sử dụng thuật toán sinh tổ hợp kế tiếp để tìm tổ hợp kế tiếp của chuỗi số này theo thứ tự từ điển. Nếu chuỗi đã là tổ hợp lớn nhất, trả về tổ hợp nhỏ nhất (sắp xếp lại từ đầu theo thứ tự từ điển).
Ví dụ: Với chuỗi 1, 2, 3 tổ hợp kế tiếp là 1, 3, 2. Nếu chuỗi là 3, 2, 1 (tổ hợp lớn nhất), kết quả sẽ là 1, 2, 3 (tổ hợp nhỏ nhất).

c. Triệu gọi phương thức submitData để gửi chuỗi chứa tổ hợp kế tiếp đã tìm được trở lại server.

d. Kết thúc chương trình client.
