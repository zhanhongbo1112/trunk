define(['jquery/jquery-ui'], function () {
    return {
        startup: function () {
            $('#date').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });
        }
    };
});