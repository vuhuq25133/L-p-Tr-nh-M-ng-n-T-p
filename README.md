# 🧭 Luồng Thực Hiện Web Service (SOAP) – Lập Trình Mạng PTIT

* **DataService**
* **CharacterService**
* **ObjectService**
* **ByteService**
---

## **1. Tạo Project & Cấu Hình JDK**

* Tạo Java Application trong NetBeans.
* Trong `Project Properties`:

  * **Sources → Source/Binary Format = JDK 8**
  * **Compile → Java Platform = JDK 1.8**
  * **Run → Java Platform = JDK 1.8**

---

## **2. Thêm Web Service Client từ WSDL**
```
Project → New → Web Service Client
```
Nhập URL dạng:
```
http://<Exam_IP>:8080/JNPWS/<ServiceName>?wsdl
```
NetBeans sẽ sinh ra stub tại:
```
build/generated-sources/jax-ws/vn/medianews/
```
**Lưu ý:** Không copy folder này vào `src/`.

---

## **3. Tạo File Main.java để gọi Web Service**

* Đặt trong thư mục `src/<package>/Main.java`
* Không đặt tên file trùng với service.

### **Khởi tạo service & port:**

```java
<ServiceName>_Service service = new <ServiceName>_Service();
<ServiceName> port = service.get<ServiceName>Port();
```
---
## **4. Gọi phương thức request...() để lấy dữ liệu từ server**
Mỗi bài sẽ dùng 1 trong những hàm sau:
* `requestString(studentCode, qCode)`
* `getData(studentCode, qCode)`
* `requestCharacter(studentCode, qCode)`
* `requestListStudent(studentCode, qCode)`
* `requestData(studentCode, qCode)` (byte[])
Dữ liệu trả về tùy service:
* `String`
* `List<Integer>`
* `List<Student>`
* `byte[]`
---
## **5. Xử Lý Dữ Liệu Theo Yêu Cầu Đề Bài**

### Ví dụ:
* **DataService:** đổi hệ 10 → hệ 8 & hệ 16
* **CharacterService:** PascalCase, camelCase, snake_case
* **ObjectService:** lọc sinh viên nhóm A và D
* **ByteService:** mã hóa/giải mã chuỗi từ byte[]
Bạn chỉ xử lý tại client; không thay đổi service.
---
## **6. Submit kết quả về server**
Mỗi service có một hàm submit riêng:
* `submitDataStringArray(...)`
* `submitCharacterStringArray(...)`
* `submitListStudent(...)`
* `submitData(...)`
Ví dụ:
```java
port.submitCharacterStringArray(studentCode, qCode, results);
```
---
## **7. Kết thúc chương trình**
```java
System.out.println("DONE");
```

