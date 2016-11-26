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
        vm.trainings = [];
        vm.isSelected = false;

        vm.setTab = function (tabId) {
            //console.log("Setting tab to " + tabId);
            vm.tab = tabId;
            resetTab();
            
            if (tabId == 'search'){
            	initSearchTab();
            }
        };
        
        function resetTab(){
        	vm.isSelected = false;
        }
        
        function initSearchTab(){
        	TrainingService
     		.getAllTrainings()
     		.success(function(response) {
     			vm.trainings = response.results;
     		});
        	
        }
        
        initSearchTab();

        vm.isSet = function (tabId) {
            //console.log("Tab set to " + tabId);
            return vm.tab === tabId;
        };
        
        vm.getSelected = function(training){
        	if(training != undefined){
        		vm.isSelected = true;
        		TrainingService
        		.getTrainingById(training.trainingId)
        		.success(function(response){
        			vm.selectedTrainingDetails = response.results;
        		});
        	}
        }
        
        vm.updateTraining = function(){
        	console.log(angular.toJson(vm.selectedTrainingDetails));
        }
        
        vm.removeTrainingQuestion = function(index){
        	if(index > -1){
        		vm.selectedTrainingDetails.trainingQuestions.splice(index, 1);
        	}
        }
        
        vm.removeTrainingImage = function(index){
        	if(index > -1){
        		vm.selectedTrainingDetails.trainingImages.splice(index, 1);
        	}
        }
        
    }

})();