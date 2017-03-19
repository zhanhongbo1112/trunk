define(['baf/util/CubePortfolioCreator', 'jquery/cube-portfolio/cube-portfolio'], function (CubePortfolioCreator) {
    return {
        startup: function () {
            CubePortfolioCreator.startup6fw_ns($('#grid-container'), $('#filters-container'));
        }
    }
});