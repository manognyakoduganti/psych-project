
(function () {
    angular
        .module("PsychWebApp")
        .controller("TargetGroupController", TargetGroupController);

    function TargetGroupController(LocationService, TargetGroupService, TrainingService)
    {
    	var vm = this;
        vm.tab = 'search';
        vm.isCreateSuccessful = false;
        vm.isCreateFailed = false;
        vm.isSearchClicked = false;
    	vm.createTargetGroup = {
    			name : '',
    			description : '',
    			keywords : '',
    			location : '',
    			training : ''
    	};
       

        vm.setTab = function (tabId) {
            //console.log("Setting tab to " + tabId);
            vm.tab = tabId;
        };

        vm.isSet = function (tabId) {
            //console.log("Tab set to " + tabId);
            return vm.tab === tabId;
        };
        
        vm.create = create;
        
        
        LocationService.getAllLocations().success(function(response) {
        		if(response.status === '200') {
        			vm.locationList = response.results;
        		}
        });
        
        TrainingService.getAllTrainings().success(function(response) {
    		if(response.status === '200') {
    				vm.trainingList = response.results;
    		}
        });
        
    	
        function create(newTargetGroup) {
        	if(newTargetGroup)
        		vm.isCreateFailed = false;
        		vm.isCreateSuccessful = false;
        		
        		var targetGroup = {
        				tgName : newTargetGroup.name,
        				tgDescription : newTargetGroup.description,
        				tgKeywords : newTargetGroup.keywords,
        				tgLocationId : newTargetGroup.location.toString(),
        				tgTrainingId : newTargetGroup.training.toString()
        		};
        		/*TargetGroupService
        			.checkDuplicate('tgName', newTargetGroup.name)
        			.then(function(response) {
        				if(response.results === 'true')
        					console.log("No duplicate target name");
        					
        						});*/
        		console.log(targetGroup);
				TargetGroupService
				.createTargetGroup(targetGroup)
				.success(function(response) {
					if(response.status === '200') {
						vm.createTargetGroup = {};
						vm.regCode = response.tgRegCode;
						vm.isCreateSuccessful = true;
						
					}
					else
						vm.isCreateFailed = true;
						
					});
        	}
        
        vm.search = search;
        
        function search(targetGroupSearch) {
        	
        	var targetGroupList = [];
        	
        	TargetGroupService
        		.getAllTargetGroups()
        		.success(function(response) {
        			targetGroupList = response.results;
        		
        			
                	var targetGroupParams = {
                			targetGroupName : search.targetGroupName,
                    		targetGroupDescription : locationSearch.description,
                    		targetGroupKeywords : locationSearch.keywords,
                    		targetGroupLocation : locationSearch.code,
                    		targetGroupTraining : locationSearch.address,
               	
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
        });

        }
    }
    
   
})();