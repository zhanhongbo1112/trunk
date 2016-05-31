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

define(['baf/_base/StickyHeader', 'baf/_base/MegaMenu', 'baf/_base/SearchBox', 'baf/_base/StyleSwitcher',
        'baf/_base/ScrollToTop', 'jquery/cube-portfolio/cube-portfolio'],
    function (StickyHeader, MegaMenu, SearchBox, LocaleSwitcher, StyleSwitcher, ScrollToTop) {
        // We extend jQuery by method hasAttr
        $.fn.hasAttr = function (name) {
            return this.attr(name) !== undefined;
        };

        return {
            startup: function () {
                StickyHeader.startup();
                MegaMenu.startup();
                SearchBox.startup();
                StyleSwitcher.startup();
                ScrollToTop.startup();
            }
        };
    });