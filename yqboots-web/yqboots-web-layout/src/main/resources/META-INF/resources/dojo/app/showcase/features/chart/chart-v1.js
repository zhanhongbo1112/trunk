define(['jquery/circles-master/circles'], function () {
    return {
        startup: function () {
            // Circles 1
            Circles.create({
                id: 'circle-1',
                percentage: 87,
                radius: 80,
                width: 8,
                number: 87,
                text: '%',
                colors: ['#eee', '#72c02c'],
                duration: 2000
            });

            //Circles 2
            Circles.create({
                id: 'circle-2',
                percentage: 74,
                radius: 80,
                width: 8,
                number: 74,
                text: '%',
                colors: ['#eee', '#72c02c'],
                duration: 2000
            });

            //Circles 3
            Circles.create({
                id: 'circle-3',
                percentage: 65,
                radius: 80,
                width: 8,
                number: 65,
                text: '%',
                colors: ['#eee', '#72c02c'],
                duration: 2000
            });
        }
    }
});