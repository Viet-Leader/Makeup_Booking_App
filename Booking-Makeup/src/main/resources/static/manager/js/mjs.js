document.addEventListener("DOMContentLoaded", function() {
    // Khai báo biến
    const modal = document.getElementById("appointmentDetailModal");
    const closeModal = document.querySelector(".close");
    const confirmBtn = document.getElementById("confirmBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const completeBtn = document.getElementById("completeBtn");
    let selectedAppointmentId = null;

    // Kích hoạt Flatpickr cho lịch
    flatpickr("#datepicker", {
        inline: true,
        dateFormat: "d/m/Y",
        defaultDate: "today",
        locale: "vn",
        onChange: function(selectedDates, dateStr) {
            console.log("Ngày được chọn:", dateStr);
            filterAppointmentsByDate(dateStr);
        }
    });

    // Xử lý tab "Sắp tới" và "Tất cả"
    document.querySelectorAll(".tabs button").forEach(button => {
        button.addEventListener("click", function() {
            document.querySelectorAll(".tabs button").forEach(btn => btn.classList.remove("active"));
            this.classList.add("active");

            const filterType = this.innerText.trim();
            console.log("Lọc theo:", filterType);
            filterAppointments(filterType);
        });
    });

    // Xử lý bộ lọc trạng thái
    document.querySelector("#status").addEventListener("change", function() {
        const selectedStatus = this.value;
        console.log("Trạng thái được chọn:", selectedStatus);
        filterAppointmentsByStatus(selectedStatus);
    });

    // Hàm ánh xạ trạng thái từ tiếng Việt về tiếng Anh (database)
    function mapVietnameseToDbStatus(vietnameseStatus) {
        switch (vietnameseStatus) {
            case "Chờ xác nhận": return "pending";
            case "Đã xác nhận": return "confirmed";
            case "Hoàn thành": return "completed";
            case "Đã hủy": return "cancelled";
            default: return vietnameseStatus; // Giữ nguyên nếu không khớp
        }
    }

    // Xử lý nút "Chi tiết"
    document.querySelectorAll(".detail").forEach(button => {
        button.addEventListener("click", function() {
            const row = this.closest("tr");
            selectedAppointmentId = this.dataset.id;

            const dateTime = row.cells[0].innerText;
            const branch = row.cells[1].innerText;
            const service = row.cells[3].innerText;
            const vietnameseStatus = row.cells[4].innerText.trim(); // Trạng thái hiển thị tiếng Việt
            const dbStatus = mapVietnameseToDbStatus(vietnameseStatus); // Trạng thái gốc trong DB

            // Lấy dữ liệu từ data-* attributes
            const makeupartist = this.dataset.makeupartist; // Tên chuyên viên
            const servicePrice = parseFloat(this.dataset.servicePrice) || 0; // Giá dịch vụ
            const makeupartistPrice = parseFloat(this.dataset.makeupartistPrice) || 0; // Giá chuyên viên
            const totalPrice = servicePrice + makeupartistPrice; // Tổng giá

            // Gán dữ liệu vào modal
            document.getElementById("appointmentDateTime").innerText = dateTime;
            document.getElementById("appointmentBranch").innerText = branch;
            document.getElementById("appointmentService").innerText = service;
            document.getElementById("appointmentStaff").innerText = makeupartist; // Hiển thị chuyên viên
            document.getElementById("appointmentPrice").innerText = totalPrice.toLocaleString('vi-VN'); // Hiển thị tổng giá
            document.getElementById("appointmentStatus").innerText = vietnameseStatus;

            // Ẩn tất cả nút trước khi kiểm tra trạng thái
            confirmBtn.classList.add("hidden");
            cancelBtn.classList.add("hidden");
            completeBtn.classList.add("hidden");

            // Hiển thị nút dựa trên trạng thái gốc (dbStatus)
            if (dbStatus === "pending") {
                confirmBtn.classList.remove("hidden"); // Nút "Xác nhận"
                cancelBtn.classList.remove("hidden");  // Nút "Hủy"
            } else if (dbStatus === "confirmed") {
                completeBtn.classList.remove("hidden"); // Nút "Hoàn thành"
                cancelBtn.classList.remove("hidden");   // Nút "Hủy"
            }

            modal.classList.remove("hidden");
        });
    });

    // Đóng modal
    closeModal.addEventListener("click", function() {
        modal.classList.add("hidden");
    });

    modal.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.classList.add("hidden");
        }
    });

    // Hàm lọc theo ngày
    function filterAppointmentsByDate(dateStr) {
        const rows = document.querySelectorAll("tbody tr");
        const selectedDate = new Date(dateStr.split("/").reverse().join("-"));

        rows.forEach(row => {
            const dateTimeText = row.cells[0].innerText; // "dd/MM/yyyy - HH:mm"
            const rowDateStr = dateTimeText.split(" - ")[0];
            const rowDate = new Date(rowDateStr.split("/").reverse().join("-"));

            if (rowDate.toDateString() === selectedDate.toDateString()) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }

    // Hàm lọc "Sắp tới" và "Tất cả"
    function filterAppointments(filterType) {
        const rows = document.querySelectorAll("tbody tr");
        const now = new Date();

        rows.forEach(row => {
            const dateTimeText = row.cells[0].innerText; // "dd/MM/yyyy - HH:mm"
            const rowDateStr = dateTimeText.split(" - ")[0];
            const rowTimeStr = dateTimeText.split(" - ")[1];
            const rowDateTime = new Date(rowDateStr.split("/").reverse().join("-") + "T" + rowTimeStr);

            if (filterType === "Sắp tới") {
                row.style.display = rowDateTime > now ? "" : "none";
            } else if (filterType === "Tất cả") {
                row.style.display = "";
            }
        });
    }

    // Hàm lọc theo trạng thái
    function filterAppointmentsByStatus(selectedStatus) {
        const rows = document.querySelectorAll("tbody tr");
        rows.forEach(row => {
            const vietnameseStatus = row.cells[4].innerText.trim();
            const dbStatus = mapVietnameseToDbStatus(vietnameseStatus);
            if (selectedStatus === "all" || dbStatus === selectedStatus) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }

    // Hàm cập nhật trạng thái
    function updateAppointmentStatus(newStatus) {
        if (!selectedAppointmentId) {
            alert("Không tìm thấy cuộc hẹn!");
            return;
        }

        fetch("/manager/update-status", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `appointmentId=${selectedAppointmentId}&status=${newStatus}`
        })
            .then(response => response.text())
            .then(message => {
                alert(message);
                location.reload();
            })
            .catch(error => console.error("Lỗi cập nhật trạng thái:", error));
    }

    // Gắn sự kiện cho các nút trong modal
    confirmBtn.addEventListener("click", function() {
        updateAppointmentStatus("confirmed"); // Chuyển sang "Đã xác nhận"
    });

    cancelBtn.addEventListener("click", function() {
        updateAppointmentStatus("cancelled"); // Chuyển sang "Đã hủy"
    });

    completeBtn.addEventListener("click", function() {
        updateAppointmentStatus("completed"); // Chuyển sang "Hoàn thành"
    });
});