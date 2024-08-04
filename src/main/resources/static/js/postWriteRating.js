document.querySelector('.rating').addEventListener('mouseon', function () {
    let checkedRadio = this.querySelector('input:checked');
    if (checkedRadio) {
      let labels = this.querySelectorAll('label');
      labels.forEach(label => {
        if (label.htmlFor <= checkedRadio.id.slice(-1)) {
          label.querySelector('svg path').style.fill = '#F4D2A0';
        } else {
          label.querySelector('svg path').style.fill = '#ccc';
        }
      });
    }
  });
