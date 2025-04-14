// chuyen trang thong tin sang chuyen mon
let selectedDateStr = null;

document.addEventListener("DOMContentLoaded", function () {
    const tabs = document.querySelectorAll(".tab");
    const contents = document.querySelectorAll(".tab-content");

    tabs.forEach(tab => {
        tab.addEventListener("click", function () {
            // Loại bỏ class active khỏi tất cả tab
            tabs.forEach(t => t.classList.remove("active"));

            // Ẩn tất cả nội dung tab
            contents.forEach(content => content.classList.remove("active"));

            // Thêm class active cho tab được chọn
            this.classList.add("active");

            // Hiển thị nội dung tương ứng
            const target = this.getAttribute("data-tab");
            document.getElementById(target).classList.add("active");
        });
    });
});

// work_schedule
document.addEventListener("DOMContentLoaded", function () {
    console.log("JS loaded");

    // ======= CHUYỂN TAB (NẾU CÓ) =======
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');

    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
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

    // ======= CÁC BIẾN TOÀN CỤC CHO LỊCH LÀM VIỆC =======
    const prevBtn = document.querySelector(".week-navigation button:nth-child(1)");
    const nextBtn = document.querySelector(".week-navigation button:nth-child(3)");
    const dateRangeEl = document.querySelector(".date-range");
    const days = document.querySelectorAll(".calendar .day");
    const appointmentRows = document.querySelectorAll(".details tbody tr[data-date]");

    // ======= HÀM XỬ LÝ NGÀY THÁNG =======
    function parseDateRange(rangeStr) {
        const [startStr] = rangeStr.split(" - ");
        const [day, month, year] = startStr.split("/").map(Number);
        const date = new Date(year, month - 1, day);
        date.setHours(0, 0, 0, 0); // Đặt giờ về 00:00:00
        return date;
    }

    function formatDate(date) {
        return `${String(date.getDate()).padStart(2, '0')}/${String(date.getMonth() + 1).padStart(2, '0')}/${date.getFullYear()}`;
    }

    function formatDataDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // ======= HÀM CẬP NHẬT SỐ LƯỢNG LỊCH HẸN =======
    function updateAppointmentsCount() {
        days.forEach(day => {
            const date = day.getAttribute("data-date");
            let count = 0;

            appointmentRows.forEach(row => {
                if (row.getAttribute("data-date") === date) {
                    count++;
                }
            });

            const appEl = day.querySelector(".appointment");
            if (appEl) {
                appEl.textContent = count > 0 ? `${count} lịch hẹn` : "Không có lịch";
            }
        });
    }

    // ======= HÀM CẬP NHẬT TUẦN =======
    function updateWeek(newStartDate) {
        newStartDate.setHours(0, 0, 0, 0); // Đảm bảo giờ của newStartDate
        const dayNames = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];

        for (let i = 0; i < 7; i++) {
            const dayDate = new Date(newStartDate);
            dayDate.setDate(newStartDate.getDate() + i);
            dayDate.setHours(0, 0, 0, 0); // Chuẩn hóa giờ cho dayDate

            const dayEl = days[i];
            if (dayEl) {
                dayEl.querySelector(".date").textContent = String(dayDate.getDate()).padStart(2, '0');
                dayEl.setAttribute("data-date", formatDataDate(dayDate));

                const dayNameEl = dayEl.querySelector(".day-name");
                if (dayNameEl) {
                    dayNameEl.textContent = dayNames[dayDate.getDay()];
                }
            }
        }

        const endDate = new Date(newStartDate);
        endDate.setDate(newStartDate.getDate() + 6);
        endDate.setHours(0, 0, 0, 0); // Chuẩn hóa giờ cho endDate
        dateRangeEl.textContent = `${formatDate(newStartDate)} - ${formatDate(endDate)}`;

        updateAppointmentsCount();
        if (selectedDateStr) {
            const newSelected = [...document.querySelectorAll(".calendar .day")]
                .find(d => d.getAttribute("data-date") === selectedDateStr);
            if (newSelected) {
                highlightSelectedDay(newSelected);
                filterAppointmentsByDate(selectedDateStr);
            } else {
                if (days.length > 0) {
                    highlightSelectedDay(days[0]);
                    filterAppointmentsByDate(days[0].getAttribute("data-date"));
                }
            }
        }
    }

    // ======= XỬ LÝ CLICK VÀO NGÀY LỊCH =======
    function highlightSelectedDay(selectedEl) {
        document.querySelectorAll(".calendar .day").forEach(d => d.classList.remove("active"));
        selectedEl.classList.add("active");
        selectedDateStr = selectedEl.getAttribute("data-date");
    }


    function filterAppointmentsByDate(dateStr) {
        const rows = document.querySelectorAll(".details tbody tr");
        const selectedDate = new Date(dateStr);
        selectedDate.setHours(0, 0, 0, 0); // Chuẩn hóa giờ cho selectedDate
        let found = false;

        rows.forEach(row => {
            const dateCell = row.querySelector("td:nth-child(4)"); // Cột Ngày & Giờ (thứ 4)
            if (!dateCell) return;

            const rowDateStr = dateCell.getAttribute("data-date");
            if (!rowDateStr) return;

            const rowDate = new Date(rowDateStr);
            rowDate.setHours(0, 0, 0, 0); // Chuẩn hóa giờ cho rowDate
            const sameDate = rowDate.toDateString() === selectedDate.toDateString();

            row.style.display = sameDate ? "" : "none";
            if (sameDate) found = true;
        });

        // Dòng "Không có lịch hẹn"
        const emptyRow = document.querySelector("tbody tr[th\\:if]");
        if (emptyRow) {
            emptyRow.style.display = found ? "none" : "";
        }
    }

    days.forEach(day => {
        day.addEventListener("click", function () {
            const selectedDate = this.getAttribute("data-date");
            highlightSelectedDay(this);
            filterAppointmentsByDate(selectedDate);
        });
    });

    // ======= NÚT TUẦN TRƯỚC / TUẦN SAU =======
    prevBtn.addEventListener("click", () => {
        const currentStart = parseDateRange(dateRangeEl.textContent);
        currentStart.setDate(currentStart.getDate() - 7);
        updateWeek(currentStart);
    });

    nextBtn.addEventListener("click", () => {
        const currentStart = parseDateRange(dateRangeEl.textContent);
        currentStart.setDate(currentStart.getDate() + 7);
        updateWeek(currentStart);
    });

    // ======= KHỞI TẠO BAN ĐẦU =======
    // Ẩn tất cả dòng lịch khi load trang
    appointmentRows.forEach(row => {
        row.style.display = "none";
    });

    // Kích hoạt ngày đầu tiên
    if (days.length > 0) {
        days[0].click();
    }

    // Cập nhật số lượng lịch hẹn ban đầu
    updateAppointmentsCount();
});