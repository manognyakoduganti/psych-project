
(function () {
    angular
        .module("PsychWebApp")
        .controller("TargetGroupController", TargetGroupController);

    function TargetGroupController(LocationService, TargetGroupService, TrainingService, $window)
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
    	
    	vm.searchTg = {
    			tgName : '',
    			tgDescription : '',
    			tgKeywords : '',
    			tgLocation : '',
    			tgTraining : ''
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
						vm.createTargetGroup = {
				    			name : '',
				    			description : '',
				    			keywords : '',
				    			location : '',
				    			training : ''
				    	};
						vm.regCode = response.tgRegCode;
						vm.isCreateSuccessful = true;
						
					}
					else
						vm.isCreateFailed = true;
						
					});
        	}
        
        vm.searchTargetGroups = searchTargetGroups;
        
        function searchTargetGroups(tg) {
        	console.log("TargetGroup Search Params= " + tg);
        	var targetGroupList = [];
        	
        	TargetGroupService
        		.getAllTargetGroups()
        		.success(function(response) {
        			console.log('targetGroupList = ' + response.results);
        			targetGroupList = response.results;
        		
        			
                	var targetGroupParams = {
                			tgName : tg.tgName,
                    		tgDescription : tg.tgDescription,
                    		tgKeywords : tg.tgKeywords,
                    		tgLocation : tg.tgLocation,
                    		tgTraining : tg.tgTraining,
               	
                	};
                	
                	var keys = [];
                	var searchString = "";
                	var searchList = [];
                	for (var param in targetGroupParams) {
                		console.log(param + " " + targetGroupParams[param]);
                		if(targetGroupParams[param] != ''){
                			searchList.push(targetGroupParams[param]);
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
                    	var fuse = new Fuse(targetGroupList, options);

                    	var results = fuse.search(searchList.join(" "));
                		vm.targetGroupSearchResults = results;
                	}
                	else {
                		vm.targetGroupSearchResults = targetGroupList;
                		console.log("else");
                		console.log(vm.targetGroupSearchResults);
                	}
                	
                	vm.isSearchClicked = true;
        });

        }
        
        vm.selectTargetGroup = selectTargetGroup;
        var updateTgId = '';
        function selectTargetGroup(index) {
        	var targetGroup = vm.targetGroupSearchResults[index];
        	console.log("tgLocationId = " + targetGroup.tgLocationId);
        	vm.tgUpdate = {
        		tgName : targetGroup.tgName,
        		tgDescription : targetGroup.tgDescription,
        		tgKeywords : targetGroup.tgKeywords,
        		tgLocation : targetGroup.tgLocationId,
        		tgTraining : targetGroup.tgTrainingId
        	};
        	updateTgId = vm.targetGroupSearchResults[index].tgId;
        	
        }
        
        vm.update = update;
        
        function update(tgUpdate) {
        	var tgUpdateParams = {
        			tgName : tgUpdate.tgName,
    				tgDescription : tgUpdate.tgDescription,
    				tgKeywords : tgUpdate.tgKeywords,
    				tgLocationId : tgUpdate.tgLocation.toString(),
    				tgTrainingId : tgUpdate.tgTraining.toString(),
    				tgId : updateTgId
        	};
        	//console.log(locationUpdateParams);
        	TargetGroupService
    			.updateTargetGroup(tgUpdateParams)
    			.success(function(response) {
    				console.log(response);
    				if(response.status =='200') {
    					vm.isUpdateSuccessful = true;
    					$window.alert('Target Group has been updated successfully');
    					vm.targetGroupSearchResults = response.results;
    				}
    				
    				else {
    					$window.alert('Target Group update failed');
    				}
    				
    			});
        }
    }

    
   
})();