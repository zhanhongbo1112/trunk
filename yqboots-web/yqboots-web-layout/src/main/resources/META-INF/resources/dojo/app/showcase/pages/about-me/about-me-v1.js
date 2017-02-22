define(['baf/util/CubePortfolioLightCreator', 'jquery/parallax', 'jquery/counter/waypoints', 'jquery/counter/counterup'],
    function (CubePortfolioLightCreator) {
        return {
            startup: function () {
                $('.parallaxBg').parallax("50%", 0.2);
                $('.parallaxBg1').parallax("50%", 0.4);

                $('.counter').counterUp({
                    delay: 10,
                    time: 1000
                });

                CubePortfolioLightCreator.startup($('#grid-container'), $('#filters-container'));
            }
        }
    });