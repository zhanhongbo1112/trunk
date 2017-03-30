define(['app/showcase/forms/LoginForm', 'app/showcase/forms/ContactForm',
        'app/showcase/forms/RegistrationForm', 'app/showcase/forms/CommentForm'],
    function (LoginForm, ContactForm, RegistrationForm, CommentForm) {
        return {
            startup: function () {
                LoginForm.startup("sky-form2");
                ContactForm.startup("sky-form3");
                RegistrationForm.startup("sky-form4");
                CommentForm.startup("sky-form5");
            }
        }
    });