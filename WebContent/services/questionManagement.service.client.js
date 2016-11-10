(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("QuestionManagementService", QuestionManagementService);
    
    
    function QuestionManagementService($http) {
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var service = {
    	};
    	
    	return service;
    }
})();