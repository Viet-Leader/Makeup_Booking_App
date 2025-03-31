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

    // Xử lý nút "Chi tiết"
    document.querySelectorAll(".detail").forEach(button => {
        button.addEventListener("click", function() {
            const row = this.closest("tr");
            selectedAppointmentId = this.dataset.id; // Lấy ID từ data-id

            const dateTime = row.cells[0].innerText;
            const branch = row.cells[1].innerText;
            const service = row.cells[3].innerText;
            const status = row.cells[4].innerText.trim();

            document.getElementById("appointmentDateTime").innerText = dateTime;
            document.getElementById("appointmentBranch").innerText = branch;
            document.getElementById("appointmentService").innerText = service;
            document.getElementById("appointmentStaff").innerText = "Chưa cập nhật";
            document.getElementById("appointmentPrice").innerText = "300.000"; // Có thể thay bằng dữ liệu thực tế
            document.getElementById("appointmentStatus").innerText = status;

            // Ẩn tất cả nút trước khi kiểm tra trạng thái
            confirmBtn.classList.add("hidden");
            cancelBtn.classList.add("hidden");
            completeBtn.classList.add("hidden");

            // Hiển thị nút dựa trên trạng thái
            if (status === "Chờ xác nhận") {
                confirmBtn.classList.remove("hidden");
                cancelBtn.classList.remove("hidden");
            } else if (status === "Đã xác nhận") {
                completeBtn.classList.remove("hidden");
                cancelBtn.classList.remove("hidden");
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
        const selectedDate = new Date(dateStr.split("/").reverse().join("-")); // Chuyển "dd/mm/yyyy" thành định dạng Date

        rows.forEach(row => {
            const dateTimeText = row.cells[0].innerText; // "dd/MM/yyyy - HH:mm"
            const rowDateStr = dateTimeText.split(" - ")[0]; // Lấy phần ngày "dd/MM/yyyy"
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
            const rowDateStr = dateTimeText.split(" - ")[0]; // Lấy phần ngày
            const rowTimeStr = dateTimeText.split(" - ")[1]; // Lấy phần giờ
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
            const status = row.cells[4].innerText.trim();
            if (selectedStatus === "all" || status === selectedStatus) {
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
                location.reload(); // Tải lại trang để cập nhật dữ liệu
            })
            .catch(error => console.error("Lỗi cập nhật trạng thái:", error));
    }

    // Gắn sự kiện cho các nút trong modal
    confirmBtn.addEventListener("click", function() {
        updateAppointmentStatus("Đã xác nhận");
    });

    cancelBtn.addEventListener("click", function() {
        updateAppointmentStatus("Đã hủy");
    });

    completeBtn.addEventListener("click", function() {
        updateAppointmentStatus("Hoàn thành");
    });
});