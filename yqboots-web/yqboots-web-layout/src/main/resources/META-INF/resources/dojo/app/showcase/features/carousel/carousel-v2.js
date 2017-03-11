define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            var owl1 = $(".owl-slider-v2").owlCarousel({
                itemsDesktop: [1000, 5],
                itemsDesktopSmall: [900, 4],
                itemsTablet: [600, 3],
                itemsMobile: [479, 2],
                slideSpeed: 1000
            });

            $(".next-v2").click(function () {
                owl1.trigger('owl.next');
            });

            $(".prev-v2").click(function () {
                owl1.trigger('owl.prev');
            });
        }
    }
});