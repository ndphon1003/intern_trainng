# Foundation Training - Hệ thống Microservices E-commerce

## 📋 Tổng quan

Hệ thống là một nền tảng e-commerce hiện đại được xây dựng trên kiến trúc **Microservices** với Spring Cloud, sử dụng **Java 21** và **Spring Boot 3.3.5** (trừ Config Service dùng 3.5.13).

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────────────────────────────────┐
│         Client Applications                 │
└──────────────┬──────────────────────────────┘
               │
┌──────────────▼──────────────────────────────┐
│      API Gateway (WebFlux Reactive)         │
│  - Định tuyến yêu cầu                       │
│  - Cân bằng tải                             │
│  - Xác thực cơ bản                          │
└──────────────┬──────────────────────────────┘
               │
    ┌──────────┼──────────┬──────────┬───────────┐
    │          │          │          │           │
    ▼          ▼          ▼          ▼           ▼
┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌──────────┐
│  Auth  │ │ Product│ │  Cart  │ │ User   │ │  Config  │
│Service │ │Service │ │Service │ │Service │ │ Service  │
└────────┘ └────────┘ └────────┘ └────────┘ └──────────┘
    │          │          │          │           │
    └──────────┼──────────┼──────────┼───────────┘
               │
        ┌──────▼──────┐
        │  MongoDB 7  │
        │             │
        │ Database    │
        └─────────────┘
```

## 📦 Các microservices

### 1. **API Gateway** (Port: TBD)
- **Công nghệ**: Spring Boot WebFlux (Reactive)
- **Mục đích**: Điểm vào duy nhất cho tất cả client
- **Tính năng**: 
  - Định tuyến yêu cầu đến các service thích hợp
  - Cân bằng tải
  - Xác thực cơ bản
- **Phụ thuộc**: Config Service

### 2. **Auth Service** (Port: TBD)
- **Công nghệ**: Spring Boot Web
- **Mục đích**: Quản lý xác thực và ủy quyền
- **Tính năng**:
  - Đăng ký người dùng
  - Đăng nhập
  - Xác thực token
  - Quản lý phiên
- **Phụ thuộc**: Config Service, MongoDB

### 3. **User Service** (Port: TBD)
- **Công nghệ**: Spring Boot Web
- **Mục đích**: Quản lý thông tin người dùng
- **Tính năng**:
  - Tạo/cập nhật hồ sơ người dùng
  - Quản lý địa chỉ giao hàng
  - Lịch sử mua hàng
- **Phụ thuộc**: Config Service, MongoDB

### 4. **Product Service** (Port: TBD)
- **Công nghệ**: Spring Boot Web
- **Mục đích**: Quản lý sản phẩm
- **Tính năng**:
  - Danh sách sản phẩm
  - Chi tiết sản phẩm
  - Tìm kiếm và lọc
  - Quản lý hàng tồn kho
- **Phụ thuộc**: Config Service, MongoDB

### 5. **Cart Service** (Port: TBD)
- **Công nghệ**: Spring Boot Web
- **Mục đích**: Quản lý giỏ hàng
- **Tính năng**:
  - Thêm/xóa sản phẩm khỏi giỏ
  - Cập nhật số lượng
  - Tính toán giá tiền
  - Thanh toán
- **Phụ thuộc**: Config Service, Product Service, MongoDB

### 6. **Config Service** (Port: TBD)
- **Công nghệ**: Spring Boot 3.5.13 + Spring Cloud Config Server
- **Mục đích**: Quản lý cấu hình tập trung
- **Tính năng**:
  - Cung cấp cấu hình cho tất cả service
  - Reload động cấu hình (khi không khởi động lại)
  - Hỗ trợ nhiều profiles (dev, test, prod)
- **Phụ thuộc**: Git repository (foundation-config-repo)

## 💾 Dữ liệu

### Database: MongoDB 7
- **Container**: `mongodb_intern_training`
- **Port**: `27017`
- **Database**: `intern_training`
- **Storage**: Docker volume `mongo_data`

## 📂 Cấu trúc thư mục

```
foundation_training/
├── api-gateway/              # API Gateway service
├── auth-service/             # Auth service
├── cart-service/             # Cart service
├── config-service/           # Config server
├── product-service/          # Product service
├── user-service/             # User service
├── database/                 # Docker compose cho MongoDB
├── foundation-config-repo/   # Repository cấu hình tập trung
│   ├── application.properties          # Cấu hình mặc định
│   ├── api-gateway.properties
│   ├── auth-service.properties
│   ├── cart-service.properties
│   ├── config-service.properties
│   ├── product-service.properties
│   └── user-service.properties
└── README.md                 # Documentation
```

## 🚀 Công nghệ sử dụng

| Công nghệ | Phiên bản | Mục đích |
|-----------|----------|---------|
| Java | 21 | Ngôn ngữ lập trình |
| Spring Boot | 3.3.5 / 3.5.13 | Framework chính |
| Spring Cloud | 2025.0.2 | Microservices patterns |
| MongoDB | 7 | NoSQL Database |
| Maven | Latest | Build tool |
| Docker | Latest | Containerization |

## 🔧 Cấu hình

Tất cả service được cấu hình tập trung qua **Config Service**:
- Mỗi service có file `.properties` riêng trong `foundation-config-repo/`
- Config Service đọc từ Git repository và cấp phát cho các service
- Hỗ trợ hot reload (reload khi cấu hình thay đổi mà không cần khởi động lại)

## 🏃 Hướng dẫn khởi chạy

### 1. Khởi động Database
```bash
cd database
docker-compose up -d
```

### 2. Khởi động Config Service
```bash
cd config-service
mvn spring-boot:run
```

### 3. Khởi động các Microservices
```bash
# Mở terminal riêng cho mỗi service
cd api-gateway && mvn spring-boot:run
cd auth-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd cart-service && mvn spring-boot:run
```

## 📌 Quy ước & Best Practices

- **Naming**: Package `com.trainng` (lưu ý: có lỗi chính tả "trainng" thay vì "training")
- **Java Version**: Java 21 (Latest LTS)
- **Build Tool**: Maven
- **Communication**: REST API (HTTP)
- **Configuration**: Externalized config via Spring Cloud Config

## 🔄 Dòng tương tác (Workflows)

### 1. Đăng ký người dùng
```
Client → API Gateway → Auth Service → MongoDB
```

### 2. Xem sản phẩm
```
Client → API Gateway → Product Service → MongoDB
```

### 3. Thêm vào giỏ hàng
```
Client → API Gateway → Cart Service → Product Service
       ↓                             ↓
    Config Service          Config Service
                                 ↓
                             MongoDB
```

### 4. Thanh toán
```
Client → API Gateway → Cart Service → Auth Service (xác thực)
              ↓                         ↓
         Config Service          Config Service
                                       ↓
                                   MongoDB
```

## 📊 Trạng thái hiện tại

- ✅ Cấu trúc Microservices hoàn chỉnh
- ✅ Config Service tập trung
- ✅ MongoDB intergrated
- ✅ API Gateway reactive (WebFlux)
- ⏳ Các service cần implement chi tiết logic

## 📝 Lưu ý

- **Package naming issue**: Một số package dùng `trainng` thay vì `training` - cần kiểm tra
- **Spring Boot versions**: Config Service dùng 3.5.13, các service khác dùng 3.3.5 - cần thống nhất
- **Port configuration**: Cần xác định port cho mỗi service

---

**Cập nhật lần cuối**: April 23, 2026
