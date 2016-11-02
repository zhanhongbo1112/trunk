define(['jquery/jquery-ui'], function () {
    return {
        startup: function () {
            $('#time').datetimepicker({
                controlType: 'select',
                oneLine: true,
                timeOnly:true,
                timeFormat: 'HH:mm'
            });
        }
    };
});