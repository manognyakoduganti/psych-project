/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("LocationController", LocationController);
    

    function LocationController(LocationService)
    {
    	var vm = this;
        vm.tab = 'search';
        
    	 
	    LocationService
	 		.getAllStates()
	 		.success(function(response) {
	 			vm.statesList = response;
	 		});

        vm.setTab = function (tabId) {
            //console.log("Setting tab to " + tabId);
            vm.tab = tabId;
        };

        vm.isSet = function (tabId) {
            //console.log("Tab set to " + tabId);
            return vm.tab === tabId;
        };
        
        vm.create = create;
        
        function create(newLocation) {
        	
        	var Location = {
        		locationName : newLocation.name,
        		locationDescription : newLocation.description,
        		locationKeywords : newLocation.keywords,
        		locationCode : newLocation.code,
        		locationAddressLine1 : newLocation.address1,
        		locationAddressLine2 : newLocation.address2,
        		locationCity : newLocation.city,
        		locationState : newLocation.state,
        		locationZipcode : newLocation.zipcode,
        		locationPhoneNumber : newLocation.phoneNo,
        		locationFaxNumber : newLocation.faxNo,
        		locationEmail : newLocation.email
        		
        	};
        	LocationService
        		.createLocation(Location)
        		.success(function(response) {
        			if(response.status == '200') {
        			vm.isCreateSuccessful = true;
        			}
        		});
        }
        
        vm.search = search;
        
        function search(locationSearch) {
        	var locationParams = {
        			locationName : newLocation.name,
            		locationDescription : newLocation.description,
            		locationKeywords : newLocation.keywords,
            		locationCode : newLocation.code,
            		locationAddress : newLocation.address,
            		locationCity : newLocation.city,
            		locationState : newLocation.state,
            		locationZipcode : newLocation.zipcode,
            		locationPhoneNumber : newLocation.phoneNo,
            		locationFaxNumber : newLocation.faxNo,
            		locationEmail : newLocation.email	
        	};
        	LocationService
        		.getAllLocations(locationParams)
        }
    }
    

})();