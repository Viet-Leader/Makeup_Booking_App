// Đánh dấu mục điều hướng đã chọn
document.querySelectorAll('.box_1_2_1 a').forEach(link => {
    link.addEventListener('click', function() {
        localStorage.setItem('selectedNav', this.getAttribute('href'));
    });
});

window.addEventListener('DOMContentLoaded', () => {
    let selectedNav = localStorage.getItem('selectedNav');
    if (selectedNav) {
        document.querySelectorAll('.box_1_2_1 a').forEach(link => {
            if (link.getAttribute('href') === selectedNav) {
                link.parentElement.style.backgroundColor = '#fce7f3';
            }
        });
    }
});

// start Quản lý chi nhánh
function showForm(type) {
    if (type === 'branch') {
        document.getElementById('formPopup').classList.remove('hidden');
        // Reset form khi mở
        document.getElementById('branchName').value = '';
        document.getElementById('branchAddress').value = '';
        document.getElementById('branchPhone').value = '';
        document.getElementById('branchHours').value = '09:00 - 20:00';
        delete document.getElementById('formPopup').dataset.branchId; // Xóa branchId nếu có
    }
}

function closeForm() {
    document.getElementById('formPopup').classList.add('hidden');
}

function addBranch() {
    const branchId = document.getElementById('formPopup').dataset.branchId;
    const branchData = {
        name: document.getElementById('branchName').value.trim(),
        address: document.getElementById('branchAddress').value.trim(),
        phone: document.getElementById('branchPhone').value.trim(),
        openingHours: document.getElementById('branchHours').value.trim().split(' - ')[0],
        closingTime: document.getElementById('branchHours').value.trim().split(' - ')[1]
    };

    if (!branchData.name || !branchData.address || !branchData.phone || !branchData.openingHours || !branchData.closingTime) {
        alert('Vui lòng nhập đầy đủ thông tin!');
        return;
    }

    const url = branchId ? `/admin/branch/${branchId}` : '/admin/branch';
    const method = branchId ? 'PUT' : 'POST';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(branchData)
    })
        .then(response => {
            if (response.ok) {
                alert(branchId ? 'Cập nhật chi nhánh thành công!' : 'Thêm chi nhánh thành công!');
                location.reload(); // Tải lại trang để cập nhật danh sách
            } else {
                alert('Thao tác thất bại!');
            }
        })
        .catch(error => console.error('Lỗi:', error));

    closeForm();
}

function editBranch(branchId) {
    fetch(`/admin/branch/${branchId}`, { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không tìm thấy chi nhánh!');
            }
            return response.json();
        })
        .then(branch => {
            if (!branch) {
                throw new Error('Dữ liệu chi nhánh không hợp lệ!');
            }
            document.getElementById('branchName').value = branch.name || '';
            document.getElementById('branchAddress').value = branch.address || '';
            document.getElementById('branchPhone').value = branch.phone || '';
            document.getElementById('branchHours').value = branch.openingHours && branch.closingTime
                ? `${branch.openingHours} - ${branch.closingTime}`
                : '09:00 - 20:00';
            document.getElementById('formPopup').dataset.branchId = branchId;
            document.getElementById('formPopup').classList.remove('hidden');
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin chi nhánh:', error);
            alert('Không thể lấy thông tin chi nhánh: ' + error.message);
        });
}

