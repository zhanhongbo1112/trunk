define(['jquery'], function () {
    return {
        moveSelectedOptions: function (fromId, toId) {
            $('#' + fromId + ' option:selected').each(function () {
                // move from source to target select, no action when the target select has the moved option
                $(this).appendTo($('#' + toId + ':not(:has(option[value=' + $(this).val() + ']))'));
                // clear the moved option from source select
                var options = $('#' + fromId + ' option[value=' + $(this).val() + ']');
                if (options.length > 0) {
                    $('#' + fromId).get(0).removeChild(options.get(0));
                }
            });
        },

        moveAllOptions: function (fromId, toId) {
            $('#' + fromId + ' option').each(function () {
                // move from source to target select, no action when the target select has the moved option
                $(this).appendTo($('#' + toId + ':not(:has(option[value=' + $(this).val() + ']))'));
            });

            // clear all options from source select
            $('#' + fromId).empty();
        },

        appendSelectedOptions: function (fromId, toId) {
            // just append, never remove options from source
            $('#' + fromId + ' option:selected').each(function () {
                $('<option></option>')
                    .val($(this).val())
                    .text($(this).text())
                    .appendTo($('#' + toId + ':not(:has(option[value=' + $(this).val() + ']))'));
            });
        },

        appendAllOptions: function (fromId, toId) {
            // just append, never remove options from source
            $('#' + fromId + ' option').each(function () {
                $('<option></option>')
                    .val($(this).val())
                    .text($(this).text())
                    .appendTo($('#' + toId + ':not(:has(option[value=' + $(this).val() + ']))'));
            });
        },

        getSelectedOptions: function (selectNodeId) {
            return $('#' + selectNodeId + ' option:selected').map(function () {
                return $(this).val();
            }).get();
        },

        getAllOptions: function (selectNodeId) {
            return $('#' + selectNodeId + ' option').map(function () {
                return $(this).val();
            }).get();
        },

        sort: function (selectNodeId) {
            $('#' + selectNodeId + ' option').sort(function (a, b) {
                var aText = $(a).text().toUpperCase();
                var bText = $(b).text().toUpperCase();
                if (aText > bText) {
                    return 1
                }
                if (aText < bText) {
                    return -1
                }

                return 0;
            }).appendTo('#' + selectNodeId);
        }
    }
});