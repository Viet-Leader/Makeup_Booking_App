
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
