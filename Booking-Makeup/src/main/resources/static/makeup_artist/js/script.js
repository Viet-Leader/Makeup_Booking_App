document.querySelectorAll('.box_1_2_1 a').forEach(link => {
    link.addEventListener('click', function() {
        // Lưu đường dẫn trang vào localStorage
        localStorage.setItem('selectedNav', this.getAttribute('href'));
    });
});

// Khi tải lại trang, đặt màu nền cho mục đã chọn
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

// form add branchbranch
document.getElementById("branchForm").addEventListener("submit", function(event) {
    event.preventDefault();
    addBranch();
});

function showForm() {
    document.getElementById("formPopup").classList.remove("hidden");
}

function closeForm() {
    document.getElementById("formPopup").classList.add("hidden");
}

function addBranch() {
    let name = document.getElementById("branchName").value.trim();
    let address = document.getElementById("branchAddress").value.trim();
    let phone = document.getElementById("branchPhone").value.trim();
    let hours = document.getElementById("branchHours").value.trim();

    if (!name || !address || !phone || !hours) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    let table = document.getElementById("branchTable");
    let newRow = table.insertRow();
    newRow.innerHTML = `<td>${name}</td><td>${address}</td><td>${phone}</td><td>${hours}</td>
        <td><button class='delete-btn' onclick='deleteBranch(this)'>Xóa</button></td>`;

    closeForm();
}

function deleteBranch(btn) {
    btn.closest("tr").remove();
}
// form add services
document.getElementById("branchForm").addEventListener("submit", function(event) {
    event.preventDefault();
    addBranch();
});

function showForm() {
    document.getElementById("formPopup").classList.remove("hidden");
}

function closeForm() {
    document.getElementById("formPopup").classList.add("hidden");
}

function addBranch() {
    let name = document.getElementById("serviceName").value.trim();
    let serviceDescription = document.getElementById("serviceDescription").value.trim();
    let hours = document.getElementById("serviceTime").value.trim();
    let  Price = document.getElementById("servicePrice").value.trim();

    if (!name || !address || !phone || !hours) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    let table = document.getElementById("branchTable");
    let newRow = table.insertRow();
    newRow.innerHTML = `<td>${name}</td><td>${address}</td><td>${phone}</td><td>${hours}</td>
        <td><button class='delete-btn' onclick='deleteBranch(this)'>Xóa</button></td>`;

    closeForm();
}

function deleteBranch(btn) {
    btn.closest("tr").remove();
}