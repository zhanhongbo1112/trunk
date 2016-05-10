define([ 'jquery' ], function() {
    return {
        startup : function() {
            $(document).on('click', '.mega-menu .dropdown-menu', function(e) {
                e.stopPropagation();
            })
        }
    };
});