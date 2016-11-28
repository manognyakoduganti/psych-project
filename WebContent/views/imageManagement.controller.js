/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("ImageManagementController", ImageManagementController);
    

    function ImageManagementController(ImageManagementService, FieldLookupService,$scope, $http, $window) {
    	var vm = this;
    	vm.tab = 'imageCatergories';
    	vm.subTab = 'searchImageCatergories';
    	
    	vm.createIc = {
    			imageCategoryName : '',
    			imageCategoryDescription : ''
    	};
    	
    	vm.searchIc = {
    			imageCategoryName : '',
    			imageCategoryDescription : ''
    	};
    	
    	vm.createI = {
    			imageName : '',
    			imageDescription : '',
    			imageIntensity : '',
    			imageType : '',
    			imageCategory : '',
    	};
    	
    	ImageManagementService
    	.getAllCategories()
    	.success(function(response) {
    		if(response.status === '200')
    			vm.imageCategoryList = response.results;
    	});
    	
    	FieldLookupService
    	.fetchFields('imageType')
    	.success(function(response) {
    		if(response.status === '200')
    			vm.imageTypeList = response.results;
    	});
    	
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
        
        
        
        vm.createImageCategory = createImageCategory;
        
        function createImageCategory(Ic) {
        	var imageCategory = {
        			imageCategoryName : Ic.imageCategoryName,
        			imageCategoryDescription : Ic.imageCategoryDescription
        	};
        	
        	console.log(imageCategory);
        	
        	ImageManagementService
        		.createImageCategory(imageCategory)
        		.success(function(response) {
        			if(response.status === '200') {
        				vm.createIc = {
        						imageCategoryName : '',
        		    			imageCategoryDescription : ''
        		    	};
        				
        				$window.alert('Image Category has been created successfully');
        			}
        			
        			else
        				$window.alert('Image Category creation failed');
        		});
        }
        
        vm.searchImageCategory = searchImageCategory;
        
        function searchImageCategory(searchIc) {
        	var imageCategoriesList = [];
        	
        	ImageManagementService
        		.getAllCategories()
        		.success(function(response) {
        			imageCategoriesList = response.results;
        		
        			//console.log(locationSearch);
                	var imageCategoryParams = {
                			imageCategoryName : searchIc.imageCategoryName,
                			imageCategoryDescription : searchIc.imageCategoryDescription
                				
                	};
                	
                	var keys = [];
                	var searchString = "";
                	var searchList = [];
                	for (var param in imageCategoryParams) {
                		
                		if(imageCategoryParams[param] != ''){
                			searchList.push(imageCategoryParams[param]);
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
                    	console.log(1);
                		console.log(keys);
                		console.log(searchList);
                    	var fuse = new Fuse(imageCategoriesList, options);

                    	var results = fuse.search(searchList.join(" "));
                		vm.imageCategorySearchResults = results;
                	}
                	else {
                		vm.imageCategorySearchResults = imageCategoriesList;
                		//console.log("else");
                		//console.log(vm.locationSearchResults);
                	}
                	
                	vm.isSearchClicked = true;
                	
        		
        		
        		})
        }
        
        vm.selectImageCategory = selectImageCategory;
        var updateImageCategoryId = '';
        var selectedImageCategory = '';
        function selectImageCategory(index) {
        	var Ic = vm.imageCategorySearchResults[index];
        	selectedImageCategory = index;
        	console.log(Ic);
        	vm.updateIc = {
        			imageCategoryName : Ic.imageCategoryName,
        			imageCategoryDescription : Ic.imageCategoryDescription,
        			
        	};
        	updateImageCategoryId = vm.imageCategorySearchResults[index].imageCategoryId;
        }
        
        vm.updateImageCategory = updateImageCategory;
        
        function updateImageCategory(Ic) {
        	
        	var IcUpdateParams = {
        			imageCategoryName : Ic.imageCategoryName,
        			imageCategoryDescription : Ic.imageCategoryDescription,
        			imageCategoryId : updateImageCategoryId.toString()
        	};
        	//console.log(locationUpdateParams);
        	//console.log(qcUpdateParams);
        	ImageManagementService
    			.updateImageCategory(IcUpdateParams)
    			.success(function(response) {
    				console.log(response);
    				if(response.status =='200') {
    					vm.isUpdateSuccessful = true;
    					$window.alert('Image Category has been updated successfully');
    					vm.imageCategorySearchResults[selectedImageCategory] = IcUpdateParams;
    				}
    				
    				else {
    					$window.alert('Image Category update failed');
    				}
    				
    			});
        }
        
        	
        	$scope.file = {};

            //listen for the file selected event
            $scope.$on("fileSelected", function (event, args) {
                $scope.$apply(function () {            
                    //add the file object to the scope's files collection
                	$scope.file = args.file;
                	console.log('file= ' + $scope.file.name);
                });
           
            
            //the save method
            vm.save = function save(img) {
            	var imageProp = {
                		imageName : img.imageName,
                		imageDescription : img.imageDescription,
                		imageType : img.imageType.toString(),
                		imageIntensity : img.imageIntensity.toString(),
                		imageCategory : img.imageCategory.toString()
                	};
            	console.log(imageProp);
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
                        formData.append("imageName", $scope.imageName);
                        formData.append("imageDescription", $scope.imageDescription);
                        formData.append("imageType", $scope.imageType);
                        formData.append("imageIntensity", angular.toJson(data.imageIntensity));
                        formData.append("imageCategory", angular.toJson(data.imageCategory));
                        formData.append('imageFile', $scope.file);
                        
                        //now add all of the assigned files
                        /*for (var i = 0; i < data.files; i++) {
                            //add each file to the form data and iteratively name them
                            formData.append("imageFile" + i, data.files[i]);
                        }*/
                        return formData;
                    },
                    //Create an object that contains the model and files which will be transformed
                    // in the above transformRequest method
                    data: { imageName: 'img.imageName', imageDescription :img.imageDescription, imageType : img.imageType, 
                    	imageIntensity : img.imageIntensity,imageCategory : img.imageCategory, files: $scope.file }
                }).
                success(function (data, status, headers, config) {
                	$window.alert('Image has been created successfully');
                }).
                error(function (data, status, headers, config) {
                	$window.alert('Image creation failed');
                });
                };
            
            
            })
        	
        	
        
    }
})();