define(['baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/SearchBox', 'baf/_base/StyleSwitcher',
        'baf/_base/ScrollToTop', 'baf/_base/dynamicRequire', 'jquery/cube-portfolio/cube-portfolio'],
    function (StickyHeader, MegaMenu, SearchBox, StyleSwitcher, ScrollToTop, dynReq) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        // register the pages, based on the template path
        var pages = {
            "/prototype/project/index": "baf/prototype/ProjectPage",
            "/prototype/model/index": "baf/prototype/ModelPage"
        };

        return {
            startup: function (templateName) {
                StickyHeader.startup();
                MegaMenu.startup();
                SearchBox.startup();
                StyleSwitcher.startup();
                ScrollToTop.startup();

                if (!pages[templateName]) {
                    console.info("No mapping page for template " + templateName);
                }
                if (pages[templateName]) {
                    dynReq.dynamicRequire([pages[templateName]]).then(function (Page) {
                        Page.startup();
                    });
                }
            }
        };
    });