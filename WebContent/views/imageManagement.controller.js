/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("ImageManagementController", ImageManagementController);
    

    function ImageManagementController(ImageManagementService){
    	var vm = this;
    	vm.tab = 'imageCatergories';
    	vm.subTab = 'searchImageCatergories'
    	
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
    }
})();