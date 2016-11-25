/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("ImageManagementController", ImageManagementController);
    

    function ImageManagementController(ImageManagementService, $scope, $http) {
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
        
        $scope.file = {};

        //listen for the file selected event
        $scope.$on("fileSelected", function (event, args) {
            $scope.$apply(function () {            
                //add the file object to the scope's files collection
                $scope.file = args.file;
                console.log('file= ' + $scope.file.name);
            });
       
        
        //the save method
        vm.save = function() {
        	console.log("Inside save function");
            $http({
                method: 'POST',
                url: 'http://localhost:8080/Psych-1/' + "imageUpload",
                //IMPORTANT!!! You might think this should be set to 'multipart/form-data' 
                // but this is not true because when we are sending up files the request 
                // needs to include a 'boundary' parameter which identifies the boundary 
                // name between parts in this multi-part request and setting the Content-type 
                // manually will not set this boundary parameter. For whatever reason, 
                // setting the Content-type to 'false' will force the request to automatically
                // populate the headers properly including the boundary parameter.
                headers: { 'Content-Type': undefined },
                //This method will allow us to change how the data is sent up to the server
                // for which we'll need to encapsulate the model data in 'FormData'
                transformRequest: function (data) {
                    var formData = new FormData();
                    //need to convert our json object to a string version of json otherwise
                    // the browser will do a 'toString()' on the object which will result 
                    // in the value '[Object object]' on the server.
                    formData.append("model", angular.toJson(data.model));
                    //now add all of the assigned files
                    var fd = new FormData();
                    formData.append('file', $scope.file);
                    return formData;
                },
                //Create an object that contains the model and files which will be transformed
                // in the above transformRequest method
                data: { model: '12387lkasdj flkjas dlkfjalskdj flkajsldkfj lsdj flksjdlkf ls', files: $scope.file }
            }).
            success(function (data, status, headers, config) {
            	console.log("Success!!");
                alert("success!");
            }).
            error(function (data, status, headers, config) {
            	console.log("Failed!!");
                alert("failed!");
            });
            console.log('files sent: ' + $scope.file);
            };   
        })
    }
})();