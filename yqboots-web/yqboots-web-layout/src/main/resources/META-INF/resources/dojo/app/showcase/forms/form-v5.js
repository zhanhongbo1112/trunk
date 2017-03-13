define(['jquery/jquery-ui', 'jquery/form/maskedinput', 'jquery/form/validate'], function () {
    return {
        startup: function () {
            this.initOrderForm();
            this.initReviewForm();
            this.initCheckoutForm();
        },

        initOrderForm: function () {
            // Datepickers
            $('#start').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="icon-chevron-left"></i>',
                nextText: '<i class="icon-chevron-right"></i>',
                onSelect: function (selectedDate) {
                    $('#finish').datepicker('option', 'minDate', selectedDate);
                }
            });
            $('#finish').datepicker({
                dateFormat: 'yy-mm-dd',
                prevText: '<i class="icon-chevron-left"></i>',
                nextText: '<i class="icon-chevron-right"></i>',
                onSelect: function (selectedDate) {
                    $('#start').datepicker('option', 'maxDate', selectedDate);
                }
            });

            // Validation
            $("#sky-form1").validate({
                // Rules for form validation
                rules: {
                    name: {
                        required: true
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    phone: {
                        required: true
                    },
                    interested: {
                        required: true
                    },
                    budget: {
                        required: true
                    }
                },

                // Messages for form validation
                messages: {
                    name: {
                        required: 'Please enter your name'
                    },
                    email: {
                        required: 'Please enter your email address',
                        email: 'Please enter a VALID email address'
                    },
                    phone: {
                        required: 'Please enter your phone number'
                    },
                    interested: {
                        required: 'Please select interested service'
                    },
                    budget: {
                        required: 'Please select your budget'
                    }
                },

                // Ajax form submition
                submitHandler: function (form) {
                    $(form).ajaxSubmit({
                        beforeSend: function () {
                            $('#sky-form1 button[type="submit"]').addClass('button-uploading').attr('disabled', true);
                        },
                        uploadProgress: function (event, position, total, percentComplete) {
                            $("#sky-form1 .progress").text(percentComplete + '%');
                        },
                        success: function () {
                            $("#sky-form1").addClass('submited');
                            $('#sky-form1 button[type="submit"]').removeClass('button-uploading').attr('disabled', false);
                        }
                    });
                },

                // Do not change code below
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });
        },

        //Review Form
        initReviewForm: function () {
            // Validation
            $("#sky-form2").validate({
                // Rules for form validation
                rules: {
                    name: {
                        required: true
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    review: {
                        required: true,
                        minlength: 20
                    },
                    quality: {
                        required: true
                    },
                    reliability: {
                        required: true
                    },
                    overall: {
                        required: true
                    }
                },

                // Messages for form validation
                messages: {
                    name: {
                        required: 'Please enter your name'
                    },
                    email: {
                        required: 'Please enter your email address',
                        email: 'Please enter a VALID email address'
                    },
                    review: {
                        required: 'Please enter your review'
                    },
                    quality: {
                        required: 'Please rate quality of the product'
                    },
                    reliability: {
                        required: 'Please rate reliability of the product'
                    },
                    overall: {
                        required: 'Please rate the product'
                    }
                },

                // Do not change code below
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });
        },

        initCheckoutForm: function () {
            // Masking
            $('#cvv').mask('999', {placeholder: 'X'});
            $('#card').mask('9999-9999-9999-9999', {placeholder: 'X'});
            $('#year').mask('2099', {placeholder: 'X'});

            // Add validation method
            $.validator.addMethod("creditcard", function (value, element, param) {
                if (/[^0-9\-]+/.test(value)) {
                    return false;
                }
            }, $.validator.format('Please enter a valid credit card number.'));

            // Validation
            $('#sky-form').validate({
                // Rules for form validation
                rules: {
                    fname: {
                        required: true
                    },
                    lname: {
                        required: true
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    phone: {
                        required: true
                    },
                    country: {
                        required: true
                    },
                    city: {
                        required: true
                    },
                    code: {
                        required: true,
                        digits: true
                    },
                    address: {
                        required: true
                    },
                    name: {
                        required: true
                    },
                    card: {
                        required: true,
                        creditcard: true
                    },
                    cvv: {
                        required: true,
                        digits: true
                    },
                    month: {
                        required: true
                    },
                    year: {
                        required: true,
                        digits: true
                    }
                },

                // Messages for form validation
                messages: {
                    fname: {
                        required: 'Please enter your first name'
                    },
                    lname: {
                        required: 'Please enter your last name'
                    },
                    email: {
                        required: 'Please enter your email address',
                        email: 'Please enter a VALID email address'
                    },
                    phone: {
                        required: 'Please enter your phone number'
                    },
                    country: {
                        required: 'Please select your country'
                    },
                    city: {
                        required: 'Please enter your city'
                    },
                    code: {
                        required: 'Please enter code',
                        digits: 'Digits only please'
                    },
                    address: {
                        required: 'Please enter your full address'
                    },
                    name: {
                        required: 'Please enter name on your card'
                    },
                    card: {
                        required: 'Please enter your card number'
                    },
                    cvv: {
                        required: 'Enter CVV2',
                        digits: 'Digits only'
                    },
                    month: {
                        required: 'Select month'
                    },
                    year: {
                        required: 'Enter year',
                        digits: 'Digits only please'
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