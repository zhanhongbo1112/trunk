define(['baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/StyleSwitcher', 'baf/_base/ScrollToTop', 'baf/_base/dynamicRequire'],
    function (StickyHeader, MegaMenu, StyleSwitcher, ScrollToTop, dynReq) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        var APP_ROOT = "app/";

        return {
            startup: function (templateName) {
                StickyHeader.startup();
                MegaMenu.startup();
                StyleSwitcher.startup();
                ScrollToTop.startup();

                if (templateName) {
                    dynReq.dynamicRequire([APP_ROOT + templateName]).then(function (Page) {
                        Page.startup();
                    });
                }
            }
        };
    });