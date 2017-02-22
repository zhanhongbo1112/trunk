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
}
