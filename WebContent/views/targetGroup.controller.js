
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
        
        function search() {
        	vm.isSearchClicked = true;
        }

        }
    
   
})();