  const signinBtn = document.querySelector('.signinBtn');
  const signupBtn = document.querySelector('.signupBtn');
  const formBx = document.querySelector('.formBx');
  const wrap = document.querySelector('.wrap');

  signupBtn.onclick = function () {
    formBx.classList.add('active');
    wrap.classList.add('active');
  };

  signinBtn.onclick = function () {
    formBx.classList.remove('active');
    wrap.classList.remove('active');
  };



  const moveBtn = document.getElementById('move');
  const container = document.querySelector('.container');

  moveBtn.addEventListener('click', function () {
    window.scrollTo({
      top: container.offsetTop,
      behavior: 'smooth'
    });
  });