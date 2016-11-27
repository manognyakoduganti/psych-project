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
        vm.questionsDropDown = [];
        vm.duplicateQuestionMessage = false;
        
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
        	vm.duplicateQuestionMessage = false;
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
        
        vm.initTrainingQuestions = function(){
        	vm.duplicateQuestionMessage = false;
        	vm.questionsDropDown = [];
        	
    		TrainingService
    		.getQuestionCategories()
    		.success(function(response){
    			vm.questionCategories = response.results;
    		});
    		
    		TrainingService
    		.getQuestions()
    		.success(function(response){
    			vm.questions = response.results;
    		});
        }
        
        vm.populateQuestions = function(selectedQuestionCategory){
        	vm.questionsDropDown = [];
        	console.log(selectedQuestionCategory.questionCategoryId);
        	vm.questions.forEach(function (question){
        		if (question.questionCategoryId == selectedQuestionCategory.questionCategoryId){
        			vm.questionsDropDown.push(clone(question))
        		}
        	});
        }
        
        vm.checkDuplicate = function(){
        	vm.duplicateQuestionMessage = false;
    		
    		vm.selectedTrainingDetails.trainingQuestions.forEach(function (question){
    			if (question.questionId == vm.questionsDropDown.selected.questionId){
    				vm.duplicateQuestionMessage = true;
    			}
    		})
        }
        
        vm.addQuestionToTraining = function(){
        	if (vm.questionCategories.selected != undefined && vm.questionsDropDown.selected != undefined){
        		var duplicate = false;
        		
        		vm.selectedTrainingDetails.trainingQuestions.forEach(function (question){
        			if (question.questionId == vm.questionsDropDown.selected.questionId){
        				duplicate = true;
        			}
        		})
        		console.log(duplicate);
        		if(!duplicate){
        			console.log(vm.questionCategories.selected);
        			console.log(vm.questionsDropDown.selected);
        			var newTrainingQuestion = {
        					questionId: vm.questionsDropDown.selected.questionId,
        					questionCategoryName: vm.questionsDropDown.selected.questionCategoryName,
        					questionDescription: vm.questionsDropDown.selected.questionDescription,
        					questionCategoryId: vm.questionsDropDown.selected.questionCategoryId,
        					questionId: vm.questionsDropDown.selected.questionId
        			}
        			console.log(newTrainingQuestion);
        			
        			vm.selectedTrainingDetails.trainingQuestions.push(newTrainingQuestion);
        		}
        		else{
        			vm.duplicateQuestionMessage = true;
        		}
        	}
        }
        
        
    }

})();