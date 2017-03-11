define(['jquery/circles-master/circles'], function () {
    return {
        startup: function () {
            var colors = [
                ['#D3B6C6', '#9B6BCC'], ['#C9FF97', '#72c02c'], ['#BEE3F7', '#3498DB'], ['#FFC2BB', '#E74C3C']
            ];

            for (var i = 1; i <= 4; i++) {
                var child = document.getElementById('circles-' + i), percentage = 45 + (i * 9);

                Circles.create({
                    id: child.id,
                    percentage: percentage,
                    radius: 70,
                    width: 2,
                    number: percentage / 1,
                    text: '%',
                    colors: colors[i - 1],
                    duration: 2000
                });
            }
        }
    }
});