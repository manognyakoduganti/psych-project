(function() {
    'use strict';
    angular
        .module("PsychWebApp")
        .factory("TargetGroupService", TargetGroupService);
    
    
    function TargetGroupService($http) {
    	var localServerURL = 'http://localhost:8080/Psych-1/';
    	var awsURL = 'http://ec2-54-175-16-62.compute-1.amazonaws.com:8080/Psych-1/';
    	var service = {
    			createTargetGroup : createTargetGroup,
    			getAllTargetGroups : getAllTargetGroups,
    			updateTargetGroup : updateTargetGroup,
    			checkDuplicate : checkDuplicate
    	};
    	
    	return service;
    	
    	function createTargetGroup(targetGroup) {
    		if(targetGroup)
    			return $http ({
                    method: 'POST',
                    url: awsURL + 'targetGroup',
                    contentType: 'application/json',
                    data: targetGroup});
    		
    	}
    	
    	function getAllTargetGroups() {
    		return $http ({
                method: 'GET',
                url: awsURL + 'targetGroup',
                contentType: 'application/json'
                });
    	}
    	
    	function updateTargetGroup(updatedTargetGroup) {
    		return $http ({
                method: 'PUT',
                url: awsURL + 'targetGroup',
                contentType: 'application/json',
                data: updatedTargetGroup});
    	}
    	
    	function checkDuplicate(param, value) {
    		return $http ({
    			method : 'GET',
    			url : awsURL + 'targetGroup',
    			contentType: 'application/json',
    			params : {param : value}
    		});
    	}
    }
    
})();