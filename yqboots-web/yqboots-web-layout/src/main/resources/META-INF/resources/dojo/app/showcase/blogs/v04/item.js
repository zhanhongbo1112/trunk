define(['jquery/form/form', 'jquery/form/validate'], function () {
    return {
        startup: function () {
            this.initLoginForm();
            this.initContactForm();
        },

        initLoginForm: function () {
            // Validation for login form
            $("#sky-form1").validate({
                // Rules for form validation
                rules: {
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        minlength: 3,
                        maxlength: 20
                    }
                },

                // Messages for form validation
                messages: {
                    email: {
                        required: 'Please enter your email address',
                        email: 'Please enter a VALID email address'
                    },
                    password: {
                        required: 'Please enter your password'
                    }
                },

                // Do not change code below
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });

            // Validation for recovery form
            $("#sky-form2").validate({
                // Rules for form validation
                rules: {
                    email: {
                        required: true,
                        email: true
                    }
                },

                // Messages for form validation
                messages: {
                    email: {
                        required: 'Please enter your email address',
                        email: 'Please enter a VALID email address'
                    }
                },

                // Ajax form submition
                submitHandler: function (form) {
                    $(form).ajaxSubmit({
                        success: function () {
                            $("#sky-form2").addClass('submited');
                        }
                    });
                },

                // Do not change code below
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });
        },

        initContactForm: function () {
            // Validation
            $("#sky-form3").validate({
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

                // Ajax form submition
                submitHandler: function (form) {
                    $(form).ajaxSubmit({
                        beforeSend: function () {
                            $('#sky-form3 button[type="submit"]').attr('disabled', true);
                        },
                        success: function () {
                            $("#sky-form3").addClass('submited');
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