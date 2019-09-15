!(function (u) {
	// listId 列表id
    // chooseId 选择按钮id
	//var uploadServer = '';

    var APP_PATH = '/wxgh';
    var imageId, fileId, imageIds = '', fileIds = '';
    u.getImageNum = function () {
        return $("#" + imageId).children('.d-image').length;
    };

    u.getFileNum = function () {
        return $("#" + fileId).children('.d-file').length;
    };
    u.getImageIds = function () {
        return imageIds;
    };
    u.getFileIds = function () {
        return fileIds;
    };

    u.upload = function (uploadServer, fileNumLimit, listId, chooseId, success, error, list, thumbnailWidth, thumbnailHeight) {

        imageId = listId;

        if(!thumbnailWidth) thumbnailWidth = 100;
		if(!thumbnailHeight) thumbnailHeight = 100;
        // 初始化Web Uploader
        var uploader = WebUploader.create({
            auto: false,
            // swf文件路径
            swf: 'Uploader.swf',
            // 文件接收服务端。
            server: uploadServer,
            fileNumLimit: fileNumLimit,
            pick: '#' + chooseId,

            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        var $list = $("#" + listId);
		$list.addClass("uploader-list");

		if(list != null && list != '' && list != [])
		if(list instanceof Array){
			for(var i in list){
				init(list[i]);
			}
		}else{
			init(i);
		}

		function init(src){
			var $t = $(
                    '<div class="file-item thumbnail d-image">' +
                    '<img src="'+src+'">' +
                    '<div class="img-delete">删除</div>' +
                    '</div>'
                );
			$list.append($t);
			$t.on('click', '.img-delete', function () {
                $(this).parent().remove();
            })
		}

        uploader.on( 'fileQueued', function( file ) {
            var $li = $(
                    '<div id="' + file.id + '" class="file-item thumbnail d-image">' +
                    '<img>' +
                    '<div class="img-delete">删除</div>' +
                    '</div>'
                ), $img = $li.find('img');
            $li.on('click', '.img-delete', function () {
                uploader.removeFile( file );
                $(this).parent().remove();
            })

            // $list为容器jQuery实例
            $list.append( $li );
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }
                $img.attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress span');
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on( 'uploadSuccess', function( file, ret ) {
            $( '#'+file.id ).addClass('upload-state-done');
			var data = 0;
			if(ret.ok){
				data = ret.data.toString();
				imageIds += (data + ",")
			}

        });

        // 文件上传失败，显示上传出错。
        uploader.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                $error = $li.find('div.error');
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败');
			error();
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {

            $( '#'+file.id ).find('.progress').remove();
        });

        // 所有文件上传完毕
        uploader.on( 'uploadFinished', function( file ) {
            imageIds = imageIds.substring(0, imageIds.length - 1)
            success(imageIds);
        });

        return uploader;
    }


	u.uploadFile = function (uploadServer, fileNumLimit, listId, chooseId, success, error, list) {

        fileId = listId;

        // 初始化Web Uploader
        var uploader = WebUploader.create({
            auto: false,
            // swf文件路径
            swf: 'Uploader.swf',
            // 文件接收服务端。
            server: uploadServer,
            pick: '#' + chooseId,
            fileNumLimit: fileNumLimit,
			accept: {
                title: 'file',
                extensions: '*',
                mimeTypes: '*'
            }
        });

        var $list = $("#" + listId);
		$list.addClass("uploader-list");

        if(list != null && list != '' && list != [])
		if(list instanceof Array){
			for(var i in list){
				init(list[i]);
			}
		}else{
			init(i);
		}

		function init(src){
			var $t = $(
                    '<div class="file-item thumbnail d-file">' +
                    '<img src="'+APP_PATH+'/libs/webuploader/image/file_unknow.png" width="100" height="100">' +
                    '<div class="file-delete">删除</div>' +
					'<p>' + src['name'] + '</p>' +
                    '</div>'
                );
			$list.append($t);
			$t.on('click', '.file-delete', function () {
                $(this).parent().remove();
            })
		}

        uploader.on( 'fileQueued', function( file ) {
            var $li = $(
                    '<div id="' + file.id + '" class="file-item thumbnail d-file">' +
                    '<img src="'+APP_PATH+'/libs/webuploader/image/file_unknow.png" width="100" height="100">' +
                    '<div class="file-delete">删除</div>' +
					'<p>' + file.name + '</p>' + 
                    '</div>'
                );
				console.log(file);
            $list.append( $li );
            $li.on('click', '.file-delete', function () {
                uploader.removeFile( file );
                $(this).parent().remove();
            });
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress span');
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on( 'uploadSuccess', function( file, ret ) {
            $( '#'+file.id ).addClass('upload-state-done');
			var data = 0;
			if(ret.ok){
				data = ret.data.toString();
				fileIds += (data + ",");
			}

        });

        // 文件上传失败，显示上传出错。
        uploader.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                $error = $li.find('div.error');
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败');
			error();
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });

        // 所有文件上传完毕
        uploader.on( 'uploadFinished', function( file ) {
            fileIds = fileIds.substring(0, fileIds.length - 1)
            success(fileIds);
        });

        return uploader;
    }
})
(window.up = {})