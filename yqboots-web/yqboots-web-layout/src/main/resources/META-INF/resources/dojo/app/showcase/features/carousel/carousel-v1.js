define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            var owl = $(".owl-slider").owlCarousel({
                itemsDesktop: [1000, 5],
                itemsDesktopSmall: [900, 4],
                itemsTablet: [600, 3],
                itemsMobile: [479, 2]
            });

            $(".next-v1").click(function () {
                owl.trigger('owl.next');
            });

            $(".prev-v1").click(function () {
                owl.trigger('owl.prev');
            });
        }
    }
});