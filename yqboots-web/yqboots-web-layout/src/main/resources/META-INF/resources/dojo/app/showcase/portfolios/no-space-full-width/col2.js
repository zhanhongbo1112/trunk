define(['baf/util/CubePortfolioCreator', 'jquery/cube-portfolio/cube-portfolio'], function (CubePortfolioCreator) {
    return {
        startup: function () {
            CubePortfolioCreator.startup2ns($('#grid-container'), $('#filters-container'));
        }
    }
});