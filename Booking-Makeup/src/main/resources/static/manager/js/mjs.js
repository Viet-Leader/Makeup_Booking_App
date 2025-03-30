document.addEventListener("DOMContentLoaded", function() {
    // Kích hoạt Flatpickr
    flatpickr("#datepicker", {
        inline: true,
        dateFormat: "d/m/Y",
        defaultDate: "today",
        locale: "vn",
        onChange: function(selectedDates, dateStr, instance) {
            console.log("Ngày được chọn:", dateStr);
            filterAppointmentsByDate(dateStr);
        }
    });

    // Xử lý khi nhấn vào tab "Sắp tới" hoặc "Tất cả"
    document.querySelectorAll(".tabs button").forEach(button => {
        button.addEventListener("click", function() {
            document.querySelectorAll(".tabs button").forEach(btn => btn.classList.remove("active"));
            this.classList.add("active");

            const filterType = this.innerText.trim();
            console.log("Lọc theo:", filterType);
            filterAppointments(filterType);
        });
    });

    // Xử lý khi thay đổi bộ lọc trạng thái
    document.querySelector("#status").addEventListener("change", function() {
        const selectedStatus = this.value;
        console.log("Trạng thái được chọn:", selectedStatus);
        filterAppointmentsByStatus(selectedStatus);
    });

    // Lấy modal và nút đóng
    const modal = document.getElementById("appointmentDetailModal");
    const closeModal = document.querySelector(".close");

    // Lấy các nút xác nhận, hủy, hoàn thành
    const confirmBtn = document.getElementById("confirmBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const completeBtn = document.getElementById("completeBtn");

    // Xử lý khi nhấn vào nút chi tiết
    document.querySelectorAll(".detail").forEach(button => {
        button.addEventListener("click", function() {
            const row = this.closest("tr");
            const dateTime = row.cells[0].innerText;
            const branch = row.cells[1].innerText;
            const service = row.cells[3].innerText;
            const status = row.cells[4].innerText.trim(); // Lấy trạng thái

            // Hiển thị thông tin trong modal
            document.getElementById("appointmentDateTime").innerText = dateTime;
            document.getElementById("appointmentBranch").innerText = branch;
            document.getElementById("appointmentService").innerText = service;
            document.getElementById("appointmentStaff").innerText = "Chưa cập nhật";
            document.getElementById("appointmentPrice").innerText = "300.000";
            document.getElementById("appointmentStatus").innerText = status;

            // Ẩn tất cả các nút trước khi kiểm tra trạng thái
            confirmBtn.classList.add("hidden");
            cancelBtn.classList.add("hidden");
            completeBtn.classList.add("hidden");

            // Kiểm tra trạng thái và hiển thị nút phù hợp
            if (status === "Chờ xác nhận") {
                confirmBtn.classList.remove("hidden");
                cancelBtn.classList.remove("hidden");
            } else if (status === "Đã xác nhận") {
                completeBtn.classList.remove("hidden");
                cancelBtn.classList.remove("hidden");
            }

            modal.classList.remove("hidden"); // Hiển thị modal
        });
    });

    // Đóng modal khi bấm vào nút đóng
    closeModal.addEventListener("click", function() {
        modal.classList.add("hidden");
    });

    // Đóng modal khi nhấn vào overlay
    modal.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.classList.add("hidden");
        }
    });

    // Xử lý nút xác nhận
    confirmBtn.addEventListener("click", function() {
        alert("Đã xác nhận lịch hẹn!");
        modal.classList.add("hidden");
    });

    // Xử lý nút hủy
    cancelBtn.addEventListener("click", function() {
        alert("Lịch hẹn đã bị hủy!");
        modal.classList.add("hidden");
    });

    // Xử lý nút hoàn thành
    completeBtn.addEventListener("click", function() {
        alert("Lịch hẹn đã hoàn thành!");
        modal.classList.add("hidden");
    });

    // Hàm lọc lịch hẹn theo tab
    function filterAppointments(filterType) {
        const rows = document.querySelectorAll("tbody tr");
        const today = new Date();
        today.setHours(0, 0, 0, 0); // Đặt giờ về 00:00:00.000

        rows.forEach(row => {
            const rowDate = new Date(row.dataset.date);
            if (filterType === "Sắp tới") {
                if (rowDate >= today) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            } else {
                row.style.display = ""; // "Tất cả" hiển thị tất cả
            }
        });
    }

    // Hàm lọc lịch hẹn theo ngày được chọn trên lịch
    function filterAppointmentsByDate(selectedDateStr) {
        const rows = document.querySelectorAll("tbody tr");
        const selectedDate = parseDate(selectedDateStr); // Chuyển đổi sang đối tượng Date

        rows.forEach(row => {
            const rowDate = parseDate(row.dataset.date.split("-").reverse().join("/")); // Chuyển đổi sang đối tượng Date
            if (rowDate.getTime() === selectedDate.getTime()) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }

    // Hàm chuyển đổi chuỗi ngày sang đối tượng Date
    function parseDate(dateStr) {
        const parts = dateStr.split("/");
        return new Date(parts[2], parts[1] - 1, parts[0]);
    }
    // Hàm lọc lịch hẹn theo trạng thái
    function filterAppointmentsByStatus(status) {
        const rows = document.querySelectorAll("tbody tr");

        rows.forEach(row => {
            const rowStatus = row.querySelector(".status").innerText.trim().toLowerCase(); // Lấy trạng thái từ cột trạng thái

            // Chuyển đổi trạng thái hiển thị sang giá trị tương ứng
            let normalizedStatus = "";
            if (rowStatus === "chờ xác nhận") {
                normalizedStatus = "pending";
            } else if (rowStatus === "đã xác nhận") {
                normalizedStatus = "confirmed";
            } else if (rowStatus === "hoàn thành") {
                normalizedStatus = "completed";
            } else if (rowStatus === "đã hủy") {
                normalizedStatus = "cancelled";
            }

            if (status === "all" || normalizedStatus === status) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }
});