define(['jquery/back-to-top', 'jquery/cube-portfolio'], function(){
    // We extend jQuery by method hasAttr
    $.fn.hasAttr = function(name) {
      return this.attr(name) !== undefined;
    };

    // Fixed Header
    function handleHeader() {
        $(window).scroll(function() {
          if ($(window).scrollTop() > 100) {
            $('.header-fixed .header-sticky').addClass('header-fixed-shrink');
          } else {
            $('.header-fixed .header-sticky').removeClass('header-fixed-shrink');
          }
        });
    }

    // Header Mega Menu
    function handleMegaMenu() {
        $(document).on('click', '.mega-menu .dropdown-menu', function(e) {
            e.stopPropagation();
        })
    }

    // Search Box (Header)
    function handleSearch() {
        $('.search').on("click", function () {
            if($('.search-btn').hasClass('fa-search')){
                $('.search-open').fadeIn(500);
                $('.search-btn').removeClass('fa-search');
                $('.search-btn').addClass('fa-times');
            } else {
                $('.search-open').fadeOut(500);
                $('.search-btn').addClass('fa-search');
                $('.search-btn').removeClass('fa-times');
            }
        });
    }

    // Hover Selector
    function handleHoverSelector() {
        $('.hoverSelector').on('click', function(e) {
          if ($(this).children('ul').hasClass('languages')) {
            if ($(this).children('ul').hasClass('languages-visible')) {
              $(this).children('.languages').slideUp();
              $(this).children('.languages').removeClass('languages-visible');
            } else {
              $(this).children('.languages').slideDown();
              $(this).children('.languages').addClass('languages-visible');
            }
          }
        });
    }

    //Style Switcher
    function initStyleSwitcher() {
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

        var setColor = function (color, data_header) {
            $('#style_color').attr("href", "assets/css/theme-colors/" + color + ".css");
            if(data_header == 'light'){
                $('.logo img').attr("src", "assets/img/themes/logo1-" + color + ".png");
                $('#logo-footer').attr("src", "assets/img/themes/logo2-" + color + ".png");
                $('.navbar-brand img').attr("src", "assets/img/themes/logo1-" + color + ".png");
            } else if(data_header == 'dark'){
                $('.logo img').attr("src", "assets/img/themes/logo1-" + color + ".png");
                $('#logo-footer').attr("src", "assets/img/themes/logo2-" + color + ".png");
            }
        }

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

    return {
        startup : function() {
            handleHeader();
            handleMegaMenu();
            handleSearch();
            handleHoverSelector();
            initStyleSwitcher();
        }
    };
});