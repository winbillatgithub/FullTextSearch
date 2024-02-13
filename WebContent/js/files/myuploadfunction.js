$(function () {
	
    $('#fileupload').fileupload({
//    	async: false,
//    	dataType: 'json',
    	autoUpload: true,
    	acceptFileTypes:  /(\.|\/)(doc|docx|gif|jpeg|png)$/i,
    	maxNumberOfFiles : 1,
//    	url: ctx + '/admin/files/fileController/uploadFile',
        add: function (e, data) {
	        var progress = 0;
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
            //data.context = $('<p/>').text('Uploading...').appendTo(document.body);
            data.submit(); //this will 'force' the submit in IE < 10
/*
			$.ajax({
				async: false,
	            type: 'post', 
	            cache: false, 
	            dataType: 'json',
	            url: ctx + '/admin/files/fileController/uploadFile',
	            success: function (data) {
					if (!data.success) {
						alert('upload error');
//						$('#errorTxt_cardCode').html(data.msg);
			        } else {
			        	alert('upload succeeded');
//			        	$('#errorTxt_cardCode').html(null);
			        }
	            }
	        });
	        openWin("元数据", ctx + "/admin/files/fileController/getMetaData?fileId=1233", 900, 500);
*/
        },

        done: function (e, data) {
            // Call the metadata page
            openWin("元数据", ctx + "/admin/files/fileController/getMetaData?fileId=1233", 1200, 650);
        },
        
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
   		},
   		fail: function(e, data) {
            //错误提示
   			var el = document.createElement('div');
   			el.innerHTML = data.jqXHR.responseText;
   			var content = el.getElementsByTagName('h1');
   			if (content.length == 1) {
   				alert(content[0].innerHTML);
   			} else {
   				alert(data.jqXHR.responseText);
   			}
   		},
		dropZone: $('#dropzone')
//    }).bind('fileuploadsubmit', function (e, data) {
//        // The example input, doesn't have to be part of the upload form:
//    	//alert('submit');
//        var progress = parseInt(0, 10);
//        $('#progress .bar').css(
//            'width',
//            progress + '%'
//        );
//        location.reload(true);

    });
   
});