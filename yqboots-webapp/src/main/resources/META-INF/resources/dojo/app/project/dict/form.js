define(['app/_base/PageRegistry', 'jquery/skyforms/maskedinput', 'jquery/skyforms/ui', 'jquery/skyforms/form'],
    function (PageRegistry) {
        return {
            startup: function () {
                PageRegistry.appendCss(PageRegistry.CSS_FORMS);
            }
        };
    });