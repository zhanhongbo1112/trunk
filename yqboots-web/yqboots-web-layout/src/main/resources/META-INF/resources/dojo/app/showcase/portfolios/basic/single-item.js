define(['baf/util/CubePortfolioCreator'], function (CubePortfolioCreator) {
    return {
        startup: function () {
            CubePortfolioCreator.startup($('#grid-container'), $('#filters-container'), {
                gapHorizontal: 20,
                gapVertical: 20,
                mediaQueries: [{
                    width: 800,
                    cols: 4
                }, {
                    width: 500,
                    cols: 2
                }, {
                    width: 320,
                    cols: 1
                }]
            });
        }
    }
});