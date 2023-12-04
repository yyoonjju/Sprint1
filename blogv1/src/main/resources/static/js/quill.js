function quilljsediterInit() {
  const formats = [
    'font',
    'size',
    'header',
    'color',
    'background',
    'bold',
    'italic',
    'underline',
    'strike',
    'blockquote',
    'list',
    'bullet',
    'indent',
    'link',
    'image'
  ];

  var option = {
    modules: {
      toolbar: [
        [{ font: [] }],
        [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
        [{ header: [1, 2, 3, 4, 5, 6, false] }],
        [{ color: [] }, { background: [] }], // dropdown with defaults from theme
        ['bold', 'italic', 'underline', 'strike', 'blockquote'],
        [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
        ['link', 'image']
      ]
    },
    placeholder: '여기 input 유효!',
    theme: 'snow',
    formats: formats
  };

  const quill = new Quill('#editor', option);
  quill.on('text-change', function () {
    document.getElementById('quill_html').value = quill.root.innerHTML;
  });
}

quilljsediterInit();
