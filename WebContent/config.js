/**
 * Created by surajnagaraj on 10/26/16.
 */
(function(){
    'use strict';
    angular
        .module("PsychWebApp")
        .config(function($routeProvider, $httpProvider)
        {
            $routeProvider
                .when('/login', {

                    templateUrl: "./views/login.view.html",



                    controller: "LoginController",
                    controllerAs: "model"
                })
                .when('/profile', {

                    templateUrl: "./views/adminProfile.view.html",



                    controller: "AdminProfileController",
                    controllerAs: "model"
                })
                .when('/location', {

                    templateUrl: "./views/location.view.html",



                    controller: "LocationController",
                    controllerAs: "model"
                })
                .when('/targetgroup', {

                    templateUrl: "./views/targetGroup.view.html",



                    controller: "TargetGroupController",
                    controllerAs: "model"
                })
                .otherwise({
                    redirectTo: '/login'
                })
        })
})();
