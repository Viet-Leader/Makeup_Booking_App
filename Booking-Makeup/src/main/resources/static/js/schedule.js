function confirmBooking(event) {
    const dateInput = document.getElementById("appointmentDate").value;
    const chosenDate = new Date(dateInput);
    const now = new Date();
    now.setSeconds(0, 0); // bỏ phần giây để so chính xác

    if (chosenDate < now) {
        alert("Không thể đặt lịch cho ngày trong quá khứ!");
        return false;
    }

    // Nếu ngày hợp lệ, hiện modal xác nhận
    document.getElementById("confirmModal").style.display = "block";
    event.preventDefault();
    return false;
}

function submitForm() {
    document.getElementById("confirmModal").style.display = "none";
    document.getElementById("appointmentForm").submit();
}

function cancelForm() {
    document.getElementById("confirmModal").style.display = "none";
    window.location.href = "/schedule";
}