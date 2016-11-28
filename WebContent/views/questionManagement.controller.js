/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("QuestionManagementController", QuestionManagementController);
    

    function QuestionManagementController(QuestionManagementService, $window){
    	var vm = this;
    	vm.tab = 'questionCategories';
    	vm.subTab = 'searchQuestionCategories'
    	
    	vm.createQ = {
    			newQuestionName : '',
    			newQuestionDescription : '',
    			newQuestionCategoryId : ''
    	};
    	
    	vm.createQc = {
    			newQuestionCategoryName : '',
    			newQuestionCategoryDescription : '',
    			newResponseType : '',
    			newStartLabel : '',
    			newEndLabel : ''
    	};
    	
    	vm.searchQc = {
    			questionCategoryName : '',
    			questionCategoryDescription : '',
    			responseTypeFieldId : '',
    			startLabel : '',
    			endLabel : ''
    	};
    	
    	vm.searchQ = {
    			questionName : '',
    			questionDescription : ''
    	};
    	
    	vm.setTab = function (tabId) {
            //console.log("Setting tab to " + tabId);
            vm.tab = tabId;
        };
        
        vm.setSubTab = function (tabId) {
        	vm.subTab = tabId;
        }

        vm.isSet = function (tabId) {
            //console.log("Tab set to " + tabId);
        	
            return vm.tab === tabId;
        };
        
        vm.isSetSubTab = function (tabId) {
        	return vm.subTab === tabId;
        };
        
        QuestionManagementService
        	.getAllCategories()
        	.success(function(response) {
        		if(response.status === '200')
        			vm.questionCategoryList = response.results;
        	});
        
        QuestionManagementService
    		.getAllResponseTypes()
    		.success(function(response) {
    			if(response.status === '200')
    				vm.responseTypeList = response.results;
    	});
        
        vm.createQuestionCategory = createQuestionCategory;
        
        function createQuestionCategory(qc) {
        	var questionCategory = {
        			newQuestionCategoryName : qc.newQuestionCategoryName,
        			newQuestionCategoryDescription : qc.newQuestionCategoryDescription,
        			newResponseType : qc.newResponseType,
        			newStartLabel : qc.newStartLabel,
        			newEndLabel : qc.newEndLabel
        	};
        	
        	console.log(questionCategory);
        	
        	QuestionManagementService
        		.createQuestionCategory(questionCategory)
        		.success(function(response) {
        			if(response.status === '200') {
        				vm.createQc = {
        		    			newQuestionCategoryName : '',
        		    			newQuestionCategoryDescription : '',
        		    			newResponseType : '',
        		    			newStartLabel : '',
        		    			newEndLabel : ''
        		    	};
        				
        				$window.alert('Question Category has been created successfully');
        			}
        			
        			else
        				$window.alert('Question Category creation failed');
        		});
        }
        
        vm.createQuestion = createQuestion;
        
        function createQuestion(question) {
        	var questionParams = {
        			newQuestionName : question.newQuestionName,
        			newQuestionDescription : question.newQuestionDescription,
        			newQuestionCategoryId : parseInt(question.newQuestionCategoryId)
        	};
        	
        	console.log(questionParams);
        	
        	QuestionManagementService
        		.createQuestion(questionParams)
        		.success(function(response) {
        			if(response.status === '200') {
        				vm.createQ = {
        		    			newQuestionName : '',
        		    			newQuestionDescription : '',
        		    			newQuestionCategoryId : ''
        		    	};
        				
        				$window.alert('Question has been created successfully');
        			}
        			
        			else
        				$window.alert('Question creation failed');
        		});
        }
        
        vm.searchQuestionCategories = searchQuestionCategories;
        
        function searchQuestionCategories(searchQc) {
        	var questionCategoriesList = [];
        	
        	QuestionManagementService
        		.getAllCategories()
        		.success(function(response) {
        			questionCategoriesList = response.results;
        			console.log(questionCategoriesList);
        		
        			console.log(searchQc.responseTypeFieldId);
                	var questionCategoryParams = {
                			questionCategoryName : searchQc.questionCategoryName,
                			questionCategoryDescription : searchQc.questionCategoryDescription,
                			responseTypeFieldId : searchQc.responseTypeFieldId.toString(),
                			startLabel : searchQc.startLabel,
                			endLabel : searchQc.endLabel	
                	};
                	
                	var keys = [];
                	var searchString = "";
                	var searchList = [];
                	for (var param in questionCategoryParams) {
                		
                		if(questionCategoryParams[param] != ''){
                			searchList.push(questionCategoryParams[param]);
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
                    	
                    	var fuse = new Fuse(questionCategoriesList, options);

                    	var results = fuse.search(searchList.join(" "));
                		vm.questionCategorySearchResults = results;
                	}
                	else {
                		vm.questionCategorySearchResults = questionCategoriesList;
                		
                	}
                	
                	vm.isSearchClicked = true;
                	
        		
        		
        		})
        }
        
        vm.searchQuestions = searchQuestions;
        
        function searchQuestions(q) {
        	var questionsList = [];
        	
        	QuestionManagementService
        		.getAllQuestions()
        		.success(function(response) {
        			questionsList= response.results;
        		
        			//console.log(locationSearch);
                	var questionParams = {
                			questionName : q.questionName,
                			questionDescription : q.questionDescription	
                	};
                	
                	var keys = [];
                	var searchString = "";
                	var searchList = [];
                	for (var param in questionParams) {
                		console.log(param + " " + questionParams[param]);
                		if(questionParams[param] != ''){
                			searchList.push(questionParams[param]);
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
                    	
                    	var fuse = new Fuse(questionList, options);

                    	var results = fuse.search(searchList.join(" "));
                		vm.questionSearchResults = results;
                	}
                	else {
                		vm.questionSearchResults = questionsList;
                		//console.log("else");
                		//console.log(vm.locationSearchResults);
                	}
                	
                	vm.isSearchClicked = true;
                	
        		
        		
        		})
        }
        
        vm.selectQuestionCategory = selectQuestionCategory;
        var updateQuestionCategoryId = '';
        var selectedQuestionCategory = '';
        function selectQuestionCategory(index) {
        	var qc = vm.questionCategorySearchResults[index];
        	selectedQuestionCategory = index;
        	console.log(qc);
        	vm.updateQc = {
        			questionCategoryName : qc.questionCategoryName,
        			questionCategoryDescription : qc.questionCategoryDescription,
        			responseType : parseInt(qc.responseTypeFieldId),
        			startLabel : qc.startLabel,
        			endLabel : qc.endLabel
        	};
        	updateQuestionCategoryId = vm.questionCategorySearchResults[index].questionCategoryId;
        }
        
        vm.selectQuestion = selectQuestion;
        var updateQuestionId = '';
        var selectedQuestion = '';
        function selectQuestion(index) {
        	var q = vm.questionSearchResults[index];
        	selectedQuestion = index;
        	console.log(q);
        	vm.updateQ = {
        			questionName : q.questionName,
        			questionDescription : q.questionDescription,
        			questionCategoryId : q.questionCategoryId
        	};
        	updateQuestionId = vm.questionSearchResults[index].questionId;
        }
        
        vm.updateQuestionCategory = updateQuestionCategory;
        
        function updateQuestionCategory(qc) {
        	
        	var qcUpdateParams = {
        			questionCategoryName : qc.questionCategoryName,
        			questionCategoryDescription : qc.questionCategoryDescription,
        			responseType : qc.responseType,
        			startLabel : qc.startLabel,
        			endLabel : qc.endLabel,
        			questionCategoryId : parseInt(updateQuestionCategoryId)
        	};
        	//console.log(locationUpdateParams);
        	console.log(qcUpdateParams);
        	QuestionManagementService
    			.updateQuestionCategory(qcUpdateParams)
    			.success(function(response) {
    				console.log(response);
    				if(response.status =='200') {
    					vm.isUpdateSuccessful = true;
    					$window.alert('Question Category has been updated successfully');
    					vm.questionCategorySearchResults[selectedQuestionCategory] = qcUpdateParams;
    				}
    				
    				else {
    					$window.alert('Question Category update failed');
    				}
    				
    			});
        }
        
        vm.updateQuestion = updateQuestion;
        
        function updateQuestion(q) {
        	
        	var questionUpdateParams = {
        			questionName : q.questionName,
        			questionDescription : q.questionDescription,
        			questionId : parseInt(updateQuestionId),
        			questionCategoryId : parseInt(q.questionCategoryId)
        	};
        	//console.log(locationUpdateParams);
        	console.log(questionUpdateParams);
        	QuestionManagementService
    			.updateQuestion(questionUpdateParams)
    			.success(function(response) {
    				console.log(response);
    				if(response.status =='200') {
    					vm.isUpdateSuccessful = true;
    					$window.alert('Question has been updated successfully');
    					vm.questionSearchResults[selectedQuestion] = questionUpdateParams;
    				}
    				
    				else {
    					$window.alert('Question update failed');
    				}
    				
    			});
        }
        
        
    }
})();