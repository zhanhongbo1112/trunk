define(['baf/_base/dynamicRequire', 'jquery/jquery-i18n-properties'], function (dynReq) {
    return {
        startup: function (locale) {
            var language = 'en';
            var country = '';
            if (locale) {
                language = locale['language'];
                country = locale['country'];
            }

            if (country) {
                language = language + '_' + country;
            }

            $.i18n.properties({
                name: 'messages',
                path: '/dojo/baf/nls/',
                mode: 'both',
                language: language,
                async: true
            });

            dynReq.dynamicRequire(['baf/nls/datepicker']).then(function (datepicker) {
                $.datepicker.setDefaults(datepicker[language]);
            });

            dynReq.dynamicRequire(['baf/nls/timepicker']).then(function (timepicker) {
                $.timepicker.setDefaults(timepicker[language]);
            });
        }
    }
});