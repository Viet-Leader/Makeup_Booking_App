
// chuyen trang thong tin sang chuyen mon
document.addEventListener('DOMContentLoaded', function () {
    console.log("JS is running");
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');
    console.log("Tabs found:", tabs);
    console.log("Tab contents found:", tabContents);

    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
            console.log("Tab clicked:", tab.getAttribute('data-tab'));
            tabs.forEach(t => t.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            this.classList.add('active');
            const tabId = this.getAttribute('data-tab');
            const contentToShow = document.getElementById(tabId);
            if (contentToShow) {
                contentToShow.classList.add('active');
            }
        });
    });
});
//end
// work_schedule
// infor.js
document.addEventListener("DOMContentLoaded", function () {
    // Lấy các phần tử DOM
    const prevWeekButton = document.querySelector(".nav-button:first-child");
    const nextWeekButton = document.querySelector(".nav-button:last-child");
    const dateRange = document.querySelector(".date-range");
    const calendarDays = document.querySelectorAll(".calendar .day");
    const tableBody = document.querySelector(".details table tbody");

    // Dữ liệu lịch hẹn mẫu
    const appointments = [
        {
            date: "2025-04-09",
            customer: "Thu Thủy",
            service: "Trang điểm dự tiệc",
            branch: "Chi nhánh Quận 1",
            time: "10:00 - 11:00",
            status: "pending"
        },
        {
            date: "2025-04-10",
            customer: "Lan Anh",
            service: "Làm tóc dạ hội",
            branch: "Chi nhánh Quận 3",
            time: "14:00 - 15:00",
            status: "confirmed"
        }
    ];

    // Hàm định dạng ngày thành DD/MM/YYYY
    function formatDate(date) {
        const day = String(date.getDate()).padStart(2, "0");
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const year = date.getFullYear();
        return `${day}/${month}/${year}`;
    }

    // Hàm định dạng ngày thành YYYY-MM-DD để so sánh
    function formatDateForComparison(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        return `${year}-${month}-${day}`;
    }

    // Hàm tính ngày bắt đầu và kết thúc của tuần
    function getWeekRange(date) {
        const startOfWeek = new Date(date);
        const dayOfWeek = startOfWeek.getDay();
        const diffToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
        startOfWeek.setDate(startOfWeek.getDate() + diffToMonday);

        const endOfWeek = new Date(startOfWeek);
        endOfWeek.setDate(endOfWeek.getDate() + 6);

        return { start: startOfWeek, end: endOfWeek };
    }

    // Hàm cập nhật giao diện lịch
    function updateCalendar(startDate) {
        const { start, end } = getWeekRange(startDate);
        dateRange.textContent = `${formatDate(start)} - ${formatDate(end)}`;

        const currentDate = new Date(start);
        const dayNames = ["Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"];

        calendarDays.forEach((dayElement, index) => {
            const date = new Date(currentDate);
            date.setDate(currentDate.getDate() + index);

            dayElement.querySelector(".day-name").textContent = dayNames[index];
            dayElement.querySelector(".date").textContent = String(date.getDate()).padStart(2, "0");
            dayElement.setAttribute("data-date", formatDateForComparison(date));

            const timeElement = dayElement.querySelector(".time");
            const appointmentElement = dayElement.querySelector(".appointment");
            const statusElement = dayElement.querySelector(".status");

            if (timeElement && appointmentElement) {
                // Giữ nguyên thời gian và lịch hẹn mẫu
            } else if (statusElement) {
                statusElement.textContent = "Không có lịch";
            }
        });
    }

    // Hàm cập nhật bảng chi tiết lịch hẹn
    function updateAppointmentDetails(selectedDate) {
        tableBody.innerHTML = ""; // Xóa nội dung cũ

        const matchingAppointments = appointments.filter(
            (appt) => appt.date === selectedDate
        );

        if (matchingAppointments.length === 0) {
            const row = document.createElement("tr");
            row.innerHTML = `<td colspan="6">Không có lịch hẹn</td>`;
            tableBody.appendChild(row);
        } else {
            matchingAppointments.forEach((appt) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${appt.customer}</td>
                    <td>${appt.service}</td>
                    <td>${appt.branch}</td>
                    <td>${formatDate(new Date(appt.date))}<br>${appt.time}</td>
                    <td>${appt.status}</td>
                    <td><button>Hành động</button></td>
                `;
                tableBody.appendChild(row);
            });
        }
    }

    // Ngày hiện tại (09/04/2025)
    let currentDate = new Date(2025, 3, 9);

    // Khởi tạo lịch ban đầu
    updateCalendar(currentDate);
    updateAppointmentDetails("2025-04-09"); // Hiển thị lịch hẹn cho ngày mặc định

    // Xử lý sự kiện nhấn nút "Tuần trước"
    prevWeekButton.addEventListener("click", function () {
        currentDate.setDate(currentDate.getDate() - 7);
        updateCalendar(currentDate);
    });

    // Xử lý sự kiện nhấn nút "Tuần sau"
    nextWeekButton.addEventListener("click", function () {
        currentDate.setDate(currentDate.getDate() + 7);
        updateCalendar(currentDate);
    });

    // Xử lý sự kiện nhấn vào ngày
    calendarDays.forEach((day) => {
        day.addEventListener("click", function () {
            // Xóa trạng thái active của ngày trước đó
            calendarDays.forEach((d) => d.classList.remove("active"));
            // Thêm trạng thái active cho ngày được chọn
            day.classList.add("active");

            // Cập nhật bảng chi tiết
            const selectedDate = day.getAttribute("data-date");
            updateAppointmentDetails(selectedDate);
        });
    });
});
//end work_schedule
