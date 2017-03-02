define(['jquery/counter/waypoints', 'jquery/counter/counterup'], function () {
    return {
        startup: function () {
            $('.counter').counterUp({
                delay: 10,
                time: 1000
            });
        }
    }
});