define(['baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/SearchBox', 'baf/_base/LocaleSwitcher',
        'baf/_base/StyleSwitcher', 'baf/_base/ScrollToTop', 'jquery/cube-portfolio/cube-portfolio'],
    function (StickyHeader, MegaMenu, SearchBox, LocaleSwitcher, StyleSwitcher, ScrollToTop) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        return {
            startup: function () {
                StickyHeader.startup();
                MegaMenu.startup();
                SearchBox.startup();
                LocaleSwitcher.startup();
                StyleSwitcher.startup();
                ScrollToTop.startup();
            }
        };
    });