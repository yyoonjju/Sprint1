
document.addEventListener('DOMContentLoaded', function () {
    var loading = document.getElementById("loading");
    
    let currentBody = 1;

    setTimeout(function() {
        document.querySelector("video").style.animation = "fadeIn 1s ease-in-out 0.5s forwards";
        document.getElementById("body2").style.animation = "fadeIn 1s ease-in-out 0.5s forwards";
        document.getElementById("body3").style.animation = "fadeIn 1s ease-in-out 0.5s forwards";
        document.getElementById("body4").style.animation = "fadeIn 1s ease-in-out 0.5s forwards";
        document.getElementById("body5").style.animation = "fadeIn 1s ease-in-out 0.5s forwards";
        // 나머지 요소들 넣기
    }, 1600);
    

    window.addEventListener('wheel', function (event) {
        if (currentBody === 1 && loading.style.display !== "none") {
            event.preventDefault();
            return;
        }

        if (event.deltaY > 0) {
            currentBody++;
        } else {
            currentBody--;
        }

        currentBody = Math.max(1, Math.min(currentBody, 5));

        const bodyElement = document.getElementById(`body${currentBody}`);
        bodyElement.scrollIntoView({ behavior: 'smooth' });
    });
});



