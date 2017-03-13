define(['jquery/jquery-ui', 'jquery/form/maskedinput', 'jquery/form/validate'], function () {
    return {
        startup: function () {
            $('#date').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });

            $('#start').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>',
                onSelect: function (selectedDate) {
                    $('#finish').datepicker('option', 'minDate', selectedDate);
                }
            });

            $('#finish').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>',
                onSelect: function (selectedDate) {
                    $('#start').datepicker('option', 'maxDate', selectedDate);
                }
            });

            // Inline datepicker
            $('#inline').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>'
            });

            // Inline date range
            $('#inline-start').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>',
                onSelect: function (selectedDate) {
                    $('#inline-finish').datepicker('option', 'minDate', selectedDate);
                }
            });

            $('#inline-finish').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="fa fa-angle-left"></i>',
                nextText: '<i class="fa fa-angle-right"></i>',
                onSelect: function (selectedDate) {
                    $('#inline-start').datepicker('option', 'maxDate', selectedDate);
                }
            });

            $("#date1").mask('99/99/9999', {placeholder: 'X'});
            $("#phone").mask('(999) 999-9999', {placeholder: 'X'});
            $("#card").mask('9999-9999-9999-9999', {placeholder: 'X'});
            $("#serial").mask('***-***-***-***-***-***', {placeholder: '_'});
            $("#tax").mask('99-9999999', {placeholder: 'X'});

            $("#sky-form1").validate({
                // Rules for form validation
                rules: {
                    required: {
                        required: true
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    url: {
                        required: true,
                        url: true
                    },
                    date: {
                        required: true,
                        date: true
                    },
                    min: {
                        required: true,
                        minlength: 5
                    },
                    max: {
                        required: true,
                        maxlength: 5
                    },
                    range: {
                        required: true,
                        rangelength: [5, 10]
                    },
                    digits: {
                        required: true,
                        digits: true
                    },
                    number: {
                        required: true,
                        number: true
                    },
                    minVal: {
                        required: true,
                        min: 5
                    },
                    maxVal: {
                        required: true,
                        max: 100
                    },
                    rangeVal: {
                        required: true,
                        range: [5, 100]
                    }
                },

                // Messages for form validation
                messages: {
                    required: {
                        required: 'Please enter something'
                    },
                    email: {
                        required: 'Please enter your email address'
                    },
                    url: {
                        required: 'Please enter your URL'
                    },
                    date: {
                        required: 'Please enter some date'
                    },
                    min: {
                        required: 'Please enter some text'
                    },
                    max: {
                        required: 'Please enter some text'
                    },
                    range: {
                        required: 'Please enter some text'
                    },
                    digits: {
                        required: 'Please enter some digits'
                    },
                    number: {
                        required: 'Please enter some number'
                    },
                    minVal: {
                        required: 'Please enter some value'
                    },
                    maxVal: {
                        required: 'Please enter some value'
                    },
                    rangeVal: {
                        required: 'Please enter some value'
                    }
                },

                // Do not change code below
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });
        }
    }
});