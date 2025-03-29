document.addEventListener("DOMContentLoaded", function () {
    // DOM Elements
    const authButtons = document.getElementById("auth-buttons");
    const userInfo = document.getElementById("user-info");
    const logoutBtn = document.getElementById("logout-btn");
    const dropdownToggle = document.getElementById("dropdown-toggle");
    const dropdownContent = document.getElementById("dropdown-content");
    const dropdownArrow = document.querySelector(".dropdown-arrow");
    const loginForm = document.getElementById("loginForm");
    const currentPage = window.location.pathname.split("/").pop();

    // 1. Kiểm tra trạng thái đăng nhập
    function checkAuthStatus() {
        fetch("/auth/status")
            .then(response => response.json())
            .then(data => {
                if (data.status === "logged_in") {
                    localStorage.setItem("email", data.email);
                    authButtons.classList.add("hidden");
                    userInfo.classList.remove("hidden");

                    // Cập nhật thông tin user
                    const userName = document.querySelector(".user-name");
                    const userEmail = document.querySelector(".user-email");

                    userName.textContent = data.name || (data.role === "admin" ? "Admin User" : "User");
                    userEmail.textContent = data.email;

                    // Highlight active nav link
                    highlightActiveNavLink();
                } else {
                    localStorage.removeItem("email");
                    authButtons.classList.remove("hidden");
                    userInfo.classList.add("hidden");
                }
            })
            .catch(error => console.error("Error checking auth status:", error));
    }

    // 2. Highlight active navigation link
    function highlightActiveNavLink() {
        const links = document.querySelectorAll(".nav-menu a");
        links.forEach(link => {
            const linkPath = link.getAttribute("href").split("/").pop();
            if (linkPath === currentPage) {
                link.classList.add("active");
            }
        });
    }

    // 3. Xử lý dropdown khi click
    if (dropdownToggle && dropdownContent) {
        dropdownToggle.addEventListener("click", function(e) {
            e.stopPropagation();
            dropdownContent.classList.toggle("show");
            dropdownArrow.classList.toggle("arrow-rotate");
        });

        // Đóng dropdown khi click ra ngoài
        document.addEventListener("click", function() {
            dropdownContent.classList.remove("show");
            dropdownArrow.classList.remove("arrow-rotate");
        });

        // Ngăn dropdown đóng khi click vào nội dung dropdown
        dropdownContent.addEventListener("click", function(e) {
            e.stopPropagation();
        });
    }

    // 4. Xử lý đăng nhập
    if (loginForm) {
        loginForm.addEventListener("submit", function(e) {
            e.preventDefault();

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            fetch("/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        localStorage.setItem("email", data.email);
                        alert("Đăng nhập thành công!");
                        window.location.href = data.redirectUrl || "/home.html";
                    } else {
                        alert(data.message || "Sai tài khoản hoặc mật khẩu!");
                    }
                })
                .catch(error => {
                    console.error("Login error:", error);
                    alert("Đã xảy ra lỗi khi đăng nhập!");
                });
        });
    }

    // 5. Xử lý đăng xuất
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function(e) {
            e.preventDefault();

            fetch("/auth/logout", { method: "POST" })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        localStorage.removeItem("email");
                        alert("Đăng xuất thành công!");
                        window.location.href = "/home.html";
                    }
                })
                .catch(error => {
                    console.error("Logout error:", error);
                    alert("Đã xảy ra lỗi khi đăng xuất!");
                });
        });
    }

    // 6. Điền tài khoản demo
    window.fillDemoAccount = function(role) {
        const demoAccounts = {
            owner: { email: "owner@example.com", password: "123456" },
            manager: { email: "manager1@example.com", password: "123456" },
            receptionist: { email: "receptionist1@example.com", password: "123456" },
            artist: { email: "artist1@example.com", password: "123456" },
            customer: { email: "customer1@example.com", password: "123456" },
            admin: { email: "admin@example.com", password: "123456" }
        };

        if (demoAccounts[role]) {
            document.getElementById("email").value = demoAccounts[role].email;
            document.getElementById("password").value = demoAccounts[role].password;
        }
    };

    // 7. Xử lý nút bấm demo
    document.querySelectorAll(".demo-buttons button").forEach(button => {
        button.addEventListener("click", function() {
            const role = this.getAttribute("data-role");
            fillDemoAccount(role);
        });
    });

    // Khởi chạy khi tải trang
    checkAuthStatus();
    highlightActiveNavLink();
});

// 8. Xử lý đăng ký
async function register() {
    const nameAccount = document.getElementById("nameAccount").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const message = document.getElementById("message");

    // Kiểm tra mật khẩu
    if (password !== confirmPassword) {
        message.textContent = "Mật khẩu không khớp!";
        message.style.color = "red";
        return;
    }

    // Kiểm tra định dạng email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        message.textContent = "Email không hợp lệ!";
        message.style.color = "red";
        return;
    }

    try {
        const response = await fetch("/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nameAccount, email, password })
        });

        const result = await response.json();

        if (response.ok) {
            alert("🎉 Đăng ký thành công! Vui lòng đăng nhập.");
            window.location.href = "/login.html";
        } else {
            message.textContent = result.message || "Đăng ký thất bại!";
            message.style.color = "red";
        }
    } catch (error) {
        message.textContent = "Lỗi kết nối đến server!";
        message.style.color = "red";
        console.error("Registration error:", error);
    }
}