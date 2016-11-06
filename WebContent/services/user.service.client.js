/**
 * Created by surajnagaraj on 10/27/16.
 */
(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("UserService", UserService);

    function UserService($http, $rootScope) {
        var service = {
            login: login,
            logout: logout,
            setCurrentUser: setCurrentUser
        };

        return service;

        function login(user) {

        	if(user) {
        		
        	console.log("Data being sent: " + user.email + " " + user.password);
            return $http ({
                method: 'POST',
                url: 'http://localhost:8080/Psych_Server/adminAuthentication',
                contentType: 'application/json',
                data: {
    				email: user.email,
    				password: user.password
    		}});
                
        	}



        }

        function logout() {
            $rootScope.currentUser = null;
        }

        function setCurrentUser(user) {
            $rootScope.currentUser = user;
        }
    }
})();
