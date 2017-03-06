define(['baf/util/CubePortfolioLightCreator'], function (CubePortfolioLightCreator) {
    return {
        startup: function () {
            CubePortfolioLightCreator.startup($('#grid-container'), $('#filters-container'));
        }
    }
});