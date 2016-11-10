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
        vm.isLocationNameDuplicate = false;
        vm.isLocationCodeDuplicate = false;
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
        
vm.checkDuplicate = checkDuplicate;
        
        function checkDuplicate(param, value){
        	//console.log(param + " " + value);
        	
        	if (param == 'locationName'){
        		LocationService
        		.checkDuplicate(param, value)
        		.then(function(response) {
        			if(response.data.results == true) {
        				vm.locationCreateForm.createLocationName.$error.locationNameError = true;
        			}
        			else{
        				vm.locationCreateForm.createLocationName.$error.locationNameError = false;
        			}
        		})
        	}
        	
        	if (param == 'locationCode'){
        		LocationService
        		.checkDuplicate(param, value)
        		.then(function(response) {
        			if(response.data.results == true) {
        				vm.locationCreateForm.createLocationCode.$error.locationCodeError = true;
        			}
        			else{
        				vm.locationCreateForm.createLocationCode.$error.locationCodeError = false;
        			}
        		})
        	}
        	
        }
        
        function create(newLocation) {
        	
        	vm.isCreateFailed = false;
        	vm.isCreateSuccessful = false;
        	
        	var Location = {
        		locationName : newLocation.name,
        		locationDescription : newLocation.description,
        		locationKeywords : newLocation.keywords,
        		locationCode : newLocation.code,
        		locationAddressLine1 : newLocation.address1,
        		locationAddressLine2 : newLocation.address2,
        		locationCity : newLocation.city,
        		locationStateId : newLocation.state.toString(),
        		locationZipCode : newLocation.zipcode,
        		locationPhoneNumber : newLocation.phoneNo,
        		locationFaxNumber : newLocation.faxNo,
        		locationEmail : newLocation.email
        		
        	};
        	console.log(Location);
        	
        	/*LocationService
        		.checkDuplicate('locationName', newLocation.name)
        		.then(function(response) {
        			if(response.results === 'true') {
        				LocationService
        					.checkDuplicate('locationCode', newLocation.code)
        					.then(function(response) {
        						if(response.results === "true") {
        							
        						}
        						
        						else 
        							vm.isLocationCodeDuplicate = true;
        				});
        			}
        			
        			else
        				vm.isLocationNameDuplicate = true;
        		})*/
        	
        	LocationService
    		.createLocation(Location)
    		.success(function(response) {
    			if(response.status == '200') {
    			vm.isCreateSuccessful = true;
    			
    			}
    			
    			else
    				vm.isCreateFailed = true;
    				
    		});
        	
        }
        
        vm.search = search;
        var locationId = '';
        
        function search(locationSearch) {
        	
        	var locationsList = [];
        	
        	LocationService
        		.getAllLocations()
        		.success(function(response) {
        			locationsList = response.results;
        		
        			console.log(locationSearch);
                	var locationParams = {
                			locationName : locationSearch.name,
                    		locationDescription : locationSearch.description,
                    		locationKeywords : locationSearch.keywords,
                    		locationCode : locationSearch.code,
                    		locationAddress : locationSearch.address,
                    		locationCity : locationSearch.city,
                    		locationStateId : locationSearch.state.toString(),
                    		locationZipCode : locationSearch.zipcode,
                    		locationPhoneNumber : locationSearch.phoneNo,
                    		locationFaxNumber : locationSearch.faxNo,
                    		locationEmail : locationSearch.email	
                	};
                	
                	var keys = [];
                	var searchString = "";
                	var searchList = [];
                	for (var param in locationParams) {
                		console.log(param + " " + locationParams[param]);
                		if(locationParams[param] != ''){
                			searchList.push(locationParams[param]);
                			keys.push(param);
                		}
                	}
                	
                	if(keys.length > 0) {
                		var options = {
                    			shouldSort: true,
                    			tokenize: true,
                    			threshold: 0.3,
                    			location: 0,
                    			distance: 10,
                    			//maxPatternLength: 32,
                    			keys: keys
                    	}
                    	console.log(1);
                		console.log(keys);
                		console.log(searchList);
                    	var fuse = new Fuse(locationsList, options);

                    	var results = fuse.search(searchList.join(" "));
                		vm.locationSearchResults = results;
                	}
                	else {
                		vm.locationSearchResults = locationsList;
                		console.log("else");
                		console.log(vm.locationSearchResults);
                	}
                	
                	vm.isSearchClicked = true;
                	
                	/*LocationService
                		.getAllLocations(locationParams)
                		.success(function(response) {
                			locationId = response.locationId;
                		});*/
        		
        		
        		})
        	
        	
        	
        }
        
        vm.selectLocation = selectLocation;
        
        function selectLocation(index) {
        	var location = vm.locationSearchResults[index]
        	vm.locationUpdate = {
        			name : location.locationName,
        			description : location.locationDescription,
        			keywords : location.locationKeywords,
        			address1 : location.locationAddressLine1,
        			address2 : location.locationAddressLine2,
        			city : location.locationCity,
        			state : location.locationStateId,
        			zipCode : location.locationZipCode,
        			phoneNo : location.locationPhoneNumber,
        			faxNo : location.locationFaxNumber
        			
        	}
        	
        	
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
            		locationStateId : locationUpdate.state.toString(),
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