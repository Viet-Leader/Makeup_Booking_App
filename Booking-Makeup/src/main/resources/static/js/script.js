//start Login
document.addEventListener("DOMContentLoaded", function () {
    const authButtons = document.getElementById("auth-buttons");
    const userInfo = document.getElementById("user-info");
    const usernameSpan = document.querySelector(".email");
    const logoutBtn = document.getElementById("logout-btn");

    // ✅ Kiểm tra trạng thái đăng nhập từ API
    fetch("/auth/status")
        .then(response => response.json())
        .then(data => {
            if (data.status === "logged_in") {
                localStorage.setItem("email", data.email);
                authButtons.classList.add("hidden");
                userInfo.classList.remove("hidden");
                usernameSpan.textContent = `Xin chào, ${data.email}`;
            } else {
                localStorage.removeItem("email");
                authButtons.classList.remove("hidden");
                userInfo.classList.add("hidden");
            }
        });
    // --- THÊM SCRIPT ĐỂ TẠO GẠCH CHÂN MÀU VÀNG DƯỚI TRANG HIỆN TẠI ---
    document.addEventListener("DOMContentLoaded", function () {
        const links = document.querySelectorAll(".nav-link");
        const currentPage = window.location.pathname.split("/").pop(); // Lấy tên file hiện tại

        links.forEach(link => {
            if (link.getAttribute("href").endsWith(currentPage)) {
                link.classList.add("active"); // Thêm class "active" vào trang hiện tại
            }
        });
    });

    // ✅ Xử lý khi đăng nhập
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            fetch("/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: new URLSearchParams({ email, password })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        localStorage.setItem("email", data.email);
                        alert("Đăng nhập thành công!");
                        window.location.href="/home.html";
                    } else {
                        alert("Sai tài khoản hoặc mật khẩu!");
                    }
                })
                .catch(error => console.error("Lỗi:", error));
        });
    }

    // ✅ Xử lý khi nhấn nút "Đăng xuất"
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            fetch("/auth/logout", { method: "POST" })
                .then(response => response.json())
                .then(() => {
                    localStorage.removeItem("email");
                    alert("Đăng xuất thành công!");
                    window.location.reload();
                })
                .catch(error => console.error("Lỗi:", error));
        });
    }

    // ✅ Điền tài khoản demo vào ô đăng nhập
    window.fillDemoAccount = function (role) {
        const demoAccounts = {
            owner: { email: "owner@example.com", password: "123456" },
            manager: { email: "manager1@example.com", password: "123456" },
            receptionist: { email: "receptionist1@example.com", password: "123456" },
            artist: { email: "arartist1@example.com", password: "123456" },
            customer: { email: "customer1@example.com", password: "123456" }
        };

        if (demoAccounts[role]) {
            document.getElementById("email").value = demoAccounts[role].email;
            document.getElementById("password").value = demoAccounts[role].password;
        }
    };

    // ✅ Xử lý nút bấm demo
    document.querySelectorAll(".demo-buttons button").forEach(button => {
        button.addEventListener("click", function () {
            const role = this.getAttribute("data-role");
            fillDemoAccount(role);
        });
    });
});
//End Login
//Star Register
async function register() {
    const nameAccount = document.getElementById("nameAccount").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm-password").value;
    const message = document.getElementById("message");

    // ✅ Kiểm tra định dạng email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        message.textContent = "Email không hợp lệ! Vui lòng nhập đúng định dạng email.";
        message.style.color = "red";
        return;
    }

    if (password !== confirmPassword) {
        message.textContent = "Mật khẩu không khớp!";
        message.style.color = "red";
        return;
    }

    const account = { nameAccount, email, password };

    try {
        const response = await fetch("/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(account),
        });

        const result = await response.json();

        if (response.ok) {
            alert("🎉 Đăng ký thành công! Vui lòng đăng nhập.");
            window.location.href = "login.html"; // Chuyển hướng đến trang login
        } else {
            message.textContent = result.message;
            message.style.color = "red";
        }
    } catch (error) {
        message.textContent = "Lỗi kết nối đến server!";
        message.style.color = "red";
    }
}
//EndRegister