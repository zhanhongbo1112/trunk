define(['jquery/jquery-ui'], function () {
    return {
        startup: function () {
            $('#dateitem').datetimepicker({
                controlType: 'select',
                oneLine: true,
                dateFormat: 'yy-mm-dd',
                timeFormat: 'HH:mm:ss',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });
        }
    };
});