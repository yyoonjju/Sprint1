
document.querySelectorAll('.dropdown').forEach(function(dropdown, index) {
    let titleBox = dropdown.querySelector('.title_box');
    let option = dropdown.querySelector('.option');

    titleBox.onclick = function(event) {
        let isActive = dropdown.classList.contains('active');

        dropdown.classList.toggle('active');

        if (dropdown.classList.contains('active')) {
            let siblings = Array.from(dropdown.parentNode.children).filter(child => child !== dropdown);

            siblings.forEach(function(otherDropdown) {
                if (otherDropdown.classList.contains('active')) {
                    otherDropdown.classList.remove('active');
                    otherDropdown.style.transform = 'translateY(0)';
                } else {
                    otherDropdown.style.transform = 'translateY(0)';
                }
            });

            if (!isActive) {
                dropdown.style.transform = `translateY(0)`;
            }

            let optionHeight = option.clientHeight + 50;
            let nextDropdown = dropdown.nextElementSibling;
            while (nextDropdown) {
                nextDropdown.style.transform = `translateY(${optionHeight}px)`;
                nextDropdown = nextDropdown.nextElementSibling;
            }

        } else {
            let siblings = Array.from(dropdown.parentNode.children).filter(child => child !== dropdown);
            siblings.forEach(function(otherDropdown) {
                otherDropdown.style.transform = 'translateY(0)';
            });
        }

        event.stopPropagation();
    };
});
