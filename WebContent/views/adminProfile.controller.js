/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("AdminProfileController", AdminProfileController);

    function AdminProfileController($scope, $location, $rootScope, UserService)
    {
        $scope.$location = $location;
        var vm = this;
        var userData = $rootScope.user;
        var email = $rootScope.user.email;
        vm.isUpdateSuccessful = false;
        vm.isUpdateFailed = false;
        vm.isChangePasswordSuccessful = false;
        
        vm.firstName = userData.firstName;
        vm.lastName = userData.lastName;
        vm.email = userData.email;
        
        vm.update = update;
        
        function update() {
        	var updateUserData = {
        			newFirstName : vm.firstName,
        			newLastName : vm.lastName,
        			newEmail : vm.email,
        			newPassword : vm.password,
        			email : email
        	};
        	
        	
        		UserService.updateProfile(updateUserData)
                .success(function(response) {
                	if(response.status === '200') {
                	vm.isUpdateSuccessful = true;
                	console.log("Response received: " + response);
                	console.log("verified received: " + response.verified);
                	console.log("Data received: " + response.data);
                	$rootScope.user.firstName = response.firstName;
                	$rootScope.user.lastName = response.lastName;
                	$rootScope.user.email = response.email;
                	}
                	
                	else
                		vm.isUpdateFailed = true;
                });
        	
        vm.changePassword = changePassword;
        
        function changePassword() {
        	if(vm.password === vm.retypePassword) {
        		pwd = {
        			newPassword : vm.password	
        		};
        		UserService
        			.changePassword(pwd)
        			.success(function(response) {
        			if(response.status === '200')
        				vm.isChangePasswordSuccessful = true;
        			else
        				vm.isChangePasswordSuccessful = false;
        		});
        	}
        }
        	
        }
    }


})();