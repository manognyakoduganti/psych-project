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
        };
        
        vm.isSet = function (tabId) {
            return vm.tab === tabId;
        };
        
        vm.reportConfig = {
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
        	console.log(selectedParticipant);
        	ReportService
        	.getParticipantReport(selectedParticipant.participantId)
        	.success(function(response){
        		if(response.status == '200'){
        			var len = response.results.data.length;
        			if (len > 5){
        				console.log(len);
            			vm.chartWidth = (len * 200) + 'px';
        			}
        			vm.reportConfig.title = 'Average Correct Response Times for ' + selectedParticipant.userName
        			vm.reportData = response.results;
        			vm.loadedReport = true;
        		}
        		else{
        			vm.loadedReport = false;
        		}
        		
        	});
        	
        	
        }
        
    }

})();