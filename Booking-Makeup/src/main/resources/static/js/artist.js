document.addEventListener("DOMContentLoaded", function () {
    const commentForm = document.querySelector(".m-comment form");
    if (!commentForm) {
        console.error("Không tìm thấy form bình luận với class .m-comment form");
        return;
    }

    commentForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const makeupArtistIdInput = commentForm.querySelector("input[name='makeupArtistId']");
        const commentInput = commentForm.querySelector("input[name='cmt']");
        if (!makeupArtistIdInput || !commentInput) {
            console.error("Không tìm thấy input makeupArtistId hoặc cmt");
            return;
        }

        const makeupArtistId = makeupArtistIdInput.value;
        const commentText = commentInput.value.trim();

        if (!makeupArtistId) {
            alert("Không tìm thấy ID nghệ sĩ trang điểm. Vui lòng tải lại trang!");
            return;
        }
        if (!commentText) {
            alert("Vui lòng nhập nội dung bình luận!");
            return;
        }

        // Tạo FormData để gửi dữ liệu dưới dạng application/x-www-form-urlencoded
        const formData = new FormData();
        formData.append("makeupArtistId", makeupArtistId);
        formData.append("cmt", commentText);

        console.log("Dữ liệu gửi đi:", { makeupArtistId, cmt: commentText });

        fetch("/comments/add", {
            method: "POST",
            body: formData, // Không cần Content-Type, FormData tự động đặt thành application/x-www-form-urlencoded
        })
            .then((response) => {
                if (!response.ok) {
                    return response.text().then((errorMessage) => {
                        throw new Error(errorMessage || "Lỗi không xác định từ server");
                    });
                }
                return response.text(); // Sử dụng .text() để nhận thông báo từ backend
            })
            .then((message) => {
                alert(message); // Hiển thị thông báo từ backend

                // Tự động reload lại trang sau khi bình luận thành công
                location.reload(); // Tải lại trang
            })
            .catch((error) => {
                console.error("Lỗi chi tiết khi gửi bình luận:", error);
                alert("Không thể gửi bình luận: " + error.message);
            });
    });
});
