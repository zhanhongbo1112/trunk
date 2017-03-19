define(['jquery/fancybox/fancybox'], function () {
    return {
        startup: function () {
            $(".fancybox").fancybox({
                groupAttr: 'data-rel',
                prevEffect: 'fade',
                nextEffect: 'fade',
                openEffect: 'elastic',
                closeEffect: 'fade',
                closeBtn: true,
                helpers: {
                    title: {
                        type: 'float'
                    }
                }
            });
        }
    }
});