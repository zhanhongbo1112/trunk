define(['baf/nls/datepicker', 'jquery', 'jquery/jquery-ui', 'jquery/jquery-ui-timepicker'], function (dMessages) {
    return {
        'en': {
            prevText: '<i class="fa fa-angle-left"></i>', // Display text for previous month link
            nextText: '<i class="fa fa-angle-right"></i>', // Display text for next month link
            currentText: 'Now',
            closeText: 'Done',
            amNames: ['AM', 'A'],
            pmNames: ['PM', 'P'],
            timeSuffix: '',
            timeOnlyTitle: 'Choose Time',
            timeText: 'Time',
            hourText: 'Hour',
            minuteText: 'Minute',
            secondText: 'Second',
            millisecText: 'Millisecond',
            microsecText: 'Microsecond',
            timezoneText: 'Time Zone',
            isRTL: false,
            dateFormat: 'yy-mm-dd', // See format options on parseDate
            timeFormat: 'HH:mm:ss',
            controlType: 'select',
            oneLine: true
        },

        'zh': {
            prevText: '<i class="fa fa-angle-left"></i>', // Display text for previous month link
            nextText: '<i class="fa fa-angle-right"></i>', // Display text for next month link
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'], // Names of months for drop-down and formatting
            monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'], // For formatting
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'], // For formatting
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'], // For formatting
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'], // Column headings for days starting at Sunday
            weekHeader: '周', // Column header for week of the year
            currentText: '当前时间',
            closeText: '完成',
            amNames: ['上午', 'A'],
            pmNames: ['下午', 'P'],
            timeSuffix: '',
            timeOnlyTitle: '选择时间',
            timeText: '时间',
            hourText: '小时',
            minuteText: '分钟',
            secondText: '秒',
            millisecText: '毫秒',
            microsecText: '微秒',
            timezoneText: '时区',
            isRTL: false,
            dateFormat: 'yy-mm-dd', // See format options on parseDate
            timeFormat: 'HH:mm:ss',
            controlType: 'select',
            oneLine: true
        },

        'zh_CN': {
            prevText: '<i class="fa fa-angle-left"></i>', // Display text for previous month link
            nextText: '<i class="fa fa-angle-right"></i>', // Display text for next month link
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'], // Names of months for drop-down and formatting
            monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'], // For formatting
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'], // For formatting
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'], // For formatting
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'], // Column headings for days starting at Sunday
            weekHeader: '周', // Column header for week of the year
            currentText: '当前时间',
            closeText: '完成',
            amNames: ['上午', 'A'],
            pmNames: ['下午', 'P'],
            timeSuffix: '',
            timeOnlyTitle: '选择时间',
            timeText: '时间',
            hourText: '小时',
            minuteText: '分钟',
            secondText: '秒',
            millisecText: '毫秒',
            microsecText: '微秒',
            timezoneText: '时区',
            isRTL: false,
            dateFormat: 'yy-mm-dd', // See format options on parseDate
            timeFormat: 'HH:mm:ss',
            controlType: 'select',
            oneLine: true
        }
    };
});