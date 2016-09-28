define(['baf/_base/dynamicRequire', 'baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/ScrollToTop', 'baf/util/StyleSwitcher',
        'baf/nls/NlsMessage'],
    function (dynReq, StickyHeader, MegaMenu, ScrollToTop, StyleSwitcher, NlsMessage) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        return {
            startup: function (templateName, locale) {
                NlsMessage.startup(locale);
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