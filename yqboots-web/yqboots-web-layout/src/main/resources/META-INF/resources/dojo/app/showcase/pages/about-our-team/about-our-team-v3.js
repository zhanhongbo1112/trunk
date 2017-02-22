define(['jquery/parallax'], function () {
    return {
        startup: function () {
            $('.parallaxBg').parallax("50%", 0.2);
        }
    }
});