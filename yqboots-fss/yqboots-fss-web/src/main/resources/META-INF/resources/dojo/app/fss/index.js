define(['dojo/_base/lang', 'jquery/jstree/jstree'], function (lang) {
    return {
        startup: function () {
            $('#tree').on('loaded.jstree', function(e) {
                $(this).show();
                $('#treeLoading').hide();
            }).on('changed.jstree', function (e, data) {
                $('#criterion').val(data.selected[0]);
                $('#searchBtn').click();
            }).jstree();

            $('#homeBtn').on('click', function(e) {
                e.preventDefault();
                $('#criterion').val('/');
                $('#searchBtn').click();
            })
        }
    };
});