function deleteBranch(branchId) {
    if (confirm('Bạn có chắc chắn muốn xóa chi nhánh này không?')) {
        fetch(`/admin/branch/${branchId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert('Xóa chi nhánh thành công!');
                    location.reload();
                } else {
                    alert('Xóa chi nhánh thất bại!');
                }
            })
            .catch(error => console.error('Lỗi khi xóa chi nhánh:', error));
    }
}
//end Quản lý chi nhánh

// start Quản lý dịch vụ
function showForm(type) {
    if (type === 'service') {
        document.getElementById('formPopup').classList.remove('hidden');
        // Reset form khi mở
        document.getElementById('serviceName').value = '';
        document.getElementById('serviceDescription').value = '';
        document.getElementById('serviceDuration').value = '';
        document.getElementById('servicePrice').value = '';
        delete document.getElementById('formPopup').dataset.serviceId; // Xóa serviceId nếu có
    }
}

function closeForm() {
    document.getElementById('formPopup').classList.add('hidden');
}

function addservice() {
    const serviceId = document.getElementById('formPopup').dataset.serviceId;
    const serviceData = {
        name: document.getElementById('serviceName').value.trim(),
        description: document.getElementById('serviceDescription').value.trim(),
        duration: document.getElementById('serviceDuration').value.trim(),
        price: document.getElementById('servicePrice').value.trim()
    };

    if (!serviceData.name || !serviceData.description || !serviceData.duration || !serviceData.price) {
        alert('Vui lòng nhập đầy đủ thông tin!');
        return;
    }

    const url = serviceId ? `/admin/service/${serviceId}` : '/admin/service';
    const method = serviceId ? 'PUT' : 'POST';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(serviceData)
    })
        .then(response => {
            if (response.ok) {
                alert(serviceId ? 'Cập nhật dịch vụ thành công!' : 'Thêm dịch vụ thành công!');
                location.reload(); // Tải lại trang để cập nhật danh sách
            } else {
                return response.text().then(text => { throw new Error(text) });
            }
        })
        .catch(error => {
            console.error('Lỗi:', error);
            alert('Thao tác thất bại: ' + error.message);
        });

    closeForm();
}

function editService(serviceId) {
    fetch(`/admin/service/${serviceId}`, { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Không tìm thấy dịch vụ!');
            }
            return response.json();
        })
        .then(serviceWrapper => {
            const service = serviceWrapper; // Kiểm tra dữ liệu trả về
            if (!service) {
                throw new Error('Dữ liệu dịch vụ không hợp lệ!');
            }
            document.getElementById('serviceName').value = service.name || '';
            document.getElementById('serviceDescription').value = service.description || '';
            document.getElementById('serviceDuration').value = service.duration || '';
            document.getElementById('servicePrice').value = service.price || '';
            document.getElementById('formPopup').dataset.serviceId = serviceId;
            document.getElementById('formPopup').classList.remove('hidden');
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin dịch vụ:', error);
            alert('Không thể lấy thông tin dịch vụ: ' + error.message);
        });
}

function deleteService(serviceId) {
    if (confirm('Bạn có chắc chắn muốn xóa dịch vụ này không?')) {
        fetch(`/admin/service/${serviceId}`, { method: 'DELETE' }) // Sửa URL từ /branch thành /service
            .then(response => {
                if (response.ok) {
                    alert('Xóa dịch vụ thành công!');
                    location.reload();
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .catch(error => {
                console.error('Lỗi khi xóa dịch vụ:', error);
                alert('Xóa dịch vụ thất bại: ' + error.message);
            });
    }
}
// end Quản lý dịch vụ

// start quản lý lịch hẹn
let selectedAppointmentId = null;

function mapVietnameseToDbStatus(vietnameseStatus) {
    switch (vietnameseStatus.toLowerCase()) {
        case "chờ xác nhận": return "pending";
        case "đã xác nhận": return "confirmed";
        case "hoàn thành": return "completed";
        case "đã hủy": return "cancelled";
        default: return vietnameseStatus.toLowerCase();
    }
}

function mapDbToVietnameseStatus(dbStatus) {
    switch (dbStatus.toLowerCase()) {
        case "pending": return "Chờ xác nhận";
        case "confirmed": return "Đã xác nhận";
        case "completed": return "Hoàn thành";
        case "cancelled": return "Đã hủy";
        default: return dbStatus;
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const detailForm = document.getElementById("appointmentDetailForm");
    const confirmBtn = document.getElementById("confirmBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const completeBtn = document.getElementById("completeBtn");
    const closeDetailBtn = document.getElementById("closeDetailBtn");

    document.querySelectorAll(".detail").forEach(button => {
        button.addEventListener("click", function () {
            selectedAppointmentId = this.dataset.id;
            const row = this.closest("tr");
            const dateTime = row.cells[4].innerText;
            const branch = row.cells[3].innerText;
            const service = row.cells[1].innerText;
            const vietnameseStatus = row.cells[5].innerText.trim();
            const dbStatus = mapVietnameseToDbStatus(vietnameseStatus);
            const makeupartist = this.dataset.makeupartist || 'Chưa cập nhật';
            const servicePrice = parseFloat(this.dataset.servicePrice) || 0;
            const makeupartistPrice = parseFloat(this.dataset.makeupartistPrice) || 0;
            const totalPrice = servicePrice + makeupartistPrice;

            document.getElementById("appointmentDateTime").innerText = dateTime;
            document.getElementById("appointmentBranch").innerText = branch;
            document.getElementById("appointmentService").innerText = service;
            document.getElementById("appointmentStaff").innerText = makeupartist;
            document.getElementById("appointmentPrice").innerText = totalPrice.toLocaleString('vi-VN');
            document.getElementById("appointmentStatus").innerText = vietnameseStatus;

            confirmBtn.classList.add("hidden");
            cancelBtn.classList.add("hidden");
            completeBtn.classList.add("hidden");

            if (dbStatus === "pending") {
                confirmBtn.classList.remove("hidden");
                cancelBtn.classList.remove("hidden");
            } else if (dbStatus === "confirmed") {
                completeBtn.classList.remove("hidden");
                cancelBtn.classList.remove("hidden");
            }

            detailForm.classList.remove("hidden");
            detailForm.classList.remove("hidden");
            document.getElementById("overlay").classList.remove("hidden");
        });
    });

    closeDetailBtn.addEventListener("click", function () {
        detailForm.classList.add("hidden");
        selectedAppointmentId = null;
    });

    function updateAppointmentStatus(newStatus) {
        if (!selectedAppointmentId) {
            alert("Không tìm thấy cuộc hẹn!");
            return;
        }

        fetch("/admin/update-status", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: `appointmentId=${selectedAppointmentId}&status=${newStatus}`
        })
            .then(response => response.text())
            .then(message => {
                alert(message);
                location.reload();
            })
            .catch(error => {
                console.error("Lỗi cập nhật trạng thái:", error);
                alert("Cập nhật trạng thái thất bại!");
            });
    }

    confirmBtn.addEventListener("click", function () {
        if (confirm("Xác nhận lịch hẹn này?")) {
            updateAppointmentStatus("confirmed");
        }
    });

    cancelBtn.addEventListener("click", function () {
        if (confirm("Bạn có chắc muốn hủy lịch hẹn này?")) {
            updateAppointmentStatus("cancelled");
        }
    });

    completeBtn.addEventListener("click", function () {
        if (confirm("Xác nhận hoàn thành cuộc hẹn này?")) {
            updateAppointmentStatus("completed");
        }
    });

});
// end quản lý lịch hẹn

