define(['jquery/parallax', 'jquery/owl-carousel/owl-carousel', 'jquery/counter/waypoints', 'jquery/counter/counterup'],
    function () {
        return {
            startup: function () {
                jQuery('.counter').counterUp({
                    delay: 10,
                    time: 1000
                });

                $('.parallaxBg').parallax("50%", 0.4);

                $(".owl-clients-v1").owlCarousel({
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