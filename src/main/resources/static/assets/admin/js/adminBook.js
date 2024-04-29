
/*
 * TOAST UI Editor 생성 JavaScript 코드
 */
const bookIndexEditor = new toastui.Editor({
    el: document.querySelector('#bookIndexEditor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '도서 목차를 작성해주세요.'
});

const bookDescEditor = new toastui.Editor({
    el: document.querySelector('#bookDescEditor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '도서의 설명을 작성해주세요.'
});
