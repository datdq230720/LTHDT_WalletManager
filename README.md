# LTHDT_WalletManager
# 🏦 Wallet Manager Application (PTIT OOP Project)

![Java](https://img.shields.io/badge/Language-Java-orange)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![Status](https://img.shields.io/badge/Status-Completed-green)

Ứng dụng **Quản lý Ví điện tử** được xây dựng bằng ngôn ngữ lập trình Java, sử dụng thư viện Swing cho giao diện người dùng. Đây là đồ án môn **Lập trình hướng đối tượng (OOP)** tại Học viện Công nghệ Bưu chính Viễn thông (PTIT).

## 📌 Tổng quan dự án
Dự án mô phỏng một hệ thống ví điểm thưởng cho phép người dùng thực hiện các giao dịch an toàn thông qua xác thực OTP và mã hóa mật khẩu. Hệ thống được thiết kế theo mô hình phân tầng giúp quản lý mã nguồn một cách khoa học và dễ bảo trì.



## 🚀 Các tính năng chính

### 1. Đối với Người dùng (User)
* **Đăng nhập/Đăng ký:** Truy cập hệ thống thông qua định danh cá nhân.
* **Chuyển tiền:** Gửi điểm thưởng cho người dùng khác trong hệ thống một cách nhanh chóng.
* **Lịch sử giao dịch:** Xem chi tiết thời gian, người gửi/nhận và số tiền đã giao dịch.
* **Bảo mật:** Đổi mật khẩu hoặc xác thực chuyển tiền thông qua mã **OTP** (One-Time Password).

### 2. Đối với Quản trị viên (Admin)
* **Quản lý người dùng:** Xem danh sách, tạo mới người dùng (phí khởi tạo 10đ) hoặc xóa tài khoản.
* **Kiểm soát số dư:** Khi xóa người dùng, số dư sẽ được hoàn trả về tài khoản Admin.
* **Giám sát:** Theo dõi toàn bộ lịch sử giao dịch diễn ra trong hệ thống.

## 🛠 Công nghệ & Kỹ thuật sử dụng
* **Ngôn ngữ:** Java 8 hoặc cao hơn.
* **Giao diện:** Java Swing (JFrame, JButton, JTable, JOptionPane, JScrollPane).
* **Bảo mật:**
    * Thuật toán **MD5 Hash** để mã hóa mật khẩu trước khi lưu xuống file.
    * Cơ chế **OTP** ngẫu nhiên 6 chữ số để xác thực các tác vụ quan trọng.
* **Lưu trữ:** File I/O (Lưu trữ dữ liệu tĩnh vào các tệp tin `.txt` trong thư mục `data/`).

## 📂 Cấu trúc thư mục dự án
```text
project-root/
├── data/                       # Lưu trữ tệp tin dữ liệu (.txt)
│   ├── history.txt             # Nhật ký giao dịch toàn hệ thống
│   └── users.txt               # Danh sách tài khoản và số dư
├── src/main/java/ptit/walletmanager/
│   ├── model/                  # Các lớp thực thể (User, Transaction)
│   ├── service/                # Xử lý logic nghiệp vụ, File I/O, OTP, Hash
│   ├── ui/                     # Các lớp giao diện Swing (Admin, Dashboard, Login,...)
│   └── WalletManager.java      # Lớp khởi chạy ứng dụng (Main)
└── README.md                   # Tài liệu hướng dẫn


## 👥 Thành viên thực hiện (Nhóm 05)

Dưới đây là danh sách thành viên và phân công nhiệm vụ chi tiết trong dự án đồ án môn Lập trình hướng đối tượng (OOP):

| STT | Họ và tên | MSSV | Vai trò | Nhiệm vụ chi tiết |
| :--- | :--- | :--- | :--- | :--- |
| 1 | **Lê Vũ Huy Trung** | B24DTCN024 | **Trưởng nhóm** | Thiết kế kiến trúc hệ thống, quản lý mã nguồn, viết logic `WalletService`. |
| 2 | **Đinh Quốc Đạt** | B24DTCN020 | **Thành viên** | Xử lý lưu trữ dữ liệu (`FileService`), thiết kế các lớp Model (`User`, `Transaction`). |
| 3 | **Phan Thanh Minh Châu** | B24DTCN019 | **Thành viên** | Thiết kế giao diện đồ họa (UI) cho `AdminFrame` và `UserDashboard`. |
| 4 | **Nguyễn Thế Quang** | B24DTCN022 | **Thành viên** | Triển khai bảo mật: Hash mật khẩu (MD5) và cơ chế xác thực OTP. |
| 5 | **Trần Huy Linh** | K24DTCN298 | **Thành viên** | Kiểm thử hệ thống (Unit Test), viết báo cáo đồ án và hoàn thiện tài liệu. |

---

## ⚙️ Hướng dẫn cài đặt và Khởi chạy

Để chạy ứng dụng **Wallet Manager** trên máy tính cá nhân, vui lòng thực hiện theo các bước sau:

### 1. Yêu cầu hệ thống
* **Java Development Kit (JDK):** Phiên bản 8.0 trở lên.
* **IDE:** NetBeans, IntelliJ IDEA, hoặc Eclipse.
* **Thư viện:** Không cần thư viện ngoài (Sử dụng chuẩn Java Swing/AWT).

### 2. Các bước cài đặt
1. **Tải mã nguồn:** Tải file nén (.zip) hoặc Clone repository này về máy tính.
2. **Mở Project:** Khởi động IDE của bạn và chọn `Open Project`, trỏ đến thư mục chứa mã nguồn.
3. **Kiểm tra dữ liệu:** Đảm bảo thư mục `data/` nằm ở thư mục gốc của dự án và chứa các file `users.txt`, `history.txt`. Nếu chưa có, hãy tạo thư mục và file trống.

### 3. Khởi chạy ứng dụng
1. Tìm đến file `WalletManager.java` (thường nằm trong package `ptit.walletmanager`).
2. Chuột phải vào file và chọn **Run File** (hoặc nhấn `Shift + F6` trong NetBeans).
3. Sau khi giao diện đăng nhập hiện lên, bạn có thể sử dụng tài khoản Admin mặc định:
   * **Username:** `admin`
   * **Password:** `123`

### 4. Lưu ý khi sử dụng
* Mật khẩu sau khi đăng ký sẽ được tự động Hash mã hóa, bạn không thể xem mật khẩu gốc trong file `users.txt`.
* Khi thực hiện chuyển tiền hoặc đổi mật khẩu, hãy kiểm tra **Console** (cửa sổ đầu ra của IDE) để lấy mã **OTP** giả lập.

---
*© 2026 - Nhóm 05 - Học viện Công nghệ Bưu chính Viễn thông (PTIT)*
