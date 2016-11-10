/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("LocationController", LocationController);
    

    function LocationController(LocationService)
    {
    	var sampleLocations = {
			"results": [
			{
			"locationName": "Northeastern University",
			"locationFaxNumber": "1234567891",
			"locationAddressLine1": "360 Huntington Ave",
			"locationDescription": "Location Decription",
			"locationAddressLine2": "",
			"locationPhoneNumber": "1234567891",
			"locationKeywords": "Keywords1, Keywords2, Keyword3",
			"locationZipCode": "2120",
			"locationId": "1",
			"locationCode": "ABCD12",
			"locationState": "Massachusetts",
			"locationCity": "Boston",
			"locationStateId": "21",
			"locationEmail": "northeastern@google.com"
			},
			{
			"locationName": "Mass General Hospital",
			"locationFaxNumber": "6177262000",
			"locationAddressLine1": "55 Fruit St",
			"locationDescription": "Massachusetts General Hospital is the original and largest teaching hospital of Harvard Medical School and a biomedical research facility located in the West End neighborhood of Boston, Massachusetts.",
			"locationAddressLine2": "",
			"locationPhoneNumber": "6177262000",
			"locationKeywords": "Boston, General",
			"locationZipCode": "2114",
			"locationId": "2",
			"locationCode": "CDEFGH",
			"locationState": "Massachusetts",
			"locationCity": "Boston",
			"locationStateId": "21",
			"locationEmail": "massgeneral@google.com"
			},
			{
			"locationName": "Booston",
			"locationFaxNumber": "2168016907",
			"locationAddressLine1": "asdf",
			"locationDescription": "",
			"locationAddressLine2": "",
			"locationPhoneNumber": "2168016907",
			"locationKeywords": "",
			"locationZipCode": "2120",
			"locationId": "3",
			"locationCode": "IESNA2",
			"locationState": "Massachusetts",
			"locationCity": "asldfkj",
			"locationStateId": "21",
			"locationEmail": "ddpatel.2012@gmail.com"
			},
			{
			"locationName": "Northeastern University TEST",
			"locationFaxNumber": "1234567891",
			"locationAddressLine1": "360 Huntington Avenue",
			"locationDescription": "Northeastern University is a private institution that was founded in 1898. It has a total undergraduate enrollment of 13,697, its setting is urban, and the campus size is 73 acres. It utilizes a semester-based academic calendar. Northeastern University's ranking in the 2017 edition of Best Colleges is National Universities, 39. Its tuition and fees are $47,655 (2016-17).",
			"locationAddressLine2": "",
			"locationPhoneNumber": "1234567891",
			"locationKeywords": "Northeastern, Psychology",
			"locationZipCode": "2115",
			"locationId": "5",
			"locationCode": "ABBDE2",
			"locationState": "Massachusetts",
			"locationCity": "Boston",
			"locationStateId": "21",
			"locationEmail": "northeastern@neu.edu"
			}
			],
			"status": "200"
    	};
    	var vm = this;
        vm.tab = 'search';
        vm.isLocationNameDuplicate = false;
        vm.isLocationCodeDuplicate = false;
        vm.isCreateSuccessful = false;
        vm.isSearchClicked = false;
        
        vm.newLocation = {
        		name : '',
        		description : '',
        		keywords : '',
        		code : '',
        		address1 : '',
        		address2 : '',
        		city : '',
        		state : '',
        		zipcode : '',
        		phoneNo : '',
        		faxNo : '',
        		email : ''
        };
        
        vm.searchLoc = {
        		name : '',
        		description : '',
        		keywords : '',
        		code : '',
        		address : '',
        		city : '',
        		state : '',
        		zipcode : '',
        		phoneNo : '',
        		faxNo : '',
        		email : ''
        };
        
       
    	 
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
        		locationZipCode : newLocation.zipcode,
        		locationPhoneNumber : newLocation.phoneNo,
        		locationFaxNumber : newLocation.faxNo,
        		locationEmail : newLocation.email
        		
        	};
        	console.log(Location);
        	
        	LocationService
        		.checkDuplicate(locationName, newLocation.name)
        		.then(function(response) {
        			if(response.results === 'true') {
        				LocationService
        					.checkDuplicate(locationCode, newLocation.code)
        					.then(function(response) {
        						if(response.results === "true") {
        							LocationService
        			        		.createLocation(Location)
        			        		.success(function(response) {
        			        			if(response.results == '200') {
        			        			vm.isCreateSuccessful = true;
        			        			}
        			        		});
        						}
        						
        						else 
        							vm.isLocationCodeDuplicate = true;
        				});
        			}
        			
        			else
        				vm.isLocationNameDuplicate = true;
        		})
        	
        }
        
        vm.search = search;
        var locationId = '';
        
        function search(locationSearch) {
        	vm.isSearchClicked = true;
        	
        	
        	
        	
        	var locationParams = {
        			locationName : locationSearch.name,
            		locationDescription : locationSearch.description,
            		locationKeywords : locationSearch.keywords,
            		locationCode : locationSearch.code,
            		locationAddress : locationSearch.address,
            		locationCity : locationSearch.city,
            		locationState : locationSearch.state.toString(),
            		locationZipCode : locationSearch.zipcode,
            		locationPhoneNumber : locationSearch.phoneNo,
            		locationFaxNumber : locationSearch.faxNo,
            		locationEmail : locationSearch.email	
        	};
        	var queryParams = {};
        	for (var param in locationParams) {
        		if(locationParams[param] != '')
        			console.log("param= " + param);
        			queryParams[param] = locationParams[param];
        	}
        	
        	if(queryParams) {
        		vm.locationSearchResults = jlinq.from(sampleLocations.results).select(queryParams);
        		console.log(vm.locationSearchResults);
        	}
        	else {
        		vm.locationSearchResults = jlinq.from(sampleLocations.results).select();
        		console.log(vm.locationSearchResults);
        	}
        	
        	/*LocationService
        		.getAllLocations(locationParams)
        		.success(function(response) {
        			locationId = response.locationId;
        		});*/
        	
        }
        
        vm.update = update;
        
        function update(locationUpdate) {
        	var locationUpdateParams = {
        			locationId : locationUpdate.locationId,
        			locationName : locationUpdate.name,
            		locationDescription : locationUpdate.description,
            		locationKeywords : locationUpdate.keywords,
            		locationCode : locationUpdate.code,
            		locationAddress : locationUpdate.address,
            		locationCity : locationUpdate.city,
            		locationState : locationUpdate.state.toString(),
            		locationZipCode : locationUpdate.zipcode,
            		locationPhoneNumber : locationUpdate.phoneNo,
            		locationFaxNumber : locationUpdate.faxNo,
            		locationEmail : locationUpdate.email	
        	};
        	
        	LocationService
    			.updateLocation(locationUpdateParams)
    			.success(function(response) {
    				vm.isUpdateSuccessful = true;
    				
    			});
        }
    }
    

})();