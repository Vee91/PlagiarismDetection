define([], function () {
    var routesConfig = {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: 'pages/plagiarism/home.html',
                dependencies: ['homeController'],
                cntrl: 'homeController',
                cntrlAs: 'model'
            }
        }
    };
    return routesConfig;
});