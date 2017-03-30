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
                    message: {
                        required: true,
                        minlength: 10
                    },
                    captcha: {
                        required: true,
                        remote: 'assets/plugins/sky-forms-pro/skyforms/captcha/process.php'
                    }
                },

                // Messages for form validation
                messages: {
                    name: {
                        required: 'Please enter your name',
                    },
                    email: {
                        required: 'Please enter your email address',
                        email: 'Please enter a VALID email address'
                    },
                    message: {
                        required: 'Please enter your message'
                    },
                    captcha: {
                        required: 'Please enter characters',
                        remote: 'Correct captcha is required'
                    }
                },

                // Ajax form submission
                submitHandler: function (form) {
                    $(form).ajaxSubmit(
                        {
                            beforeSend: function () {
                                $('#' + formId + ' button[type="submit"]').attr('disabled', true);
                            },
                            success: function () {
                                $('#' + formId).addClass('submited');
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