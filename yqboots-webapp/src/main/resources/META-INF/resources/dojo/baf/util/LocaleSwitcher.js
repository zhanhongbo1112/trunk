define(['jquery'], function(){
    return {
        startup : function() {
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
    };
});