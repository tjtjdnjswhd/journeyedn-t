let btn = document.querySelector('.btn-scroll-up');
btn.addEventListener('click', () => {
    window.scroll({top: 0, behavior: "smooth"})
});