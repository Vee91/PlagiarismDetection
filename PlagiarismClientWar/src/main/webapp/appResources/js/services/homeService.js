define([ 'app', 'jqueryui'], function(app) {
	app.factory('HomeService', function($http) {
		var factory = {
			submitFiles : submitFiles,
			validateFiles : validateFiles,
			uploadFiles : uploadFiles
		};
		
		var rowId="";
		var uploadedFileNames=[];
		var filesTransaction=[];
		var fileSizeLimitTransaction=10;
		var UploadedfileList=[];
		var fileTransactionSize=0;

		function submitFiles(file1, file2) {
			var fd = new FormData();
			for (var j = 0; j < file1.length; j++) {
				fd.append('file' + 1, file1[j]);

			}
			for (var j = 0; j < file2.length; j++) {
				fd.append('file' + 2, file2[j]);

			}
			var files = {
				"file1" : file1,
				"file2" : file2
			};
			var url = '/plagiarismrest/rest/compareFiles/';
			var submitRes = $http({
				method : 'POST',
				url : url,
				headers : {
					'Content-Type' : undefined
				},
				data : fd,
				transformRequest : angular.identity
			});
			/*
			 * return $http.post( '/plagiarismrest/rest/compareFiles/?cd=' +
			 * (new Date()).getTime(), files).then( function(response) { return
			 * response.data; })
			 */
			submitRes.then(function(response) {
				console.log(response);
			})
		};
		
		function validateFiles(fileList, rowIndex){
			rowId = rowIndex;
			var exceedFlag = false;
			angular.forEach(fileList, function(value){
				if(fileValidation(value) && (!checkExistingFile(uploadedFileNames, value.name, rowIndex))){
					var file = value;
					fileTransactionSize = fileTransactionSize + file.size;
					if(fileTransactionSize/(1024*1024) <= fileSizeLimitTransaction){
						
						if(filesTransaction[rowIndex] == null){
							filesTransaction[rowIndex] = [];
						}
						
						angular.forEach(filesTransaction, function(value1, key) {
							if(key == rowIndex){
								var oldFile = filesTransaction[key];
								oldFile.push(file);
								filesTransaction[rowIndex] = oldFile;
							}
						});
						
						if(uploadedFileNames[rowIndex] == null){
							uploadedFileNames[rowIndex] = [];
						}
						
						angular.forEach(uploadedFileNames, function(value1, key) {
							if(key == rowIndex) {
								var oldName= uploadedFileNames[key];
								oldName.push({"name": value.name});
								uploadedFileNames[rowIndex] = oldName;
							}
						});
						
						 generateUploadedView(filesTransaction,UploadedfileList,"downloadThisFile","removeThisFile","uploadedDocDiv",rowIndex);
					}else{
						fileTransactionSize=fileTransactionSize-file.size;
						exceedFlag=true;
					}
				}
			});
			if(exceedFlag){
				$('#errorMessageForFile').append("<span class='lev-field-error'>Document upload failed as file size exceeded 5 MB </span><br>");
			}
			
		};
		
		function uploadFiles(){
			$('#errorMessageForFile').html("");
			var fileSize=0;
			var sendData = [];
			var fd = new FormData();
			for (var i = 0; i < filesTransaction.length; i++){
				var obj=filesTransaction[i];

				if(obj!=undefined){
					for(var j=0;j<obj.length;j++){
						fd.append('file'+i, obj[j]);	
						fileSize=fileSize+obj[j].size;
					}
				}
	        }
			
	    	
	    	
	    	var uploadURL= '/plagiarismrest/rest/compareFiles/';
			if(fileSize/(1024*1024) <= fileSizeLimitTransaction) {
			var submitResp = $http({
		        method: 'POST',
		        url: uploadURL,
		        headers: {'Content-Type': undefined},
		        data: fd,
		        transformRequest: angular.identity
			   });
			
			
			 
			 
			}
			 return submitResp.then(function(response) { 
				  return response.data; 
				  })
		};
		
		
		
		function generateUploadedView(fileList, checkList, download, remove, divName, index) {
			
			var spanTextHTML = "";
			
			if(Object.keys(fileList).length !=0){
				if(undefined!=fileList[index]){
					if(fileList[index].length){
						var spanTextHTML1 = "";
						spanTextHTML1 += "<div><div  class='doc-upload-header' style='padding-bottom:3px;border-bottom:1px solid #da532c;margin-bottom:8px;' translate>Documents Uploaded</div>";
						$("#uploadedDivName").html(spanTextHTML1).css({
							"font-size" : "12px",
							"text-align" : "left",
						});
					};
					
					angular.forEach(fileList[index],function(value, key) {
						spanTextHTML += "<div><div class='col-xs-8 col-sm-8 col-md-10 col-lg-10' >"
						+value.name	
						+"</div>";
					});
					
					$("#"+divName).html(spanTextHTML).css({
						"font-size" : "12px",
						"text-align" : "left"
					});		

					$("#"+divName+index).html(spanTextHTML).css({
						"font-size" : "12px",
						"text-align" : "left"
					});		

					if(fileList[index].length==0 && checkList.length==0){
						$("#uploadedDivName").html("");
					}
					
				
				  }
				}
			
		};
		
		// 	TODO
		function checkExistingFile(fileArray,fileName,rowIndex){
			return false;
		};
		//TODO
		function fileValidation(file){
			return true;
		};

		return factory;
	});
});