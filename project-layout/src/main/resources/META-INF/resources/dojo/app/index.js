define(['baf/util/CubePortfolioCreator', 'jquery'], function (CubePortfolioCreator) {
    return {
        startup: function () {
            CubePortfolioCreator.startup($('#grid-container'), $('#filters-container'));
        }
    };
});