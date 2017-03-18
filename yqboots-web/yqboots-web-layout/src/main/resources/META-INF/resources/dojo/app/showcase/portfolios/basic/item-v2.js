define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            var owl = $(".owl-recent-works-v1");
            owl.owlCarousel({
                items: [4],
                itemsDesktop: [1000, 4],
                itemsDesktopSmall: [900, 3],
                itemsTablet: [600, 2],
                itemsMobile: [479, 1],
                slideSpeed: 1000
            });

            // Custom Navigation Events
            $(".next-v2").click(function () {
                owl.trigger('owl.next');
            });
            $(".prev-v2").click(function () {
                owl.trigger('owl.prev');
            });
        }
    }
});