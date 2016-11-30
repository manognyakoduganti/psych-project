/**
 * Created by surajnagaraj on 10/27/16.
 */
(function () {
    angular
        .module("PsychWebApp")
        .controller("ImageManagementController", ImageManagementController);
    

    function ImageManagementController(ImageManagementService, FieldLookupService,$scope, $http, $window, serverURL) {
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
    	
    	vm.searchI = {
    			imageName : '',
    			imageDescription : '',
    			imageIntensity : '',
    			imageTypeId : '',
    			imageCategoryId : '',
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
        	console.log(searchIc);
        	ImageManagementService
        		.getAllCategories()
        		.success(function(response) {
        			imageCategoriesList = response.results;
        		
        			console.log(imageCategoriesList);
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
                		console.log(vm.imageCategorySearchResults);
                	}
                	else {
                		vm.imageCategorySearchResults = imageCategoriesList;
                		//console.log("else");
                		console.log(vm.imageCategorySearchResults);
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
        
        	
        	$scope.files = [];

            //listen for the file selected event
            $scope.$on("fileSelected", function (event, args) {
                $scope.$apply(function () {            
                    //add the file object to the scope's files collection
                	$scope.files.push(args.file);
                	console.log($scope.files[0].name);
                });
            });
           
            
            //the save method
            vm.save = function() {
            	/*var imageProp = {
                		imageName : $scope.imageName,
                		imageDescription : $scope.imageDescription,
                		imageType : $scope.imageType.toString(),
                		imageIntensity : $scope.imageIntensity.toString(),
                		imageCategory : $scope.imageCategory.toString()
                	};*/
            	//console.log(imageProp);
            	console.log("Inside save function");
            	console.log(angular.toJson('filename 123'));
            	console.log(angular.toJson({'name' : 'filename'}));
            	/*var formData = new FormData();
                
                formData.append('model', 'hfhefhoewoew');
                
                
                formData.append('files', $scope.files[0]);*/
                $http({
                	withCredentials : true,
                    method: 'POST',
                    url: serverURL.url + "imageUpload",
                   
                    headers: { 'Content-Type': undefined },
                   
                    
                    
                    transformRequest: function (data) {
                        var formData = new FormData();
                        
                        formData.append('imageName', $scope.imageName);
                        formData.append('imageDescription', $scope.imageDescription);
                        formData.append('imageTypeId', $scope.imageTypeId);
                        formData.append('imageIntensity', $scope.imageIntensity);
                        formData.append('imageCategoryId', $scope.imageCategoryId);
                        formData.append('imageFile', $scope.files[0]);
                        
                        
                        //formData.append('files', data.files[0]);
                       
                        return formData;
                    }
                   
                    
                }).
                success(function (data, status, headers, config) {
                	$window.alert('Image has been created successfully');
                }).
                error(function (data, status, headers, config) {
                	$window.alert('Image creation failed');
                });
                };
                
                vm.searchImages = searchImages;
                
                function searchImages(searchI) {
                	console.log(searchI);
                	var imageList = [];
                	console.log("Inside search images");
                	
                	ImageManagementService
                	.getAllImages()
                	.success(function(response) {
                		if(response.status === '200')
                			imageList = response.results;
                			for(var img in imageList) {
                				//console.log(img);
                				//console.log(serverURL);
                				imageList[img].imagePath = serverURL.url + 'imageUpload?imagePath=' + imageList[img].imagePath;
                			}
                			console.log(imageList);
                	
                		
                			//console.log(locationSearch);
                        	var imageParams = {
                        			imageName : searchI.imageName,
                        			imageDescription : searchI.imageDescription,
                        			imageIntensity : searchI.imageIntensity,
                        			imageTypeId : searchI.imageTypeId,
                        			imageCategoryId : searchI.imageCategoryId,
                        				
                        	};
                        	console.log(imageParams);
                        	var keys = [];
                        	var searchString = "";
                        	var searchList = [];
                        	for (var param in imageParams) {
                        		
                        		if(imageParams[param] != ''){
                        			searchList.push(imageParams[param]);
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
                        		console.log(imageList);
                            	var fuse = new Fuse(imageList, options);

                            	var results = fuse.search(searchList.join(" "));
                        		vm.imageSearchResults = results;
                        		console.log(vm.imageSearchResults);
                        	}
                        	else {
                        		vm.imageSearchResults = imageList;
                        		//console.log("else");
                        		console.log(vm.imageSearchResults);
                        	}
                        	
                        	vm.isSearchImagesClicked = true;
                        	
                		
                		
                		});
                }
                
    }
            
           
        	
        	
        
    
})();