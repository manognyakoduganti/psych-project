/**
 * Created by surajnagaraj on 10/26/16.
 */
//awsURL : http://ec2-54-175-16-62.compute-1.amazonaws.com:8080/Psych-1/
//localURL : http://localhost:8080/Psych-1/
(function(){
    'use strict';
    angular
        .module("PsychWebApp")
        .constant("serverURL", { url : 'http://localhost:8080/Psych-1/'})
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
                    controllerAs: "model",
                    resolve:{
                    	loggedin: RedirectToPageIfLoggedIn
                    }
                })
                .when('/password', {

                    templateUrl: "./views/changePassword.view.html",
                    controller: "AdminProfileController",
                    controllerAs: "model",
                    resolve:{
                    	loggedin: RedirectToPageIfLoggedIn
                    }
                })
                .when('/location', {

                    templateUrl: "./views/location.view.html",
                    controller: "LocationController",
                    controllerAs: "model",
                    resolve:{
                    	loggedin: RedirectToPageIfLoggedIn
                    }
                })
                .when('/targetgroup', {

                    templateUrl: "./views/targetGroup.view.html",
                    controller: "TargetGroupController",
                    controllerAs: "model",
                    resolve:{
                    	loggedin: RedirectToPageIfLoggedIn
                    }
                })
                .when('/questionManagement', {

                    templateUrl: "./views/questionManagement.view.html",
                    controller: "QuestionManagementController",
                    controllerAs: "model",
                    resolve:{
                    	loggedin: RedirectToPageIfLoggedIn
                    }
                })
                 .when('/imageManagement', {

                    templateUrl: "./views/imageManagement.view.html",
                    controller: "ImageManagementController",
                    controllerAs: "model",
                    resolve:{
                    	loggedin: RedirectToPageIfLoggedIn
                    }
                })
                .otherwise({
                    redirectTo: '/login'
                })
        })
        
        function RedirectToPageIfLoggedIn(UserService, $rootScope, $location) {
        	UserService
            .findIfUserLoggedIn()
            .success(function (response) {
            	if(response.status == '200') {
                    var user = {
                		firstName : response.firstName,
                		lastName : response.lastName,
                		email : response.email,
                		role: response.role
                    };
                    UserService.setCurrentUser(user);
            	}
                else {
                	UserService.logout();
                    $rootScope.user = null;
                    $rootScope.errorMessage = 'Please log in to continue.';
                    $location.url('/login');
                }

            });
    }
})();
