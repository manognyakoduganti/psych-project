/**
 * Created by surajnagaraj on 10/27/16.
 */
(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("UserService", UserService);

    function UserService($http, $rootScope) {
    	
    	var serverURL = "http://ec2-52-207-248-229.compute-1.amazonaws.com:8080/Psych-1/";
    	var localServerURL = 'http://localhost:8080/Psych-1/';
        var service = {
            login: login,
            logout: logout,
            setCurrentUser: setCurrentUser,
            updateProfile: updateProfile,
            findIfUserLoggedIn: findIfUserLoggedIn
        };

        return service;

        function login(user) {

        	if(user) {
        		
        	console.log("Data being sent: " + user.email + " " + user.password);
            return $http ({
                method: 'POST',
                url: localServerURL + 'adminAuthentication',
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
        
        function updateProfile(userData) {
        	if(userData) {
        		
            	console.log("Data being sent: " + userData.firstName + " " + userData.lastName + " " + userData.email);
                return $http ({
                    method: 'PUT',
                    url: localServerURL + 'userProfile',
                    contentType: 'application/json',
                    data: userData
        		});
                    
            	}
        }
        
        function findIfUserLoggedIn() {

        	return $http ({
                method: 'GET',
                url: localServerURL + 'adminAuthentication?loggedIn=yes',
                contentType: 'application/json',
                data: ""
    		});
        }
    }
})();
