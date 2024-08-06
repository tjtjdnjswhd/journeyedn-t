const request = new Request("/api/country", {
    method: "GET"
});

fetch(request).then((response) => {
    response.json().then(countryMap => {
        const addressDropdowns = document.querySelectorAll('.dropdown-address');
        addressDropdowns.forEach(dropdown => {
            const dropdownToggleBtn = dropdown.querySelector('.address-toggle');
            const cityUl = dropdown.querySelector('.list-city');

            for (let countryMapKey in countryMap) {
                const cityLi = document.createElement('li');
                cityLi.role = 'button';
                cityLi.textContent = countryMapKey;
                cityLi.dataset.value = countryMapKey;
                cityLi.classList.add('list-group-item');
                cityUl.appendChild(cityLi);
            }

            const cityLiList = cityUl.querySelectorAll('li[role=button]');
            const countryUl = dropdown.querySelector('.list-country');
            const idInput = dropdown.querySelector('input[type=hidden]');

            cityLiList.forEach(cityLi => {
                cityLi.addEventListener('click', e => {
                    while (countryUl.children.length !== 1) {
                        countryUl.lastChild.remove();
                    }

                    cityUl.querySelector('.active')?.classList.remove('active');

                    let target = e.target;
                    target.classList.add('active');

                    let cityName = target.dataset.value;
                    let countryDict = countryMap[cityName];
                    for (let countryName in countryDict) {
                        let countryId = countryDict[countryName];

                        const li = document.createElement('li');
                        li.classList.add('list-group-item');
                        li.role = 'button';
                        li.dataset.value = countryId;
                        li.textContent = countryName;
                        countryUl.appendChild(li);
                        li.addEventListener('click', e => selectCountry(e.target, cityName, countryName));
                    }

                    function selectCountry(li, cityName, countryName) {
                        countryUl.querySelector('.active')?.classList.remove('active');
                        li.classList.add('active');
                        idInput.value = parseInt(li.dataset.value);
                        dropdownToggleBtn.innerHTML = cityName + ' ' + countryName;
                    }
                });
            });

            if (idInput.value) {
                for (let countryMapKey in countryMap) {
                    const countryDict = countryMap[countryMapKey];
                    for (let countryName in countryDict) {
                        if (countryDict[countryName] === parseInt(idInput.value)) {
                            let targetCityLi = cityUl.querySelector('li[data-value=' + countryMapKey + ']');
                            let clickEvent = new Event('click')
                            targetCityLi.dispatchEvent(clickEvent);

                            let countryLi = countryUl.querySelector('li[data-value="' + idInput.value + '"]');
                            countryLi.dispatchEvent(clickEvent);
                            return;
                        }
                    }
                }
            }
        });
    });
})
