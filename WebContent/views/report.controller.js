(function () {
    angular
        .module("PsychWebApp")
        .controller("ReportController", ReportController);
    

    function ReportController($window, TargetGroupService, UserService, ReportService)
    {
    	var vm = this;
        vm.tab = 'participant';
        
        vm.setTab = function (tabId) {
            vm.tab = tabId;
            
            resetTab();
        };
        
        vm.isSet = function (tabId) {
            return vm.tab === tabId;
        };
        
        function resetTab(){
        	vm.reportDataCorrectImageResponses = [];
			vm.reportDataWrongImageResponses = [];
			vm.loadedReport = false;
			vm.participantsDropDown.selected = "";
        }
        
        vm.reportConfigCorrectImage = {
        		  "labels": false,
        		  "title": "Report",
        		  "legend": {
        		    "display": true,
        		    "position": "left"
        		  },
        		  "innerRadius": 20,
        		  "lineLegend": "traditional"
        		}
        
        vm.reportConfigWrongImage = {
      		  "labels": false,
      		  "title": "Report",
      		  "legend": {
      		    "display": true,
      		    "position": "left"
      		  },
      		  "innerRadius": 0,
      		  "lineLegend": "traditional"
      		}
        
        function initParticipantTab(){
        	TargetGroupService
        	.getAllTargetGroups()
        	.success(function(response){
        		vm.targetGroups = response.results;
        	});
        	
        	UserService
        	.getAllParticipants()
        	.success(function (response){
        		if (response.status == '200'){
        			vm.participantsDropDown = response.results;
        			vm.participants = response.results;
        		}
        		else{
        			vm.participantsDropDown = [];
        			vm.participants = [];
        		}
        		
        	});
        }
        
        initParticipantTab();
        
        vm.filterParticipants = function(selected){
        	vm.participantsDropDown = [];
        	
        	vm.participants.forEach(function (participant){
        		if (participant.targetGroupId == selected.tgId){
        			vm.participantsDropDown.push(clone(participant));
        		}
        	});
        }
        
        vm.loadReportForParticipant = function(selectedParticipant){
        	ReportService
        	.getParticipantReport(selectedParticipant.participantId)
        	.success(function(response){
        		console.log(response);
        		
        		if(response.status == '200'){
        			var len = response.results.avgImageCorrectReponses.data.length;
        			if (len > 5){
        				console.log(len);
            			vm.chartWidth = (len * 140) + 'px';
        			}
        			vm.reportConfigCorrectImage.title = 'Average Correct Response Times for ' + selectedParticipant.userName
        			vm.reportDataCorrectImageResponses = response.results.avgImageCorrectReponses;
        			vm.reportDataWrongImageResponses = response.results.avgImageWrongReponses;
        			
        			console.log(vm.reportDataCorrectImageResponses);
        			
        			vm.reportConfigWrongImage.title = 'Average Incorrect Response Times for ' + selectedParticipant.userName
        			vm.loadedReport = true;
        		}
        		else{
        			vm.loadedReport = false;
        			vm.reportDataCorrectImageResponses = [];
        			vm.reportDataWrongImageResponses = [];
        		}
        		
        	});
        	
        }
        
    }

})();