define(['jquery', 'jquery/jquery-ui'], function () {
    return {
        'en': {
            closeText: 'Done', // Display text for close link
            prevText: '<i class="fa fa-angle-left"></i>', // Display text for previous month link
            nextText: '<i class="fa fa-angle-right"></i>', // Display text for next month link
            currentText: 'Today', // Display text for current month link
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'], // Names of months for drop-down and formatting
            monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], // For formatting
            dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'], // For formatting
            dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'], // For formatting
            dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'], // Column headings for days starting at Sunday
            weekHeader: 'Wk', // Column header for week of the year
            dateFormat: 'yy-mm-dd', // See format options on parseDate
            firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
            isRTL: false, // True if right-to-left language, false if left-to-right
            showMonthAfterYear: false, // True if the year select precedes month, false for month then year
            yearSuffix: '' // Additional text to append to the year in the month headers
        },

        'zh': {
            closeText: '完成', // Display text for close link
            prevText: '<i class="fa fa-angle-left"></i>', // Display text for previous month link
            nextText: '<i class="fa fa-angle-right"></i>', // Display text for next month link
            currentText: '今天', // Display text for current month link
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'], // Names of months for drop-down and formatting
            monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'], // For formatting
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'], // For formatting
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'], // For formatting
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'], // Column headings for days starting at Sunday
            weekHeader: '周', // Column header for week of the year
            dateFormat: 'yy-mm-dd', // See format options on parseDate
            firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
            isRTL: false, // True if right-to-left language, false if left-to-right
            showMonthAfterYear: false, // True if the year select precedes month, false for month then year
            yearSuffix: '' // Additional text to append to the year in the month headers
        },

        'zn_CN': {
            closeText: '完成', // Display text for close link
            prevText: '<i class="fa fa-angle-left"></i>', // Display text for previous month link
            nextText: '<i class="fa fa-angle-right"></i>', // Display text for next month link
            currentText: '今天', // Display text for current month link
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'], // Names of months for drop-down and formatting
            monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'], // For formatting
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'], // For formatting
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'], // For formatting
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'], // Column headings for days starting at Sunday
            weekHeader: '周', // Column header for week of the year
            dateFormat: 'yy-mm-dd', // See format options on parseDate
            firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
            isRTL: false, // True if right-to-left language, false if left-to-right
            showMonthAfterYear: false, // True if the year select precedes month, false for month then year
            yearSuffix: '' // Additional text to append to the year in the month headers
        }
    }
});