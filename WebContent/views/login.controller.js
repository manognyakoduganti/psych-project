/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("LoginController", LoginController);

    function LoginController($location, UserService, $rootScope)
    {
        var vm = this;
        vm.login = login;

        function login(user) {
            if(user) {


                UserService.login(user)
                .success(function(response) {
                	console.log("Response received: " + response);
                	console.log("verified received: " + response.verified);
                	console.log("Data received: " + response.data);
                	if(response.status == '200') {
                        UserService.setCurrentUser(user);
                        $location.url("/location");
                        $rootScope.user = {
                        		firstName : response.firstName,
                        		lastName : response.lastName,
                        		email : response.email,
                        		role: response.role
                        };
                	}
                	
                	else {
                		console.log(response.status + " Unauthorized")
                	}
                    })
               
                   
                    /*UserService.setCurrentUser(user);
                    $location.url("/location");*/
               
            }


        }



    }

})();
