document.addEventListener('DOMContentLoaded', function() {
    updateSearchTypeDisplay();
    updateSearchQueryDisplay();
    updateCategoryIdFromURL();

    var searchInput = document.getElementById('searchQueryInput');
    var resultsContainer = document.getElementById('autocompleteResults');

    searchInput.addEventListener('input', function() {
        var query = this.value;
        if (query.length > 1) {
            fetch('/autocomplete?prefix=' + encodeURIComponent(query))
                .then(response => response.json())
                .then(response => {
                    if (response.data && response.data.length) {
                        resultsContainer.innerHTML = '';
                        response.data.forEach(item => {
                            var resultItem = document.createElement('div');
                            resultItem.textContent = item;
                            resultItem.addEventListener('click', function() {
                                searchInput.value = this.textContent;
                                resultsContainer.innerHTML = '';
                                resultsContainer.style.display = 'none';
                            });
                            resultsContainer.appendChild(resultItem);
                        });
                        resultsContainer.style.display = 'block';
                    } else {
                        resultsContainer.innerHTML = '';
                        resultsContainer.style.display = 'none';
                    }
                })
                .catch(error => console.error('Error fetching autocomplete suggestions:', error));
        } else {
            resultsContainer.innerHTML = '';
            resultsContainer.style.display = 'none';
        }
    });

    document.addEventListener('click', function(event) {
        if (!searchInput.contains(event.target) && !resultsContainer.contains(event.target)) {
            resultsContainer.style.display = 'none';
        }
    });

    searchInput.addEventListener('focus', function() {
        if (resultsContainer.innerHTML !== '') {
            resultsContainer.style.display = 'block';
        }
    });
});

function updateCategoryIdFromURL() {
    const path = window.location.pathname;
    const categoryPattern = /\/category\/(\d+)/; // '/category/' 다음에 오는 숫자를 찾는 정규 표현식
    const match = path.match(categoryPattern);

    if (match && match[1]) {
        document.getElementById('categoryIdInput').value = match[1];
    } else {
        document.getElementById('categoryIdInput').parentNode.removeChild(document.getElementById('categoryIdInput'));
    }
}
function setSearchType(type) {
    var typeNames = {
        'all': '통합검색',
        'book_name': '책 이름',
        'publisher_name': '출판사',
        'participant_name': '참여자'
    };
    document.getElementById('searchTypeInput').value = type;
    document.getElementById('dropdownMenuButton').textContent = typeNames[type];
}
function updateSearchTypeDisplay() {
    var searchParams = new URLSearchParams(window.location.search);
    var currentType = searchParams.get('searchType') || 'all';
    setSearchType(currentType);
}
function updateSearchQueryDisplay() {
    var searchParams = new URLSearchParams(window.location.search);
    var currentQuery = searchParams.get('query');
    if (currentQuery) {
        document.getElementById('searchQueryInput').value = currentQuery;
    }
}