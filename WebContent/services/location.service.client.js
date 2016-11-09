(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("LocationService", LocationService);
    
    
    function LocationService($http) {
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var service = {
    			createLocation : createLocation,
    			getAllLocations : getAllLocations,
    			getAllStates : getAllStates
    	};
    	
    	return service;
    	
    	function createLocation(location) {
    		if(location)
    			return $http ({
                    method: 'POST',
                    url: localServerURL + 'location',
                    contentType: 'application/json',
                    data: location});
    		
    	}
    	
    	function getAllLocations() {
    		return $http ({
                method: 'GET',
                url: localServerURL + 'getAllLocations',
                contentType: 'application/json'
                });
    	}
    	
    	function getAllStates() {
    		return $http ({
    			method : 'GET',
    			url : localServerURL + 'fetchField',
    			contentType: 'application/json',
    			params : {'fieldName' : 'State'}
    		});
    	}
    }
    
})();