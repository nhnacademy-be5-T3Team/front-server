// isotope js
$(window).on('load', function () {
    // 첫 번째 메뉴의 data-filter 값을 가져와서 초기 필터로 설정
    var initialFilter = $('.filters_menu li:first-child').attr('data-filter');
    $('.filters_menu li:first-child').addClass('active');

    // Isotope 초기화
    var $grid = $(".grid").isotope({
        itemSelector: ".all",
        percentPosition: false,
        masonry: {
            columnWidth: ".all"
        },
        filter: initialFilter // 초기 필터로 첫 번째 data-filter 값 선택
    });

    // 필터 메뉴 클릭 시 동작
    $('.filters_menu li').click(function () {
        $('.filters_menu li').removeClass('active');
        $(this).addClass('active');

        var data = $(this).attr('data-filter');
        $grid.isotope({
            filter: data
        })
    });
});