/**
 * Created by gan on 2017/9/5.
 */


(function ($) {


    $(function () {
        var $wrap = $('#uploader'),

            // 图片容器
            $queue = $( '<ul class="filelist"></ul>' )
                .appendTo( $wrap.find( '.queueList' ) ),

            // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find( '.statusBar' ),

            // 文件总体选择信息。
            $info = $statusBar.find( '.info' ),

            // 文件添加输入框。
            $addBtn = $wrap.find( '#fileToUpload' ),

            // 文件添加按钮。
            $uploadBtn = $wrap.find( '#upload_btn' ),

            // 上传按钮
            $upload = $wrap.find( '#uploadBtn' ),

            $progress = $statusBar.find( '.progress' ).hide(),

            // 添加的文件数量
            fileCount = 0,

            // 添加的文件总大小
            fileSize = 0,

            //文件上传接口
            url = '${home}/reimb/dep/upload.json',

            //上传携带的额外数据
            data = {},

            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,

            // 缩略图大小
            thumbnailWidth = 110 * ratio,
            thumbnailHeight = 110 * ratio,

            // 可能有pedding, ready, uploading, confirm, done.
            state = 'ready',

            // 所有文件的进度信息，key为file id
            percentages = {},

            // 浏览器是否支持bse64
            isSupportBase64 = ( function() {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function() {
                    if( this.width != 1 || this.height != 1 ) {
                        support = false;
                    }
                };
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            } )(),
            supportTransition = (function(){
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                        'WebkitTransition' in s ||
                        'MozTransition' in s ||
                        'msTransition' in s ||
                        'OTransition' in s;
                s = null;
                return r;
            })();

        //
        uploader.setUrlData(url,data);
        //
        uploader.limitAddFile = function () {
            if(fileCount >= 20){
                alert("文件超过最大数量限制.");
                return true;
            }
        };
        //文件添加前触发事件
        uploader.onBeforeFileQueued = function (file) {

        };
        //文件添加触发事件
        uploader.onFileQueued = function( file ) {
            fileCount++;
            fileSize += file.size;

            addFile( file );
            setState( 'ready' );
            updateTotalProgress();
        };

        //文件删除触发事件
        uploader.onFileDequeued = function( file ) {
            fileCount--;
            fileSize -= file.size;

            /*if ( !fileCount ) {
                setState( 'pedding' );
            }*/

            removeFile( file );
            updateTotalProgress()
        };
        //文件上传进度
        uploader.onUploadProgress = function( file, percentage ) {
            var $li = $('#'+file.id),
                $percent = $li.find('.progress span');

            $percent.css( 'width', percentage * 100 + '%' );
            $percent.css('display', 'block');
            percentages[ file.id ][ 1 ] = percentage;
            updateTotalProgress();
        };

        //文件上传失败
        uploader.onUploadError = function( file, error ) {
            var $li = $('#'+file.id),
                $prgress = $li.find('p.progress '),
                $info = $('<p class="error"></p>');

            $prgress.hide().width(0);
            var s = getError(error || file.statusText);
            $info.text( s ).appendTo( $li );
            updateTotalProgress();
        };

        //文件上传成功
        uploader.onUploadSuccess = function( file, id ) {
            var $li = $('#'+file.id),
                $prgress = $li.find('p.progress');
            $li.append( '<span class="success"></span>' );
            file.rid = id;
            file.xmlHttp = null;
            $prgress.hide().width(0);
            if(uploader.getFiles(WFile.Status.COMPLETE).length == fileCount){
                setState('finish');
            }
            updateTotalProgress();
        };

        /*
         * 开始上传事件
         * */
        uploader.onStartUpload = function () {
           /* var invalidFiles = uploader.getFiles(WFile.Status.INVALID);
            //更新无效文件 为 上传错误
            for(var i=0;i<invalidFiles.length;i++){
                invalidFiles[i].setStatus(WFile.Status.ERROR,invalidFiles[i].statusText);
            }*/
            setState('confirm');
            updateTotalProgress();
            /*for(var i=0;i<invalidFiles.length;i++){
                invalidFiles[i].setStatus(WFile.Status.INVALID,invalidFiles[i].statusText);
            }*/
        };

        function getError( code ) {
            switch( code ) {
                case 'exceed_size':
                    return  '文件大小超出';
                    break;

                case 'interrupt':
                    return  '上传暂停';
                    break;

                case 'invalid':
                    return '文件无效';
                    break;

                case 'unknown':
                    return  '无效的图片';
                    break;

                default:
                    return '上传失败，请重试';
                    break;
            }
        }
        // 当有文件添加进来时执行，负责view的创建
        function addFile(file) {
            var $li = $( '<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>'+
                    '<p class="progress"><span></span></p>' +
                    '</li>' ),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>'),
                showError = function( code ) {
                    switch( code ) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        case 'invalid':
                            text = '文件无效';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text( text ).appendTo( $li );
                };
            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                $wrap.text( '预览中' );
                uploader.getImage(file.file,function (error,src) {
                    var img;

                    if(error){
                        $wrap.text( '不能预览' );
                        file.setStatus(WFile.Status.INVALID,'不能预览');
                        return;
                    }
                    if( isSupportBase64 ) {
                        img = $('<img src="'+src+'">');
                        $wrap.empty().append( img );
                    }else{
                        img = $('<img style="'+src+'">');
                        $wrap.text("预览出错");
                        file.setStatus(WFile.Status.INVALID,'预览出错');
                        $wrap.append( img );
                    }
                });
                percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;
            }

            $li.on( 'mouseenter', function() {
                $btns.stop().animate({height: 30});
            });

            $li.on( 'mouseleave', function() {
                $btns.stop().animate({height: 0});
            });

            $btns.on( 'click', 'span', function() {
                var index = $(this).index(),
                    deg;

                switch ( index ) {
                    case 0:
                        if(file.xmlHttp){
                            file.xmlHttp.abort();
                        }
                        var id = file.rid;
                        if(id){
                            pub.postJson("delete_image.json",{
                                id:id
                            },function (res) {
                                uploader.removeFile( file ,true);
                            },function (res) {
                                app.showToast(res.msg);
                                // uploader.removeFile( file );
                            });
                        }else{
                            uploader.removeFile( file );
                        }

                        return;

                    case 1:
                        file.rotation += 90;
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if ( supportTransition ) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                }
            });
            $li.appendTo( $queue );
        }

        // 负责view的销毁
        function removeFile( file ) {
            var $li = $('#'+file.id);

            delete percentages[ file.id ];
            $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;

            $.each( percentages, function( k, v ) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            } );

            percent = total ? loaded / total : 0;


            spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
            spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );

            if(fileCount && fileCount > 0){
                $uploadBtn.text("继续添加");
            }else{
                $uploadBtn.text("添加图片");
            }
            updateStatus();
        }

        function updateStatus() {
            var text = '', stats;

            if ( state === 'ready' ) {
                text = '选中' + fileCount + '张图片，共' +
                    uploader.formatFileSize( fileSize ) +"，每张最大"+
                    uploader.formatFileSize(uploader.maxSize);
            } else if ( state === 'confirm' ) {
                stats = uploader.getStats();
                text = '共' + fileCount + '张（' +
                    uploader.formatFileSize( fileSize )  +
                    '），已上传' + stats.successNum + '张';

                if ( stats.uploadFailNum ) {
                    text +=
                        '，' + stats.uploadFailNum + '张上传失败，<a class="retry" href="javascript:;">重新上传</a>失败图片或<a class="ignore" href="javascript:;">忽略</a>'
                }
            } else {
                stats = uploader.getStats();
                text = '共' + fileCount + '张（' +
                    uploader.formatFileSize( fileSize )  +
                    '），已上传' + stats.successNum + '张';

                if ( stats.uploadFailNum ) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }
            text += '。最多'+uploader.maxNumber+'张。';
            $info.html( text );
        }

        function setState( val ) {
            var file, stats;

            if ( val === state ) {
                return;
            }

            $upload.removeClass( 'state-' + state );
            $upload.addClass( 'state-' + val );
            state = val;

            switch ( state ) {
                case 'pedding':
                    $queue.hide();
                    break;

                case 'ready':
                    $upload.text( '开始上传' );
                    $queue.show();
                    break;

                case 'uploading':
                    $upload.text( '暂停上传' );
                    break;

                case 'paused':
                    $progress.show();
                    $upload.text( '继续上传' );
                    break;

                case 'confirm':
                    $progress.hide();
                    $upload.text( '开始上传' );
                    stats = uploader.getStats();
                    /*if ( stats.successNum && !stats.uploadFailNum ) {
                        setState( 'finish' );
                        return;
                    }*/
                    break;
                case 'finish':
                    stats = uploader.getStats();
                    if ( stats.successNum ) {
                        $upload.text( '上传成功' );
                    }
                    break;
            }

            updateStatus();
        }

        //监听文件输入框事件
        $addBtn.on("change",function (file) {
            //添加文件，触发 onFileQueued事件
            uploader.loadFiles(file.target.files);
        });
        $upload.on('click', function() {
            if ( $(this).hasClass( 'disabled' ) ) {
                return false;
            }
            if ( state === 'ready' ) {
                uploader.upload();
            } else if ( state === 'paused' ) {
                uploader.upload();
            } else if ( state === 'uploading' ) {
                uploader.stop();
            }else if ( state === 'finish' ){
                // app.closeTab();
                _window.close();
            }
        });

        $info.on( 'click', '.retry', function() {
            uploader.retry();
        } );

        $info.on( 'click', '.ignore', function() {
            var failFiles = uploader.getFiles(WFile.Status.INVALID,WFile.Status.ERROR);
            for(var i=0;i<failFiles.length;i++){
                failFiles[i].setStatus(WFile.Status.CANCELLED,"忽略取消");
                uploader.removeFile(failFiles[i])
            }
            updateTotalProgress();
        } );

        $upload.addClass( 'state-' + state );
        updateTotalProgress();
    });

    $.WebLoader = {};

    $.WebLoader.setUploaderData = function (data) {
        uploader.data = data;
    };


    /************************************* uploader接口 **********************************/

        //类的构建定义，主要职责就是新建XMLHttpRequest对象
    var MyXMLHttpRequest = function(){
        var xmlhttprequest;
        if(window.XMLHttpRequest){
            xmlhttprequest=new XMLHttpRequest();
            if(xmlhttprequest.overrideMimeType){
                xmlhttprequest.overrideMimeType("text/xml");
            }
        }else if(window.ActiveXObject){
            var activeName=["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];
            for(var i=0;i<activeName.length;i++){
                try{
                    xmlhttprequest=new ActiveXObject(activeName[i]);
                    break;
                }catch(e){

                }
            }
        }

        if(xmlhttprequest == undefined || xmlhttprequest == null){
            alert("XMLHttpRequest对象创建失败！！");
        }else{
            this.xmlhttp=xmlhttprequest;
        }
        //用户发送请求的方法
        MyXMLHttpRequest.prototype.send
            =function(method,url,file,data, loadStart,uploadLoadStart,
                      uploadProgress,success,errorProPress,errorAbort,error,errorBegin){
            method=method.toUpperCase();
            if(method!="GET" && method!="POST"){
                alert("HTTP的请求方法必须为GET或POST!!!");
                return;
            }
            if(url==null || url==undefined){
                alert("HTTP的请求地址必须设置！");
                return ;
            }
            var xhr = this.xmlhttp;
            if(xhr){
                xhr.onloadstart = function (event) {
                    loadStart(file,event);
                };
                xhr.upload.onloadstart = function (event) {
                    uploadLoadStart(file,event);
                };
                xhr.onprogress = function (event) {
                    uploadProgress(file,event);
                };
                xhr.onload = function (event) {
                    if((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304){
                        success(file,event);
                    }else{
                        error(file,event);
                    }
                };

                //中断触发事件：
                xhr.upload.onprogress = function (event) {
                    uploadProgress(file,event);
                };
                xhr.upload.onabort = function (event) {
                    errorAbort(file,event);
                };
                xhr.upload.onerror = function (event) {
                    errorBegin(file,event);
                };

                xhr.open(method, url);
                //如果是POST方式，需要设置请求头
               /* if(method=="POST") {
                    xhr.setRequestHeader("Content-type", "application/x-www-four-urlencoded");
                }*/
                xhr.send(data);
            }else{
                alert("XMLHttpRequest对象创建失败，无法发送数据！");
            }
            MyXMLHttpRequest.prototype.abort=function(){
               if(this.xmlhttp){
                   this.xmlhttp.abort();
               }
            }
        }
    };

            /************************************* uploader接口 **********************************/

    var uploader = {};

    var mapping = {};
    //文件队列
    var queue = new fileQueue();

    //文件上传接口
    uploader.url = 'upload.json';

    //上传携带的额外数据
    uploader.data = {};

    uploader.count = 0;

    //是否允许重复 true-->不允许
    uploader.duplicate = true;

    uploader.xmlhttp = null;

    uploader.maxSize = 20971520; //20M

    uploader.maxNumber = 20;

    uploader.setUrlData = function(url,data){
        url = uploader.url || url;
        data = uploader.data || data;
    };
    /*
    * 触发事件 - 开始发送
    * */
    uploader.loadStart = function(file,event){

    };
    /*
     * 触发事件 - 开始上传
     * */
    uploader.uploadLoadStart = function(file,event){

    };
    /*
     * 触发事件 - 进度
     * */
    uploader.uploadProgress = function(file,event){
        if (event.lengthComputable) {
            var completedPercent = event.loaded / event.total;
            if(uploader.onUploadProgress){
                uploader.onUploadProgress(file,completedPercent);
            }
        }
    };
    /*
     * 触发事件 - 成功
     * */
    uploader.success = function(file,event){
        var res = eval('(' + event.target.responseText + ')');
        if(res.ok){
            file.setStatus(WFile.Status.COMPLETE,"上传成功.");
            if(uploader.onUploadSuccess){
                uploader.onUploadSuccess(file,res.data);
            }
        }else{
            file.setStatus(WFile.Status.ERROR,"上传失败,请重试.");
            if(uploader.onUploadError){
                uploader.onUploadError(file,res.msg);
            }
        }
    };
    /*
     * 触发事件 - 错误进度
     * */
    uploader.proPress = function(file,event){
        if (event.lengthComputable) {
            var completedPercent = event.loaded / event.total;
        }
    };
    /*
     * 触发事件 - 错误中断
     * */
    uploader.errorAbort = function(file,event){

    };
    /*
     * 触发事件 - 错误开始
     * */
    uploader.errorBegin = function(file,event){
        file.setStatus(WFile.Status.ERROR,"上传失败,请重试.");
        if(uploader.onUploadError){
            uploader.onUploadError(file,'上传失败,请重试.');
        }
    };
    /*
     * 触发事件 - 错误结束
     * */
    uploader.error = function(file,event){
        file.setStatus(WFile.Status.ERROR,"上传失败,请重试.");
        if(uploader.onUploadError){
            uploader.onUploadError(file,event.target.responseText);
        }
    };

    /*
    * 上传任务结果事件
    * */
    uploader.onUploadTaskEnd = function () {
        var invalidFiles = uploader.getFiles(WFile.Status.INVALID);
        //更新无效文件 为 上传错误
        for(var i=0;i<invalidFiles.length;i++){
            invalidFiles[i].setStatus(WFile.Status.ERROR,invalidFiles[i].statusText);
        }
    };
    /*
     * 上传成功事件
     * */
    uploader.onUploadFinished = function () {

    };
    /*
     * 开始上传事件
     * */
    uploader.onStartUpload = function () {
        var invalidFiles = uploader.getFiles(WFile.Status.INVALID);
        //更新无效文件 为 上传错误
        for(var i=0;i<invalidFiles.length;i++){
            invalidFiles[i].setStatus(WFile.Status.ERROR,invalidFiles[i].statusText);
        }
    };

    //文件上传
    uploader.upload = function () {
        if(uploader.onStartUpload){
            uploader.onStartUpload();
        }
        var files = uploader.getFiles(WFile.Status.QUEUED);
        for(var i=0;i<files.length;i++){
            var fd = new FormData();
            $.each( uploader.data, function( key, value ) {
                fd.append(key,value);
            });
            fd.append("file",files[i].file);
            uploader.xmlhttp = new MyXMLHttpRequest();
            files[i].xmlHttp = uploader.xmlhttp;
            uploader.xmlhttp.send("POST",uploader.url,files[i],fd,uploader.loadStart,
                uploader.uploadLoadStart, uploader.uploadProgress,uploader.success,
                uploader.proPress,uploader.errorAbort,uploader.error,uploader.errorBegin);
        }
    };

    uploader.retry = function( file, noForceStart ) {
        var files, i, len;

        if ( file ) {
            file = file.id ? file : queue.getFile( file );
            file.setStatus( WFile.Status.QUEUED );
            noForceStart || uploader.upload();
            return;
        }

        files = queue.getFiles( WFile.Status.ERROR );
        i = 0;
        len = files.length;

        for ( ; i < len; i++ ) {
            file = files[ i ];
            file.setStatus( WFile.Status.QUEUED );
        }
        uploader.upload();
    },
    /*
    * 添加文件
    * */
    uploader.loadFiles = function (files) {
        for(var i=0;i<files.length;i++){
            if(uploader.maxNumber && queue.length() >= uploader.maxNumber){
                alert("超过最大数量限制.");
                return ;
            }
            uploader._addFile(files[i]);
        }
    };
    /*
    * 更新文件状态数量
    * */
    uploader.updateFileNumber = function(state,pre){
        queue._onFileStatusChange(state,pre);
    };

    uploader.deleteFileStatsUpdate = function () {
        queue._onFileDeleteFile
    };
    //创建文件
    uploader._wrapFile = function (file) {
        return new WFile( file );
    };
    /*
    * 获取文件的hash值
    * */
    uploader.hashString = function( str ) {
        var hash = 0,
            i = 0,
            len = str.length,
            _char;

        for ( ; i < len; i++ ) {
            _char = str.charCodeAt( i );
            hash = _char + (hash << 6) + (hash << 16) - hash;
        }

        return hash;
    };
    /*
    * 添加文件
    * */
    uploader._addFile = function (file) {
        var WFile_ =  uploader._wrapFile(file);
        var stats = WFile_.getStatus();
        if(uploader.onBeforeFileQueued && uploader.onBeforeFileQueued(file)){
            return ;
        }
        if(!uploader._acceptFile(WFile_)){
            WFile_.setStatus(WFile.Status.INVALID,"invalid");
            // alert("无效文件.");
        }
        var hash = WFile_.__hash || (WFile_.__hash = uploader.hashString( file.name +
                file.size + file.lastModified ));
        if(uploader.onDuplicateFile &&  hash && mapping[ hash ]){
            uploader.onDuplicateFile(file);
            return ;
        }
        hash && (mapping[ hash ] = true);
        queue.append(WFile_);
        if(uploader.maxSize && WFile_.size >= uploader.maxSize){
            WFile_.setStatus(WFile.Status.INVALID,"exceed_size");
        }
        //无效文件不能上传
        if(WFile_.getStatus() == stats){
            WFile_.setStatus(WFile.Status.QUEUED,"准备上传.");
        }

        uploader.onFileQueued(WFile_);
        return WFile_;
    };

    /*
    * 重复文件事件触发，文件添加前触发
    * */
    uploader.onDuplicateFile = function (file) {
        alert("重复文件.");
    };
    /*
    * 事件触发，文件添加前触发
    * */
    uploader.onBeforeFileQueued = function (file) {

    };
    //匹配类型
    uploader._acceptFile = function (file) {
        var invalid = !file || !file.size /*&&

            // 如果名字中有后缀，才做后缀白名单处理。
            rExt.exec( file.name ) && !this.accept.test( file.name )*/;

        return !invalid;
    };
    /*
    * 获取所有文件队列
    * */
    uploader.getAllFiles = function () {
        return queue.getAllFiles();
    };

    /*
    * 获取所选状态状态文件，获取所选状态的文件
    * @type : 状态数组 （WFile.Status. ...）
    * */
    uploader.getFiles = function () {
        return queue.getFiles.apply( queue, arguments );
    };

    /*
    * 获取文件
    * @fileId: 文件ID
    * */
    uploader.getFile = function (fileId) {
        return queue.getFile(fileId);
    };
    /*
    * 删除文件
    * remove:true 删除queue中的文件，remove:false 标记为‘已取消’，不删除文件
    * */
    uploader.removeFile = function (file,remove) {
        file = file.id ? file : queue.getFile( file );
        file.setStatus(WFile.Status.CANCELLED,"文件删除.");

        var hash = file.__hash;
        hash && (delete mapping[ hash ]);

        if(remove){
            queue.removeFile(file);
        }
        uploader.onFileDequeued(file);

    };

    /*
    * 获取文件的状态数量
    * */
    uploader.getStats = function () {
        var stats = queue.stats;
        return stats ? {
                successNum: stats.numOfSuccess,
                progressNum: stats.numOfProgress,

                // who care?
                // queueFailNum: 0,
                cancelNum: stats.numOfCancel,
                invalidNum: stats.numOfInvalid,
                uploadFailNum: stats.numOfUploadFailed,
                queueNum: stats.numOfQueue,
                interruptNum: stats.numofInterrupt
            } : {};
    };

    //文件添加事件
    uploader.onFileQueued = function (file) {
        uploader.count++;
    };
    //文件删除事件
    uploader.onFileDequeued = function (file) {
        uploader.count--;
    };
    //获取图像数据
    uploader.getImage = function (file,callback) {
        // 只预览图片格式。
        if ( !file.type.match( /^image/ ) ) {
            callback( true );
            return;
        }
        if(file){
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function(evt){
                callback(false,evt.target.result);
            }
        }else{
            var style = "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'";
            callback(false,style);
        }
    };

    //格式化文件大小
    uploader.formatFileSize = function (fileSize) {
        if (fileSize < 1024) {
            return fileSize + 'B';
        } else if (fileSize < (1024*1024)) {
            var temp = fileSize / 1024;
            temp = temp.toFixed(2);
            return temp + 'KB';
        } else if (fileSize < (1024*1024*1024)) {
            var temp = fileSize / (1024*1024);
            temp = temp.toFixed(2);
            return temp + 'MB';
        } else {
            var temp = fileSize / (1024*1024*1024);
            temp = temp.toFixed(2);
            return temp + 'GB';
        }
    };


    /************************************* fileQueue类 **********************************/

    function fileQueue() {

        /**
         * 统计文件数。
         * * `numOfQueue` 队列中的文件数。
         * * `numOfSuccess` 上传成功的文件数
         * * `numOfCancel` 被取消的文件数
         * * `numOfProgress` 正在上传中的文件数
         * * `numOfUploadFailed` 上传错误的文件数。
         * * `numOfInvalid` 无效的文件数。
         * * `numofDeleted` 被移除的文件数。
         * @property {Object} stats
         */
        this.stats = {
            numOfQueue: 0,
            numOfSuccess: 0,
            numOfCancel: 0,
            numOfProgress: 0,
            numOfUploadFailed: 0,
            numOfInvalid: 0,
            numofDeleted: 0,
            numofInterrupt: 0
        };

        // 上传队列，仅包括等待上传的文件
        this._queue = [];

        // 存储所有文件
        this._map = {};
    }

    $.extend( fileQueue.prototype, {

        /**
         * 将新文件加入对队列尾部
         *
         * @method append
         * @param  {File} file   文件对象
         */
        append: function( file ) {
            this._queue.push( file );
            this._fileAdded( file );
            return this;
        },

        length: function(){
            return this._queue.length - this.getFiles(WFile.Status.CANCELLED).length;
        },

        /**
         * 将新文件加入对队列头部
         *
         * @method prepend
         * @param  {File} file   文件对象
         */
        prepend: function( file ) {
            this._queue.unshift( file );
            this._fileAdded( file );
            return this;
        },

        /**
         * 获取文件对象
         *
         * @method getFile
         * @param  {String} fileId   文件ID
         * @return {File}
         */
        getFile: function( fileId ) {
            if ( typeof fileId !== 'string' ) {
                return fileId;
            }
            return this._map[ fileId ];
        },
        /**
         * 从队列中取出一个指定状态的文件。
         * @grammar fetch( status ) => File
         * @method fetch
         * @param {String} status [文件状态值](#WebUploader:File:File.Status)
         * @return {File} [File](#WebUploader:File)
         */
        fetch: function( status ) {
            var len = this._queue.length,
                i, file;

            status = status || STATUS.QUEUED;

            for ( i = 0; i < len; i++ ) {
                file = this._queue[ i ];

                if ( status === file.getStatus() ) {
                    return file;
                }
            }

            return null;
        },
        /**
         * 对队列进行排序，能够控制文件上传顺序。
         * @grammar sort( fn ) => undefined
         * @method sort
         * @param {Function} fn 排序方法
         */
        sort: function( fn ) {
            if ( typeof fn === 'function' ) {
                this._queue.sort( fn );
            }
        },

        /**
         * 获取指定类型的文件列表, 列表中每一个成员为[File](#WebUploader:File)对象。
         * @grammar getFiles( [status1[, status2 ...]] ) => Array
         * @method getFiles
         * @param {String} [status] [文件状态值](#WebUploader:File:File.Status)
         */
        getFiles: function() {
            var sts = [].slice.call( arguments, 0 ),
                ret = [],
                i = 0,
                len = this._queue.length,
                file;

            for ( ; i < len; i++ ) {
                file = this._queue[ i ];

                if ( sts.length && !~$.inArray( file.getStatus(), sts ) ) {
                    continue;
                }

                ret.push( file );
            }

            return ret;
        },

        /**
         * 在队列中删除文件。
         * @grammar removeFile( file ) => Array
         * @method removeFile
         * @param {File} 文件对象。
         */
        removeFile: function( file ) {
            var me = this,
                existing = this._map[ file.id ];

            if ( existing ) {
                delete this._map[ file.id ];
                file.destroy();
                this.stats.numofDeleted++;
                this.stats.numOfSuccess--;
            }
        },

        _fileAdded: function( file ) {
            var me = this,
                existing = this._map[ file.id ];

            if ( !existing ) {
                this._map[ file.id ] = file;

               /* file.on( 'statuschange', function( cur, pre ) {
                    me._onFileStatusChange( cur, pre );
                });*/
            }
        },

        getAllFiles: function(){
            return this._queue;
        },

        _onFileDeleteFile:function () {
            var stats = this.stats;
            stats.numOfSuccess--;
        },

        _onFileStatusChange: function( curStatus, preStatus ) {
            var stats = this.stats;

            switch ( preStatus ) {
                case STATUS.PROGRESS:
                    stats.numOfProgress--;
                    break;

                case STATUS.QUEUED:
                    stats.numOfQueue --;
                    break;

                case STATUS.ERROR:
                    stats.numOfUploadFailed--;
                    break;

                case STATUS.INVALID:
                    stats.numOfInvalid--;
                    break;

                case STATUS.INTERRUPT:
                    stats.numofInterrupt--;
                    break;
            }

            switch ( curStatus ) {
                case STATUS.QUEUED:
                    stats.numOfQueue++;
                    break;

                case STATUS.PROGRESS:
                    stats.numOfProgress++;
                    break;

                case STATUS.ERROR:
                    stats.numOfUploadFailed++;
                    break;

                case STATUS.COMPLETE:
                    stats.numOfSuccess++;
                    break;

                case STATUS.CANCELLED:
                    stats.numOfCancel++;
                    break;


                case STATUS.INVALID:
                    stats.numOfInvalid++;
                    break;

                case STATUS.INTERRUPT:
                    stats.numofInterrupt++;
                    break;
            }
        }

    });

    /************************************* WFile类 **********************************/

    WFile.Status = {
        INITED:     'inited',    // 初始状态
        QUEUED:     'queued',    // 已经进入队列, 等待上传
        PROGRESS:   'progress',    // 上传中
        ERROR:      'error',    // 上传出错，可重试
        COMPLETE:   'complete',    // 上传完成。
        CANCELLED:  'cancelled',    // 上传取消。
        INTERRUPT:  'interrupt',    // 上传中断，可续传。
        INVALID:    'invalid'    // 文件不合格，不能重试上传。
    };

    var idPrefix = 'W_FILE_';
    var idSuffix = 0;

    function gid() {
        return idPrefix + idSuffix++;
    }
    function WFile(file) {
        this.file = file;
        this.size = file.size;
        this.name = file.name;
        this.type = file.type;
        this.lastModifiedDate = file.lastModifiedDate || new Date() + 1;
        this.id = gid();
        this.statusText = '';
        this.state = this.setStatus(WFile.Status.INITED);
     /*   this.on( 'error', function( msg ) {
            this.setStatus( WFile.Status.ERROR,msg );
        });*/
    }

    var STATUS = WFile.Status;

    $.extend( WFile.prototype, {

        /**
         * 设置状态，状态变化时会触发`change`事件。
         * @method setStatus
         * @grammar setStatus( status[, statusText] );
         * @param {File.Status|String} status [文件状态值](#WebUploader:File:File.Status)
         * @param {String} [statusText=''] 状态说明，常在error时使用，用http, abort,server等来标记是由于什么原因导致文件错误。
         */
        setStatus: function( status,text ) {
            var prevStatus = this.state;
            typeof text !== 'undefined' && (this.statusText = text);

            if ( status !== prevStatus ) {
                this.state = status;
            }
            uploader.updateFileNumber(status,prevStatus);
            return prevStatus;
        },

        /**
         * 获取文件状态
         * @return {File.Status}
         * @example
         文件状态具体包括以下几种类型：
         {
             // 初始化
            INITED:     0,
            // 已入队列
            QUEUED:     1,
            // 正在上传
            PROGRESS:     2,
            // 上传出错
            ERROR:         3,
            // 上传成功
            COMPLETE:     4,
            // 上传取消
            CANCELLED:     5
        }
         */
        getStatus: function() {
            return this.state;
        },
        destroy: function() {
            delete this.file;
        }
    });

})(jQuery);


