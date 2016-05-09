define(['jquery'], function(){
    var setColor = function (color, data_header) {
        $('#style_color').attr("href", "theme/css/theme-colors/" + color + ".css");
        if(data_header == 'light'){
            $('.logo img').attr("src", "theme/images/logo1-" + color + ".png");
            $('#logo-footer').attr("src", "theme/images/logo2-" + color + ".png");
            $('.navbar-brand img').attr("src", "theme/images/logo1-" + color + ".png");
        } else if(data_header == 'dark'){
            $('.logo img').attr("src", "theme/images/logo1-" + color + ".png");
            $('#logo-footer').attr("src", "theme/images/logo2-" + color + ".png");
        }
    };

    return {
        startup : function() {
            var panel = $('.style-switcher');

            $('.style-switcher-btn').click(function () {
                $('.style-switcher').show();
            });

            $('.theme-close').click(function () {
                $('.style-switcher').hide();
            });

            $('li', panel).click(function () {
                var color = $(this).attr("data-style");
                var data_header = $(this).attr("data-header");
                setColor(color, data_header);
                $('.list-unstyled li', panel).removeClass("theme-active");
                $(this).addClass("theme-active");
            });

            //Boxed Layout
            $('.skins-btn').click(function(){
                $(this).addClass("active-switcher-btn");
                $(".handle-skins-btn").removeClass("active-switcher-btn");
                $("body").addClass("dark");
            });

            $('.handle-skins-btn').click(function(){
                $(this).addClass("active-switcher-btn");
                $(".skins-btn").removeClass("active-switcher-btn");
                $("body").removeClass("dark");
            });


            //Boxed Layout
            $('.boxed-layout-btn').click(function(){
                $(this).addClass("active-switcher-btn");
                $(".wide-layout-btn").removeClass("active-switcher-btn");
                $("body").addClass("boxed-layout container");
            });

            $('.wide-layout-btn').click(function(){
                $(this).addClass("active-switcher-btn");
                $(".boxed-layout-btn").removeClass("active-switcher-btn");
                $("body").removeClass("boxed-layout container");
            });
        }
    };
});