(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("QuestionManagementService", QuestionManagementService);
    
    
    function QuestionManagementService($http) {
    	
    	var awsURL = 'http://ec2-54-175-16-62.compute-1.amazonaws.com:8080/Psych-1/';
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var serverURL = 'http://localhost:8080/Psych-1/';
    	
    	var service = {
    	};
    	
    	return service;
    }
})();