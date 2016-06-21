define(['baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/SearchBox', 'baf/_base/StyleSwitcher',
        'baf/_base/ScrollToTop', 'baf/_base/dynamicRequire'],
    function (StickyHeader, MegaMenu, SearchBox, StyleSwitcher, ScrollToTop, dynReq) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        return {
            startup: function (templateName, pages) {
                StickyHeader.startup();
                MegaMenu.startup();
                SearchBox.startup();
                StyleSwitcher.startup();
                ScrollToTop.startup();

                var template = pages[templateName];
                if (!template) {
                    console.info("No mapping page for template: " + templateName);
                }

                if (pages[templateName]) {
                    dynReq.dynamicRequire([pages[templateName]]).then(function (Page) {
                        Page.startup();
                    });
                }
            }
        };
    });