/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.web.layout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The entrance of a project.
 * it contains a main method to bootstrap the whole project.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/showcase")
    public String showcase() {
        return "showcase/index";
    }

    @RequestMapping(value = "/showcase/pages/login-v1")
    public String login_v1() {
        return "showcase/pages/login/login-v1";
    }

    @RequestMapping(value = "/showcase/pages/login-v2")
    public String login_v2() {
        return "showcase/pages/login/login-v2";
    }

    @RequestMapping(value = "/showcase/pages/registration-v1")
    public String registration_v1() {
        return "showcase/pages/registration/registration-v1";
    }

    @RequestMapping(value = "/showcase/pages/registration-v2")
    public String registration_v2() {
        return "showcase/pages/registration/registration-v2";
    }

    @RequestMapping(value = "/showcase/pages/404/404-v1")
    public String _404_v1() {
        return "showcase/pages/404/404-v1";
    }

    @RequestMapping(value = "/showcase/pages/about/about-v1")
    public String about_v1() {
        return "showcase/pages/about/about-v1";
    }

    @RequestMapping(value = "/showcase/pages/about/about-v2")
    public String about_v2() {
        return "showcase/pages/about/about-v2";
    }

    @RequestMapping(value = "/showcase/pages/about/about-v3")
    public String about_v3() {
        return "showcase/pages/about/about-v3";
    }

    @RequestMapping(value = "/showcase/pages/about/about-v4")
    public String about_v4() {
        return "showcase/pages/about/about-v4";
    }

    @RequestMapping(value = "/showcase/pages/about-me/about-me-v1")
    public String about_me_v1() {
        return "showcase/pages/about-me/about-me-v1";
    }

    @RequestMapping(value = "/showcase/pages/about-me/about-me-v2")
    public String about_me_v2() {
        return "showcase/pages/about-me/about-me-v2";
    }

    @RequestMapping(value = "/showcase/pages/about-me/about-me-v3")
    public String about_me_v3() {
        return "showcase/pages/about-me/about-me-v3";
    }

    @RequestMapping(value = "/showcase/pages/about-our-team/about-our-team-v1")
    public String about_our_team_v1() {
        return "showcase/pages/about-our-team/about-our-team-v1";
    }

    @RequestMapping(value = "/showcase/pages/about-our-team/about-our-team-v2")
    public String about_our_team_v2() {
        return "showcase/pages/about-our-team/about-our-team-v2";
    }

    @RequestMapping(value = "/showcase/pages/about-our-team/about-our-team-v3")
    public String about_our_team_v3() {
        return "showcase/pages/about-our-team/about-our-team-v3";
    }

    @RequestMapping(value = "/showcase/pages/contact/contact-v1")
    public String contact_v1() {
        return "showcase/pages/contact/contact-v1";
    }

    @RequestMapping(value = "/showcase/pages/contact/contact-v2")
    public String contact_v2() {
        return "showcase/pages/contact/contact-v2";
    }

    @RequestMapping(value = "/showcase/pages/contact/contact-v3")
    public String contact_v3() {
        return "showcase/pages/contact/contact-v3";
    }

    @RequestMapping(value = "/showcase/pages/faq/faq-v1")
    public String faq_v1() {
        return "showcase/pages/faq/faq-v1";
    }

    @RequestMapping(value = "/showcase/pages/faq/faq-v2")
    public String faq_v2() {
        return "showcase/pages/faq/faq-v2";
    }

    @RequestMapping(value = "/showcase/pages/services/services-v1")
    public String services_v1() {
        return "showcase/pages/services/services-v1";
    }

    @RequestMapping(value = "/showcase/pages/services/services-v2")
    public String services_v2() {
        return "showcase/pages/services/services-v2";
    }

    @RequestMapping(value = "/showcase/pages/services/services-v3")
    public String services_v3() {
        return "showcase/pages/services/services-v3";
    }

    @RequestMapping(value = "/showcase/pages/services/services-v4")
    public String services_v4() {
        return "showcase/pages/services/services-v4";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v1")
    public String search_v1() {
        return "showcase/pages/search/search-v1";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v2")
    public String search_v2() {
        return "showcase/pages/search/search-v2";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v3")
    public String search_v3() {
        return "showcase/pages/search/search-v3";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v4")
    public String search_v4() {
        return "showcase/pages/search/search-v4";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v5")
    public String search_v5() {
        return "showcase/pages/search/search-v5";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v6")
    public String search_v6() {
        return "showcase/pages/search/search-v6";
    }

    @RequestMapping(value = "/showcase/pages/search/search-v7")
    public String search_v7() {
        return "showcase/pages/search/search-v7";
    }

    @RequestMapping(value = "/showcase/pages/miscs/privacy-v1")
    public String privacy_v1() {
        return "showcase/pages/miscs/privacy-v1";
    }

    @RequestMapping(value = "/showcase/pages/miscs/terms-v1")
    public String terms_v1() {
        return "showcase/pages/miscs/terms-v1";
    }

    @RequestMapping(value = "/showcase/features/animation/animation-v1")
    public String animation_v1() {
        return "showcase/features/animation/animation-v1";
    }

    @RequestMapping(value = "/showcase/features/parallax-blocks/parallax-blocks-v1")
    public String parallax_blocks_v1() {
        return "showcase/features/parallax-blocks/parallax-blocks-v1";
    }

    @RequestMapping(value = "/showcase/features/call-action/call-action-v1")
    public String call_action_v1() {
        return "showcase/features/call-action/call-action-v1";
    }

    @RequestMapping(value = "/showcase/features/call-action/call-action-v2")
    public String call_action_v2() {
        return "showcase/features/call-action/call-action-v2";
    }

    @RequestMapping(value = "/showcase/features/funny-boxes/funny-boxes-v1")
    public String funny_boxes_v1() {
        return "showcase/features/funny-boxes/funny-boxes-v1";
    }

    @RequestMapping(value = "/showcase/features/funny-boxes/funny-boxes-v2")
    public String funny_boxes_v2() {
        return "showcase/features/funny-boxes/funny-boxes-v2";
    }

    @RequestMapping(value = "/showcase/features/funny-boxes/funny-boxes-v3")
    public String funny_boxes_v3() {
        return "showcase/features/funny-boxes/funny-boxes-v3";
    }

    @RequestMapping(value = "/showcase/features/gallery/gallery-v1")
    public String gallery_v1() {
        return "showcase/features/gallery/gallery-v1";
    }

    @RequestMapping(value = "/showcase/features/gallery/gallery-v2")
    public String gallery_v2() {
        return "showcase/features/gallery/gallery-v2";
    }

    @RequestMapping(value = "/showcase/features/gallery/gallery-v3")
    public String gallery_v3() {
        return "showcase/features/gallery/gallery-v3";
    }

    @RequestMapping(value = "/showcase/features/gallery/gallery-v4")
    public String gallery_v4() {
        return "showcase/features/gallery/gallery-v4";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v1")
    public String icon_blocks_v1() {
        return "showcase/features/icon-blocks/icon-blocks-v1";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v2")
    public String icon_blocks_v2() {
        return "showcase/features/icon-blocks/icon-blocks-v2";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v3")
    public String icon_blocks_v3() {
        return "showcase/features/icon-blocks/icon-blocks-v3";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v4")
    public String icon_blocks_v4() {
        return "showcase/features/icon-blocks/icon-blocks-v4";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v5")
    public String icon_blocks_v5() {
        return "showcase/features/icon-blocks/icon-blocks-v5";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v6")
    public String icon_blocks_v6() {
        return "showcase/features/icon-blocks/icon-blocks-v6";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v7")
    public String icon_blocks_v7() {
        return "showcase/features/icon-blocks/icon-blocks-v7";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v8")
    public String icon_blocks_v8() {
        return "showcase/features/icon-blocks/icon-blocks-v8";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v9")
    public String icon_blocks_v9() {
        return "showcase/features/icon-blocks/icon-blocks-v9";
    }

    @RequestMapping(value = "/showcase/features/icon-blocks/icon-blocks-v10")
    public String icon_blocks_v10() {
        return "showcase/features/icon-blocks/icon-blocks-v10";
    }

    @RequestMapping(value = "/showcase/features/parallax-counters/parallax-counters-v1")
    public String parallax_counters_v1() {
        return "showcase/features/parallax-counters/parallax-counters-v1";
    }

    @RequestMapping(value = "/showcase/features/parallax-counters/parallax-counters-v2")
    public String parallax_counters_v2() {
        return "showcase/features/parallax-counters/parallax-counters-v2";
    }

    @RequestMapping(value = "/showcase/features/parallax-counters/parallax-counters-v3")
    public String parallax_counters_v3() {
        return "showcase/features/parallax-counters/parallax-counters-v3";
    }

    @RequestMapping(value = "/showcase/features/parallax-counters/parallax-counters-v4")
    public String parallax_counters_v4() {
        return "showcase/features/parallax-counters/parallax-counters-v4";
    }

    @RequestMapping(value = "/showcase/features/parallax-counters/parallax-counters-v5")
    public String parallax_counters_v5() {
        return "showcase/features/parallax-counters/parallax-counters-v5";
    }

    @RequestMapping(value = "/showcase/features/news-blocks/news-blocks-v1")
    public String news_blocks_v1() {
        return "showcase/features/news-blocks/news-blocks-v1";
    }

    @RequestMapping(value = "/showcase/features/news-blocks/news-blocks-v2")
    public String news_blocks_v2() {
        return "showcase/features/news-blocks/news-blocks-v2";
    }

    @RequestMapping(value = "/showcase/features/news-blocks/news-blocks-v3")
    public String news_blocks_v3() {
        return "showcase/features/news-blocks/news-blocks-v3";
    }

    @RequestMapping(value = "/showcase/features/news-blocks/news-blocks-v4")
    public String news_blocks_v4() {
        return "showcase/features/news-blocks/news-blocks-v4";
    }

    @RequestMapping(value = "/showcase/features/news-blocks/news-blocks-v5")
    public String news_blocks_v5() {
        return "showcase/features/news-blocks/news-blocks-v5";
    }
}
