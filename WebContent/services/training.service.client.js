(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("TrainingService", TrainingService);
    
    
    function TrainingService($http) {
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var service = {
    			getAllTrainings : getAllTrainings
    	};
    	
    	return service;
    	
    	function getAllTrainings() {
    		return $http ({
    			method : 'GET',
    			url : localServerURL + 'training',
    			contentType: 'application/json',
    			params : {'dropDown' : 'yes'}
    		});
    	}
    }
})();