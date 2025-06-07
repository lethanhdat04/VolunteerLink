(function() {
    const token = localStorage.getItem('accessToken');
    const role  = localStorage.getItem('role');

    // Nếu không có token hoặc role → chuyển về trang login
    if (!token || !role) {
        window.location.href = '/login-page.html';
        return;
    }

    // Lấy role cho phép của trang hiện tại
    const pageRole = document.body.getAttribute('data-role');
    // if (!pageRole) return; // Nếu trang không khai báo data-role, không kiểm tra thêm

    // Nếu role người dùng không khớp với data-role của trang → chuyển về dashboard tương ứng
    if (role !== pageRole) {
        switch (role) {
            case 'VOLUNTEER':
                window.location.href = '/dashboard.html';
                break;
            case 'ORGANIZATION':
                window.location.href = '/dashboard-organization.html';
                break;
            case 'ADMIN':
                window.location.href = '/admin-dashboard.html';
                break;
            case 'RECIPIENT':
                // Nếu có trang dành riêng cho Recipient, thay đường dẫn tương ứng
                window.location.href = '/dashboard.html';
                break;
            default:
                window.location.href = '/login-page.html';
        }
    }
})();

// nút đăng xuất
document.getElementById('btnLogout').addEventListener('click', function(e) {
    e.preventDefault();
    // Xóa toàn bộ token và role
    localStorage.removeItem('accessToken');
    localStorage.removeItem('role');
    // Chuyển về trang login
    window.location.href = '/login-page.html';
});
