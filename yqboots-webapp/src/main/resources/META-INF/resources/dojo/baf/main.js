define(['baf/util/StickyHeader', 'baf/util/MegaMenu', 'baf/util/SearchBox', 'baf/util/LocaleSwitcher',
        'baf/util/StyleSwitcher', 'baf/util/ScrollToTop', 'jquery/cube-portfolio'],
        function(StickyHeader, MegaMenu, SearchBox, LocaleSwitcher, StyleSwitcher, ScrollToTop){
    // We extend jQuery by method hasAttr
    $.fn.hasAttr = function(name) {
      return this.attr(name) !== undefined;
    };

    return {
        startup : function() {
            StickyHeader.startup();
            MegaMenu.startup();
            SearchBox.startup();
            LocaleSwitcher.startup();
            StyleSwitcher.startup();
            ScrollToTop.startup();
        }
    };
});