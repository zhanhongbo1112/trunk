define(['baf/_base/dynamicRequire', 'baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/ScrollToTop', 'baf/util/StyleSwitcher'],
    function (dynReq, StickyHeader, MegaMenu, ScrollToTop, StyleSwitcher) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        // alert($.i18n.prop("hello"));
        var setLocale = function(locale) {
            var language = 'en';
            var country  = '';
            if(locale) {
                language = locale['language'];
                country = locale['country'];
            }

            if (country) {
                language = language + '_' + country;
            }

            $.i18n.properties({
                name:'messages',
                path:'/dojo/baf/nls/',
                mode:'both',
                language: language,
                async: true
            });
        };

        return {
            startup: function (templateName, locale) {
                setLocale(locale);
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