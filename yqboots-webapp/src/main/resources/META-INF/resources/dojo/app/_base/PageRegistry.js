define(['jquery'], function () {
    // register the pages, based on the template path
    return {
        CSS_CUBE_PORTFOLIO: ["../dojo/jquery/cube-portfolio/css/cubeportfolio.css",
            "../dojo/jquery/cube-portfolio/custom/custom-cubeportfolio.css"],
        CSS_PROFILE: ["../theme/css/pages/profile.css"],
        CSS_TIMELINE: ["../theme/css/pages/timeline.css"],

        PAGES: {
            "/index": "app/HomePage",
            "/profile/index": "app/profile/ProfilePage",
            "/prototype/project/index": "app/prototype/ProjectPage",
            "/prototype/model/index": "app/prototype/ModelPage"
        },

        appendCss: function (cssFiles) {
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