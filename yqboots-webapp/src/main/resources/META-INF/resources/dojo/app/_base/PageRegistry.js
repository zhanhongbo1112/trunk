define(['jquery'], function () {
    // check whether the css files are merged into application.css
    var cssMerged = false;

    // register the pages, based on the template path
    return {
        CSS_CUBE_PORTFOLIO: ["../dojo/jquery/cube-portfolio/css/cubeportfolio.css",
            "../dojo/jquery/cube-portfolio/custom/custom-cubeportfolio.css"],
        CSS_ERROR: ["../theme/css/pages/error.css"],
        CSS_PROFILE: ["../theme/css/pages/profile.css"],
        CSS_TIMELINE: ["../theme/css/pages/timeline.css"],
        CSS_FORMS: ["../dojo/jquery/skyforms/css/sky-forms.css", "../dojo/jquery/skyforms/custom/custom-sky-forms.css"],

        PAGES: {
            "error": "app/ErrorPage",
            "/index": "app/HomePage",
            "/profile/index": "app/profile/ProfilePage",
            "/prototype/project/index": "app/prototype/ProjectPage",
            "/prototype/model/index": "app/prototype/ModelPage"
        },

        appendCss: function (cssFiles) {
            if (cssMerged) {
                return;
            }
            $.each(cssFiles, function (i, cssFile) {
                $("head").append("<link>");
                var css = $("head").children(":last");
                css.attr({
                    rel: "stylesheet",
                    type: "text/css",
                    href: cssFile
                });
            });
        }
    };
});