(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("LocationService", LocationService);
    
    function LocationService($http) {
    	var service = {
    			createLocation : createLocation
    	};
    	
    	return service;
    	
    	function createLocation(location) {
    		if(location)
    			return $http ({
                    method: 'POST',
                    url: 'http://localhost:8080/Psych_Server/createLocation',
                    contentType: 'application/json',
                    data: location});
    		
    	}
    }
    
})();