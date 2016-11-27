(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("FieldLookupService", FieldLookupService);
    
    
    function FieldLookupService($http, serverURL) {
    	/*var awsURL = 'http://ec2-54-175-16-62.compute-1.amazonaws.com:8080/Psych-1/';
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var serverURL = 'http://localhost:8080/Psych-1/';*/
    	
    	var service = {
    			fetchFields : fetchFields
    	};
    	
    	return service;
    	
    	function fetchFields(fieldGroup){
    		return $http ({
    			method : 'GET',
    			url : serverURL.url + 'fetchField',
    			contentType: 'application/json',
    			params: {'fieldName': fieldGroup}
    		});
    	}
    	
    	
    }
})();