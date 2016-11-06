/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("AdminProfileController", AdminProfileController);

    function AdminProfileController($scope, $location)
    {
        $scope.$location = $location;
    }

})();