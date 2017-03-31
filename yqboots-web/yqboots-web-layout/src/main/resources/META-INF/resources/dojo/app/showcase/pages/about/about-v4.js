define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            $(".owl-clients").owlCarousel({
                items: 7,
                autoPlay: 5000,
                itemsDesktop: [1000, 5],
                itemsDesktopSmall: [900, 4],
                itemsTablet: [600, 3],
                itemsMobile: [300, 2]
            });
        }
    }
});