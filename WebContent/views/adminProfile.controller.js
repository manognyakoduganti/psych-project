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
        	
        	if(vm.password === vm.retypePassword) {
        		UserService.updateProfile(updateUserData)
                .success(function(response) {
                	console.log("Response received: " + response);
                	console.log("verified received: " + response.verified);
                	console.log("Data received: " + response.data);
                });
        	}
        }
    }


})();