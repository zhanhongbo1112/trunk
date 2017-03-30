define(['baf/util/CubePortfolioCreator'], function (CubePortfolioCreator) {
    return {
        startup: function () {
            CubePortfolioCreator.startup($('#grid-container'), $('#filters-container'), {
                gapHorizontal: 0,
                gapVertical: 0,
                mediaQueries: [{
                    width: 1600,
                    cols: 6
                }, {
                    width: 1200,
                    cols: 6
                }, {
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