define(['baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/ScrollToTop', 'baf/_base/dynamicRequire'],
    function (StickyHeader, MegaMenu, ScrollToTop, dynReq) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        return {
            startup: function (templateName) {
                StickyHeader.startup();
                MegaMenu.startup();
                ScrollToTop.startup();

                if (templateName) {
                    dynReq.dynamicRequire(['app/' + templateName]).then(function (Page) {
                        Page.startup();
                    });
                }
            }
        };
    });