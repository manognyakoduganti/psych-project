/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("LoginController", LoginController);

    function LoginController($location, UserService)
    {
        var vm = this;
        vm.login = login;

        function login(user) {
            if(user) {


                /*UserService.login(user)
                .success(function(response) {
                	console.log("Response received: " + response);
                	console.log("verified received: " + response.verified);
                	console.log("Data received: " + response.data);
                	if(response.verified == '1') {
                        UserService.setCurrentUser(user);
                        $location.url("/location");
                	}
                    })*/
               
                   
                    UserService.setCurrentUser(user);
                    $location.url("/location");
               
            }


        }



    }

})();
