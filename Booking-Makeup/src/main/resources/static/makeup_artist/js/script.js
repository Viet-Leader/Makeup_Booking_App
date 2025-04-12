
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

    // ======= XỬ LÝ CLICK VÀO NGÀY LỊCH =======
    const dayElements = document.querySelectorAll(".calendar .day");

    dayElements.forEach(day => {
        day.addEventListener("click", function () {
            const selectedDate = this.getAttribute("data-date"); // yyyy-MM-dd
            highlightSelectedDay(this);
            filterAppointmentsByDate(selectedDate);
        });
    });

    function highlightSelectedDay(selectedEl) {
        document.querySelectorAll(".calendar .day").forEach(d => d.classList.remove("active"));
        selectedEl.classList.add("active");
    }

    // ======= HÀM LỌC LỊCH HẸN THEO NGÀY =======
    function filterAppointmentsByDate(dateStr) {
        const rows = document.querySelectorAll(".details tbody tr");
        const selectedDate = new Date(dateStr);
        let found = false;

        rows.forEach(row => {
            const dateCell = row.querySelector("td:nth-child(4)"); // Cột Ngày & Giờ (thứ 4)
            if (!dateCell) return;

            const rowDateStr = dateCell.getAttribute("data-date");
            if (!rowDateStr) return;

            const rowDate = new Date(rowDateStr);
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


    // ======= NÚT TUẦN TRƯỚC / TUẦN SAU =======
    const prevBtn = document.querySelector(".week-navigation button:nth-child(1)");
    const nextBtn = document.querySelector(".week-navigation button:nth-child(3)");
    const dateRangeEl = document.querySelector(".date-range");
    const days = document.querySelectorAll(".calendar .day");

    function parseDateRange(rangeStr) {
        const [startStr] = rangeStr.split(" - ");
        const [day, month, year] = startStr.split("/").map(Number);
        return new Date(year, month - 1, day);
    }

    function formatDate(date) {
        return date.toLocaleDateString('vi-VN');
    }

    function formatDataDate(date) {
        return date.toISOString().split("T")[0];
    }

    function updateWeek(newStartDate) {
        for (let i = 0; i < 7; i++) {
            const dayDate = new Date(newStartDate);
            dayDate.setDate(newStartDate.getDate() + i);

            const dayEl = days[i];
            if (dayEl) {
                dayEl.querySelector(".date").textContent = String(dayDate.getDate()).padStart(2, '0');
                dayEl.setAttribute("data-date", formatDataDate(dayDate));

                // Reset lịch hiển thị giả
                const statusEl = dayEl.querySelector(".status");
                const timeEl = dayEl.querySelector(".time");
                const appEl = dayEl.querySelector(".appointment");

                if (statusEl) statusEl.textContent = "Không có lịch";
                if (timeEl) timeEl.textContent = "09:00 - 17:00";
                if (appEl) appEl.textContent = "0 lịch hẹn";
            }
        }

        const endDate = new Date(newStartDate);
        endDate.setDate(newStartDate.getDate() + 6);
        dateRangeEl.textContent = `${formatDate(newStartDate)} - ${formatDate(endDate)}`;
    }

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

});
document.addEventListener("DOMContentLoaded", function () {
    const appointmentRows = document.querySelectorAll(".details tbody tr");

    // Ẩn tất cả dòng lịch khi load trang
    appointmentRows.forEach(row => {
        row.style.display = "none";
    });

    // Xử lý khi click vào từng ngày
    document.querySelectorAll(".calendar .day").forEach(dayEl => {
        dayEl.addEventListener("click", function () {
            const selectedDate = this.getAttribute("data-date"); // yyyy-MM-dd
            let found = false;

            appointmentRows.forEach(row => {
                const dateCell = row.querySelector("td:nth-child(4)");
                const rowDate = dateCell.getAttribute("data-date");

                if (rowDate === selectedDate) {
                    row.style.display = "";
                    found = true;
                } else {
                    row.style.display = "none";
                }
            });

            // Dòng "Không có lịch hẹn"
            const emptyRow = document.querySelector("tbody tr[th\\:if]");
            if (emptyRow) {
                emptyRow.style.display = found ? "none" : "";
            }

            // Đánh dấu ngày được chọn
            document.querySelectorAll(".calendar .day").forEach(d => d.classList.remove("active"));
            this.classList.add("active");
        });
    });
});



//end work_schedule
