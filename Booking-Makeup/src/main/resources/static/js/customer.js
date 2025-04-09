// Định dạng số thành chuỗi tiền tệ VNĐ
function formatCurrency(value) {
    const number = parseFloat(value);
    if (isNaN(number)) return value;
    return number.toLocaleString('vi-VN') + ' VNĐ';
}

// Áp dụng định dạng cho tất cả các ô có class "price-cell"
function formatAllPrices() {
    const priceCells = document.querySelectorAll('.price-cell');
    priceCells.forEach(cell => {
        const raw = parseFloat(cell.textContent.trim());
        if (!isNaN(raw)) {
            cell.textContent = formatCurrency(raw);
        }
    });
}

// Tự động chạy sau khi DOM load xong
document.addEventListener("DOMContentLoaded", formatAllPrices);

// Xử lý xác nhận hủy lịch hẹn
function confirmCancel(appointmentId) {
    if (confirm("Bạn có muốn hủy cuộc hẹn?")) {
        fetch("/customer/update-status", {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `appointmentId=${appointmentId}&status=cancelled`
        })
            .then(response => response.text())
            .then(result => {
                alert(result);
                location.reload();
            })
            .catch(error => {
                console.error("Lỗi:", error);
                alert("Hủy thất bại!");
            });
    }
}
function mapVietnameseToDbStatus(vietnameseStatus) {
    switch (vietnameseStatus) {
        case "Chờ xác nhận": return "pending";
        case "Đã xác nhận": return "confirmed";
        case "Hoàn thành": return "completed";
        case "Đã hủy": return "cancelled";
        default: return vietnameseStatus; // Giữ nguyên nếu không khớp
    }
}