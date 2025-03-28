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
                    return response.json().then((errorData) => {
                        throw new Error(errorData.message || "Lỗi không xác định từ server");
                    });
                }
                return response.json();
            })
            .then((comment) => {
                if (comment && comment.cmt) {
                    const commentList = document.querySelector(".d-comment");
                    if (!commentList) {
                        console.error("Không tìm thấy danh sách bình luận .d-comment");
                        return;
                    }
                    const newComment = document.createElement("div");
                    newComment.classList.add("u-comment");
                    newComment.innerHTML = `
                        <img class="u-ava" src="${comment.customer?.user?.avatar || '/image/default-avatar.jpg'}" alt="Avatar" />
                        <div class="ten">
                            <p>${comment.customer?.user?.nameAccount || "Ẩn danh"}</p>
                            <p>${comment.cmt}</p>
                        </div>
                    `;
                    commentList.appendChild(newComment);
                    commentInput.value = "";
                } else {
                    alert("Dữ liệu trả về không hợp lệ, vui lòng thử lại!");
                }
            })
            .catch((error) => {
                console.error("Lỗi chi tiết khi gửi bình luận:", error);
                alert("Không thể gửi bình luận: " + error.message);
            });
    });
});