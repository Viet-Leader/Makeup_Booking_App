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

// Quản lý chi nhánh
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