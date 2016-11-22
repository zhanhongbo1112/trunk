define(['jquery/jquery-ui'], function () {
    return {
        startup: function () {
            $('#datetime').datetimepicker({
                controlType: 'select',
                oneLine: true,
                timeFormat: 'HH:mm:ss',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });
        }
    };
});