# [Mã câu hỏi (qCode): oIH0bdOP].
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B21DCCN795;ylrhZ6UM".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n;k;z1,z2,...,zn", trong đó:
    requestId là chuỗi ngẫu nhiên duy nhất.
    n là số phần tử của mảng.
    k là kích thước cửa sổ trượt (k < n).
    z1 đến zn là n phần tử là số nguyên của mảng.
c. Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với kích thước k trên mảng số nguyên nhận được, và gửi thông điệp lên server theo định dạng "requestId;max1,max2,...,maxm", trong đó max1 đến maxm là các giá trị lớn nhất tương ứng trong mỗi cửa sổ.
Ví dụ: "requestId;5;3;1,5,2,3,4"
Kết quả: "requestId;5,5,4"
d. Đóng socket và kết thúc chương trình.