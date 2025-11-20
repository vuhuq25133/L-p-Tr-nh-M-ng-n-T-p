# [Mã câu hỏi (qCode): 4ZGWVKhh].
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

Đối tượng trao đổi là thể hiện của lớp UDP.Employee được mô tả:
* Tên đầy đủ lớp: UDP.Employee
* Các thuộc tính: id (String), name (String), salary (double), hireDate (String)
* Hàm khởi tạo: `public Employee(String id, String name, double salary, String hireDate)`
* Trường dữ liệu: `private static final long serialVersionUID = 20261107L`

## Thực hiện:
1. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN006;ITleSdqV"

2. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Employee từ server. Trong đó, các thuộc tính id, name, salary và hireDate đã được thiết lập sẵn.

3. 
    1) Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
    2) Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm sinh.
    3) Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
    4) Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 
        08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.

4. Đóng socket và kết thúc chương trình.
