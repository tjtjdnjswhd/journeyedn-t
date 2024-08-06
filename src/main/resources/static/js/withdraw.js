{
    let withdrawForm = document.getElementById('withdraw-form');
    withdrawForm?.addEventListener('submit', (e) => {
        e.preventDefault();
        if (confirm("정말 탈퇴하시겠습니까?")) {
            withdrawForm.submit();
        }
    });
}