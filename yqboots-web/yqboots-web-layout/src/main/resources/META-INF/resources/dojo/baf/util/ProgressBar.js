define(['jquery'], function () {
    return {
        startup: function (mode) {
            if (mode === 'h') {
                this._horizontal();
                return;
            }

            if (mode = 'v') {
                this._vertical();
                return;
            }

            throw new Error('mode should be set');
        },

        _vertical: function () {
            $('.progress').each(function () {
                $(this).appear(function () {
                    $(this).animate({opacity: 1, left: "0px"}, 800);
                    var b = $(this).find(".progress-bar").attr("data-height");
                    $(this).find(".progress-bar").animate({
                        height: b + "%"
                    }, 100, "linear");
                });
            });
        },

        _horizontal: function () {
            $('.progress').each(function () {
                $(this).appear(function () {
                    $(this).animate({opacity: 1, left: "0px"}, 800);
                    var b = $(this).find(".progress-bar").attr("data-width");
                    $(this).find(".progress-bar").animate({
                        width: b + "%"
                    }, 100, "linear");
                });
            });
        }
    }
});