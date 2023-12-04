let mainText = document.querySelector('h1');

window.addEventListener('scroll', function () {
  let value = window.scrollY;

  if (value > 300) {
    mainText.style.animation = 'disappear 2s ease-out forwards';
    mainText.style.position = 'fixed'; // 헤더를 스크롤 후 고정
  } else {
    mainText.style.animation = 'slide 2s ease-in-out';
    mainText.style.position = 'absolute'; // 원래 위치로 되돌리기
  }
});
