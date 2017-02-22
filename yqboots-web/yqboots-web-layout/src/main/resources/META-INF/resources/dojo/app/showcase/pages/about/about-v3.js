define(['baf/util/ProgressBar', 'jquery/appear', 'jquery/fancybox/fancybox', 'jquery/owl-carousel/owl-carousel'],
    function (ProgressBar) {
        return {
            startup: function () {
                $(".fbox-modal").fancybox({
                    maxWidth: 800,
                    maxHeight: 600,
                    fitToView: false,
                    width: '70%',
                    height: '70%',
                    autoSize: false,
                    closeClick: false,
                    closeEffect: 'fade',
                    openEffect: 'elastic'
                });

                $(".owl-clients-v1").owlCarousel({
                    items: 7,
                    autoPlay: 5000,
                    itemsDesktop: [1000, 5],
                    itemsDesktopSmall: [900, 4],
                    itemsTablet: [600, 3],
                    itemsMobile: [300, 2]
                });

                ProgressBar.startup('h')
            }
        }
    });