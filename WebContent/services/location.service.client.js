(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("LocationService", LocationService);
    
    
    function LocationService($http) {
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var awsURL = 'http://ec2-54-175-16-62.compute-1.amazonaws.com:8080/Psych-1';
    	var service = {
    			createLocation : createLocation,
    			getAllLocations : getAllLocations,
    			getAllStates : getAllStates,
    			updateLocation : updateLocation,
    			checkDuplicate : checkDuplicate
    	};
    	
    	return service;
    	
    	function createLocation(location) {
    		if(location)
    			return $http ({
                    method: 'POST',
                    url: awsURL + 'location',
                    contentType: 'application/json',
                    data: location});
    		
    	}
    	
    	function getAllLocations() {
    		return $http ({
                method: 'GET',
                url: awsURL + 'location',
                contentType: 'application/json'
                });
    	}
    	
    	function getAllStates() {
    		return $http ({
    			method : 'GET',
    			url : awsURL + 'fetchField',
    			contentType: 'application/json',
    			params : {'fieldName' : 'State'}
    		});
    	}
    	
    	function updateLocation(updatedLocation) {
    		return $http ({
                method: 'PUT',
                url: awsURL + 'location',
                contentType: 'application/json',
                data: updatedLocation});
    	}
    	
    	function checkDuplicate(param, value) {
    		return $http ({
    			method : 'GET',
    			url : awsURL + 'location' + '?' + param + "=" + value,
    			contentType: 'application/json'
    			//params : {param : value}
    		});
    	}
    }
    
})();