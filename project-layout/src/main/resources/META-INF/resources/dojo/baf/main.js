define(['baf/_base/dynamicRequire', 'baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/ScrollToTop', 'baf/util/StyleSwitcher'],
    function (dynReq, StickyHeader, MegaMenu, ScrollToTop, StyleSwitcher) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        return {
            startup: function (templateName) {
                StickyHeader.startup();
                MegaMenu.startup();
                StyleSwitcher.startup();
                ScrollToTop.startup();

                if (templateName) {
                    dynReq.dynamicRequire(['app/' + templateName]).then(function (Page) {
                        Page.startup();
                    });
                }
            }
        };
    });