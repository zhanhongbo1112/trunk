define(['jquery/form/validate'], function () {
    return {
        startup: function (formId) {
            // Validation
            $('#' + formId).validate({
                // Rules for form validation
                rules: {
                    name: {
                        required: true
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    url: {
                        url: true
                    },
                    comment: {
                        required: true
                    }
                },

                // Messages for form validation
                messages: {
                    name: {
                        required: 'Enter your name'
                    },
                    email: {
                        required: 'Enter your email address',
                        email: 'Enter a VALID email'
                    },
                    url: {
                        email: 'Enter a VALID url'
                    },
                    comment: {
                        required: 'Please enter your comment'
                    },
                    captcha: {
                        required: 'Please enter characters',
                        remote: 'Correct captcha is required'
                    }
                },

                // Ajax form submission
                submitHandler: function (form) {
                    $(form).ajaxSubmit({
                        beforeSend: function () {
                            $('#' + formId + ' button[type="submit"]').attr('disabled', true);
                        },
                        success: function () {
                            $('#' + formId).addClass('submitted');
                        }
                    });
                },

                // Do not change code below
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });
        }
    }
});