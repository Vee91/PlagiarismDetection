define(['app', 'jqueryui','homeService'], function (app) {
	var fileSizeLimitTransaction=1;
	var fileList = "";
	var objHTML="";
	var fileContents=[];
	var allfiles=[];
	var allfiles1=[];
    app.controller('homeController', ['$location', 'HomeService', function ($location, HomeService) {
            var vm = this;
            vm.submitFiles = submitFiles;
            vm.error=null;
           
            	$(document).ready(function(){
       			 objHTML = $("#dragandrophandler");
       			 objHTML.on('dragenter', function (e) 
       			{
       			    e.stopPropagation();
       			    e.preventDefault();
       			    $(this).css('border', '2px dashed #1479bf');
       			    $(this).css('background-color', '#ededed');
       			});
       			 objHTML.on('dragover', function (e) 
       			{
       			     e.stopPropagation();
       			     e.preventDefault();
       			});
       			 objHTML.on('drop', function (e) 
       			{	
       				vm.error=null;
       				//document.getElementById('errorMessage').style.visibility = "hidden"; 
       			     $(this).css('border', '1px dashed #da532c');
       			     $(this).css('background-color', '');
       			     e.preventDefault();
       			     fileList = e.originalEvent.dataTransfer.files;
       			     $('#errorMessageForFile').html("");
       			     if(fileList.length>fileSizeLimitTransaction)
       			    	 $('#errorMessageForFile').text("Error: You can upload 1 document at time.");
       			  else if(allfiles.length >= fileSizeLimitTransaction)
    			    	 $('#errorMessageForFile').text("Error: You can upload 1 document at time.");
       			     else{
       			    	allfiles.push(fileList[0]);
       			    	HomeService.validateFiles(fileList,0);
       			 	var file = fileList[0],read = new FileReader();
                	read.readAsBinaryString(file);
                	read.onloadend = function(){
                		document.getElementById('filecontents1').value=read.result;
                	}
       			     }
       			 	 
       			});
       			 objHTML.on('dragleave', function (e) 
       						{
       						    e.stopPropagation();
       						    e.preventDefault();
       						    $(this).css('border', '1px dashed #da532c');
       						    $(this).css('background-color', '');
       						}); 
            	
            	$('#singleFile').on('change',function(event) {
            		$('#errorMessageForFile').html("");
            		vm.error=null;
            		//document.getElementById('errorMessage').style.visibility = "hidden"; 
            		var fileList=[];
            		fileList.push(document.getElementById("singleFile").files[0]);
            		HomeService.validateFiles(fileList, 0);
            		var file = fileList[0],read = new FileReader();
                	read.readAsBinaryString(file);
                	read.onloadend = function(){
                		document.getElementById('filecontents1').value=read.result;
                	}
            	});
            	});
            
            	
            	$(document).ready(function(){
          			 objHTML = $("#dragandrophandler1");
          			 objHTML.on('dragenter', function (e) 
          			{
          			    e.stopPropagation();
          			    e.preventDefault();
          			    $(this).css('border', '2px dashed #1479bf');
          			    $(this).css('background-color', '#ededed');
          			});
          			 objHTML.on('dragover', function (e) 
          			{
          			     e.stopPropagation();
          			     e.preventDefault();
          			});
          			 objHTML.on('drop', function (e) 
          			{	
          				vm.error=null;
          				//document.getElementById('errorMessage').style.visibility = "hidden"; 
          			     $(this).css('border', '1px dashed #da532c');
          			     $(this).css('background-color', '');
          			     e.preventDefault();
          			     fileList = e.originalEvent.dataTransfer.files;
          			     $('#errorMessageForFile1').html("");
          			     if(fileList.length>fileSizeLimitTransaction)
          			    	 $('#errorMessageForFile1').text("Error: You can upload 1 document at time.");
          			   else if(allfiles1.length >= fileSizeLimitTransaction)
         			    	$('#errorMessageForFile1').text("Error: You can upload 1 document at time.");
          			     else{
          			    	allfiles1.push(fileList[0]);
          			    	HomeService.validateFiles(fileList,1);
          			  	var file = fileList[0],read = new FileReader();
                    	read.readAsBinaryString(file);
                    	read.onloadend = function(){
                    		document.getElementById('filecontents2').value=read.result;
                    	}
          			     }
          			 	 
          			});
          			 objHTML.on('dragleave', function (e) 
          						{
          						    e.stopPropagation();
          						    e.preventDefault();
          						    $(this).css('border', '1px dashed #da532c');
          						    $(this).css('background-color', '');
          						}); 
               	
               	$('#singleFile1').on('change',function(event) {
               		$('#errorMessageForFile1').html("");
               		vm.error=null;
               		//document.getElementById('errorMessage').style.visibility = "hidden"; 
               		var fileList=[];
               		fileList.push(document.getElementById("singleFile1").files[0]);
               		HomeService.validateFiles(fileList, 1);
               		var file = fileList[0],read = new FileReader();
                	read.readAsBinaryString(file);
                	read.onloadend = function(){
                		document.getElementById('filecontents2').value=read.result;
                	}
               	});
               	});
            
            /*function submitFiles(file1, file2) {
            	if((file1 == null) || (file2 == null)){
            		vm.result = "Error: Please load 2 Java Files";
            	}
            	else{
            		HomeService.submitFiles(file1, file2)
                    	.then(function (response) {
                    		vm.result = response.message;
                    	}),(function (error) {
                    		vm.result = "Error : Invalid File(Compilation Error/Not a Java File)";
                    	});
            	}
            }*/
            
            function submitFiles(file1, file2){
            
            		
            	HomeService.uploadFiles()
            		.then(function (response) {
            			vm.result = response.message;
            			var resultLines=response.resultList;
            	var code=response.code;
            	if(code!=200){
console.log(response.error);
vm.error=response.error;
//document.getElementById('errorMessage').style.visibility = "visible"; 
            		document.getElementById('errorMessage').setAttribute("align", "center");
            		document.getElementById('errorMessage').innerHTML=response.error;
            		return;
            	}
            
            	
        		var fileContents1=document.getElementById('filecontents1').value;
            	var fileContents2=document.getElementById('filecontents2').value;
            	var lines1=fileContents1.split("\n");
            	var lines2=fileContents2.split("\n");
            	
            			resultLines.sort(function(a, b) {
            			    return parseInt(a.start1) - parseInt(b.start1);
            			});
            			console.log(resultLines);
            			var count1=0;
            			var count2=0;
            			
            			var j=0;
            			
            			while(j<resultLines.length){
            				var s1=resultLines[j].start1;
            				var e1=resultLines[j].end1;
            				var color=resultLines[j].color;
            			
            				
                        		var element = document.createElement("div");
                        		if(count1>=s1-1 && count1<=e1-1){
                        		element.style.color=color;
                        		
                        		}
                        		if(count1>=e1-1){
                            		j=j+1;
                            		}
                        		element.setAttribute("align", "left");
                        	    element.appendChild(document.createTextNode((count1+1)+"\t"+lines1[count1]));
                        	    document.getElementById('f1').appendChild(element);
                        	    count1=count1+1;
            			}
            			
            			
            			var k=0;
            			
            		
            			while(k<resultLines.length){
            				var s2=resultLines[k].start2;
            				var e2=resultLines[k].end2;
            				var color=resultLines[k].color;
            			
    
                        		var element = document.createElement("div");
                        		if(count2>=s2-1 && count2<=e2-1){
                        		element.style.color=color;
                        		
                        		}
                        		if(count2>=e2-1){
                            		k=k+1;
                            		}
                        		element.setAttribute("align", "left");
                        		//element.style.align-content="left";
                        	    element.appendChild(document.createTextNode((count2+1)+"\t"+lines2[count2]));
                        	    document.getElementById('f2').appendChild(element);
                        	    count2=count2+1;
            			}
            			
            			
            			
            			while(count1<lines1.length){
            				var element = document.createElement("div");
            				element.setAttribute("align", "left");
            				element.appendChild(document.createTextNode((count1+1)+"\t"+lines1[count1]));
                    	    document.getElementById('f1').appendChild(element);
                    	    count1=count1+1;
            			}
            			while(count2<lines2.length){
            				var element = document.createElement("div");
            				element.setAttribute("align", "left");
            				element.appendChild(document.createTextNode((count2+1)+"\t"+lines2[count2]));
                    	    document.getElementById('f2').appendChild(element);
                    	    count2=count2+1;
            			}
            		}), (function (error) {
            			vm.result = "Error : Invalid File(Compilation Error/Not a Java File)";
            		});
            }
            
            

        }]);
    return app;
});