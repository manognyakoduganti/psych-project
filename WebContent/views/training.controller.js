/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("TrainingController", TrainingController);
    

    function TrainingController(TrainingService, $window)
    {
    	var vm = this;
        vm.tab = 'search';
        

        vm.setTab = function (tabId) {
            //console.log("Setting tab to " + tabId);
            vm.tab = tabId;
        };

        vm.isSet = function (tabId) {
            //console.log("Tab set to " + tabId);
            return vm.tab === tabId;
        };
        
        vm.trainings = [];
        
        vm.isSelected = false;
        
        TrainingService
 		.getAllTrainings()
 		.success(function(response) {
 			vm.trainings = response.results;
 		});
        
        vm.getSelected = function(training){
        	if(training != undefined){
        		vm.isSelected = true;
        		getTrainingById(training.trainingId);
        	}
        }
    }

})();