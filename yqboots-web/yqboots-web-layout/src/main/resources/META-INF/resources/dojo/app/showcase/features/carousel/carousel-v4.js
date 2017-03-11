define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            $(".owl-slider-v4").owlCarousel({
                items: 3,
                itemsDesktop: [1000, 3],
                itemsTablet: [600, 2],
                itemsMobile: [479, 1]
            });
        }
    }
});