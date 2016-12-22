define(['jquery'], function () {
    return {
        formToObject: function (formId) {
            var result = {};
            $('#' + formId + ' [name]').each(function () {
                result[this.name] = this.value;
            });

            return result;
        }
    };
});