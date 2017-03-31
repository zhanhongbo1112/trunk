define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            var owl1 = $(".owl-carousel-v2 .owl-slider").owlCarousel({
                itemsDesktop: [1000, 5],
                itemsDesktopSmall: [900, 4],
                itemsTablet: [600, 3],
                itemsMobile: [479, 2],
                slideSpeed: 1000
            });

            $(".owl-carousel-v2 .next").click(function () {
                owl1.trigger('owl.next');
            });

            $(".owl-carousel-v2 .prev").click(function () {
                owl1.trigger('owl.prev');
            });
        }
    }
});