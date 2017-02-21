define(['jquery/parallax'], function () {
    return {
        startup: function () {
            $('.parallaxBg').parallax("50%", 0.2);
            $('.parallaxBg1').parallax("50%", 0.4);
        }
    }
});