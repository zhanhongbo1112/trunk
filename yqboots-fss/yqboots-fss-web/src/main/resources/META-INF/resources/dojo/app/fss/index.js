define(['dojo/_base/lang', 'jquery/jstree/jstree'], function (lang) {
    return {
        startup: function () {
            $('#tree').jstree();
            $('#file').change(lang.hitch(this, '_onChangeFile'));
        },

        _onChangeFile : function(e) {
            var inputFile = $(e.target);
            inputFile.parent().next().val(inputFile.val());
        }
    };
});