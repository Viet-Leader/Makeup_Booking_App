document.addEventListener("DOMContentLoaded", function () {
    const authButtons = document.getElementById("auth-buttons");
    const userInfo = document.getElementById("user-info");
    const usernameSpan = document.querySelector(".email");
    const logoutBtn = document.getElementById("logout-btn");

    // ✅ Kiểm tra trạng thái đăng nhập từ session
    fetch("/auth/status")
        .then(response => response.json())
        .then(data => {
            if (data.status === "logged_in") {
                sessionStorage.setItem("email", data.email); // Lưu vào sessionStorage
                authButtons.classList.add("hidden");
                userInfo.classList.remove("hidden");
                usernameSpan.textContent = `Xin chào, ${data.email}`;
            } else {
                sessionStorage.removeItem("email");
                authButtons.classList.remove("hidden");
                userInfo.classList.add("hidden");
            }
        });

    // ✅ Lưu trang hiện tại trước khi đăng nhập
    const loginNavLinks = document.querySelectorAll(".login-link");
    loginNavLinks.forEach(link => {
        link.addEventListener("click", function () {
            sessionStorage.setItem("redirectAfterLogin", window.location.pathname);
        });
    });

    // ✅ Xử lý đăng nhập
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
                        sessionStorage.setItem("email", data.email);
                        alert("Đăng nhập thành công!");

                        // ✅ Chuyển về trang trước đó
                        const redirectUrl = sessionStorage.getItem("redirectAfterLogin") || "/home.html";
                        sessionStorage.removeItem("redirectAfterLogin");

                        window.location.replace(redirectUrl);
                    } else {
                        alert("Sai tài khoản hoặc mật khẩu!");
                    }
                })
                .catch(error => console.error("Lỗi:", error));
        });
    }

    // ✅ Chặn truy cập trang đặt lịch nếu chưa đăng nhập
    if (window.location.pathname === "/schedule.html") {
        const email = sessionStorage.getItem("email");
        if (!email) {
            sessionStorage.setItem("redirectAfterLogin", "/schedule.html");
            window.location.href = "/login.html";
        }
    }

    // ✅ Xử lý đăng xuất
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            fetch("/auth/logout", { method: "POST" })
                .then(response => response.json())
                .then(() => {
                    sessionStorage.removeItem("email");
                    alert("Đăng xuất thành công!");
                    window.location.reload();
                })
                .catch(error => console.error("Lỗi:", error));
        });
    }

    // ✅ Tài khoản demo
    window.fillDemoAccount = function (role) {
        const demoAccounts = {
            owner: { email: "owner@example.com", password: "123456" },
            manager: { email: "manager1@example.com", password: "123456" },
            receptionist: { email: "receptionist1@example.com", password: "123456" },
            artist: { email: "artist1@example.com", password: "123456" },
            customer: { email: "customer1@example.com", password: "123456" }
        };

        if (demoAccounts[role]) {
            document.getElementById("email").value = demoAccounts[role].email;
            document.getElementById("password").value = demoAccounts[role].password;
        }
    };

    // ✅ Xử lý nút demo
    document.querySelectorAll(".demo-buttons button").forEach(button => {
        button.addEventListener("click", function () {
            const role = this.getAttribute("data-role");
            fillDemoAccount(role);
        });
    });
});
