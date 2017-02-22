define(['baf/util/ProgressBar', 'baf/util/CubePortfolioLightCreator', 'jquery/appear', 'jquery/parallax'],
    function (ProgressBar, CubePortfolioLightCreator) {
        return {
            startup: function () {
                $('.parallaxBg').parallax("50%", 0.2);
                $('.parallaxBg1').parallax("50%", 0.4);

                ProgressBar.startup('h');
                CubePortfolioLightCreator.startup($('#grid-container'), $('#filters-container'));
            }
        }
    });