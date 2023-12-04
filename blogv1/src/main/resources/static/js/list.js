// 작성자의 글 목록을 가져와서 동적으로 생성하는 함수
function generateContentList() {
  const contentList = document.getElementById('contentList');
  contentList.innerHTML = ''; // 기존 목록 초기화

  fetch('/api/content') // 작성자의 글 목록을 가져올 API 엔드포인트
    .then((response) => response.json())
    .then((data) => {
      data.forEach((content) => {
        const listItem = document.createElement('li');
        const titleLink = document.createElement('a');
        titleLink.href = `/content/${content.id}`;
        titleLink.textContent = content.title;

        listItem.appendChild(titleLink);
        contentList.appendChild(listItem);
      });
    })
    .catch((error) => {
      console.error('Error:', error);
    });
}

// 페이지 로드 시 작성자의 글 목록을 생성
window.addEventListener('load', generateContentList);
