define(['jquery/owl-carousel/owl-carousel'], function () {
    return {
        startup: function () {
            $(".owl-ts-v1").owlCarousel({
                slideSpeed: 600,
                singleItem: true,
                navigation: true,
                navigationText: ["", ""],
            });
        }
    }
});