define([ 'jquery' ], function() {
    return {
        startup : function() {
            $(window).scroll(function() {
                if ($(window).scrollTop() > 100) {
                    $('.header-fixed .header-sticky').addClass('header-fixed-shrink');
                } else {
                    $('.header-fixed .header-sticky').removeClass('header-fixed-shrink');
                }
            });
        }
    };
});