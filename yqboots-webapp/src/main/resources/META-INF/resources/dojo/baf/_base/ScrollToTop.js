//** jQuery Scroll to Top Control script- (c) Dynamic Drive DHTML code library: http://www.dynamicdrive.com.
//** Available/ usage terms at http://www.dynamicdrive.com (March 30th, 09')
//** v1.1 (April 7th, 09'):
//** 1) Adds ability to scroll to an absolute position (from top of page) or specific element on the page instead.
//** 2) Fixes scroll animation not working in Opera.
define(['jquery'], function() {
    return {
        // startLine: Integer. Number of pixels from top of doc scrollbar is scrolled before showing control
    	// scrollTo: Keyword (Integer, or "Scroll_to_Element_ID"). How far to scroll document up when control is clicked on (0=top).
    	setting : {
            startLine : 100,
            scrollTo : 0,
            scrollDuration : 1000,
            fadeDuration : [ 500, 100 ]
        },
        
        // <img src="assets/img/up.png" style="width:51px; height:42px" />
        // HTML for control, which is auto wrapped in DIV w/ ID="topcontrol"
        controlHTML : '', 

    	// offset of control relative to right/ bottom of window corner
        controlAttrs : {
            offsetX : 5,
            offsetY : 5
        }, 

        // Enter href value of HTML anchors on the page that should also act as "Scroll Up" links
    	anchorKeyword: '#top',

    	state : {
            isVisible : false,
            shouldVisible : false
        },

    	scrollUp : function() {
            // if control is positioned using JavaScript
            if (!this.cssFixedSupport) {
                // hide control immediately after clicking it
                this.$control.css({
                    opacity : 0
                })
            }

            var dest = isNaN(this.setting.scrollTo) ? this.setting.scrollTo : parseInt(this.setting.scrollTo);
            // check element set by string exists
            if (typeof dest == "string" && $('#' + dest).length == 1) {
                dest = $('#' + dest).offset().top;
            } else {
                dest = 0;
            }

            this.$body.animate({
                scrollTop : dest
            }, this.setting.scrollDuration);
        },

    	keepFixed : function() {
            var $window = $(window);
            var controlX = $window.scrollLeft() + $window.width() - this.$control.width() - this.controlAttrs.offsetX;
            var controlY = $window.scrollTop() + $window.height() - this.$control.height() - this.controlAttrs.offsetY;
            this.$control.css({
                left : controlX + 'px',
                top : controlY + 'px'
            });
        },

    	toggleControl : function() {
            var scrollTop = $(window).scrollTop();
            if (!this.cssFixedSupport) {
                this.keepFixed();
            }
                
            this.state.shouldVisible = (scrollTop >= this.setting.startLine) ? true : false;
            if (this.state.shouldVisible && !this.state.isVisible) {
                this.$control.stop().animate({
                    opacity : 1
                }, this.setting.fadeDuration[0]);
                this.state.isVisible = true;
            } else if (this.state.shouldVisible == false && this.state.isVisible) {
                this.$control.stop().animate({
                    opacity : 0
                }, this.setting.fadeDuration[1]);
                this.state.isVisible = false;
            }
        },

        startup : function() {
            var _this = this;
            var iebrws = document.all;
            // not IE or IE7+ browsers in standards mode
            this.cssFixedSupport = !iebrws || iebrws && document.compatMode == "CSS1Compat" && window.XMLHttpRequest;
            this.$body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body'))
                    : $('html,body');
            this.$control = $('<div id="topcontrol">' + _this.controlHTML + '</div>').css({
                position : _this.cssFixedSupport ? 'fixed' : 'absolute',
                bottom : _this.controlAttrs.offsetY,
                right : _this.controlAttrs.offsetX,
                opacity : 0,
                cursor : 'pointer'
            }).attr({
                title : 'Scroll Back to Top'
            }).click(function() {
                _this.scrollUp();
                return false
            }).appendTo('body');
            // loose check for IE6 and below, plus whether control contains any
            // text
            if (document.all && !window.XMLHttpRequest && _this.$control.text() != '') {
                // IE6- seems to require an explicit width on a DIV containing
                // text
                _this.$control.css({
                    width : _this.$control.width()
                });
            }

            this.toggleControl();
            $('a[href="' + _this.anchorKeyword + '"]').click(function() {
                _this.scrollUp();
                return false;
            });

            $(window).bind('scroll resize', function(e) {
                _this.toggleControl();
            })
        }
    };
});