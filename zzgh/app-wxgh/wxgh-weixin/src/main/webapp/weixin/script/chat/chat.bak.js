var MessageType = {
    TEXT: 'txt',
    IMAGE: 'image',
    VOICE: 'voice',
    LOCATION: 'location',
    ACTIVITIES: 'activits',
    NOTICE: 'notice'
}

var refresh = window.refresh('#refreshContainer', {
    url: homePath + '/wx/chat/list_msg.json',
    data: {groupId: groupId},
    dataEl: '.ui-msg-list',
    ispage: true,
    cntdown: {
        contentdown: "拉取消息",
        contentover: "释放立即拉取",
        contentrefresh: "正在拉取消息..."
    },
    intercept: refreshIntercept,
    up: false,
    bindFn: bindfn,
    errorFn: function (type) {
        console.log(type)
    }
})

function refreshIntercept() {
    if (!refresh.isFirst) {
        refresh.isFirst = true
    } else {
        refresh.setPage(Number(refresh.getPage()) + 1)
    }
    return refresh.getPage()
}

function bindfn(d) {
    page.msg.create_msgs(d.datas)
}

function refresh_resize() {
    var $refreshDiv = $('#refreshContainer');
    $refreshDiv.css('bottom', $('#chatBottom').outerHeight() + 4)
}

var page = {
    init: function () {
        this.init_size();

        wxgh.previewImageInit(); //聊天界面图片预览

        this.otherDiv.init() //更多菜单初始化
        this.chatInput.init(); //发送输入框初始化
        this.sendBtn.init(); //发送按钮
        this.sendVoice.init(); //发送语音

        this.sockect.init()

        mui('.ui-head').on('tap', 'a.mui-icon-person-filled', function () {
            mui.openWindow(this.getAttribute('href'))
        })

        window.onresize = this.init_size();
    },
    init_size: function () {
        refresh_resize()
    },
    otherDiv: {
        init: function () {
            var self = $('#otherDiv')
            var plusBtn = $('#plusBtn')
            plusBtn.on('tap', function () {
                self.toggleClass('mui-hidden')
                refresh_resize()
                refresh.scrollBottom();
            })

            mui(self).on('tap', 'a[data-type]', function () {
                var type = this.getAttribute('data-type')
                if (type == 'image') {
                    page.otherDiv.chooseImg()
                } else if (type == 'activities') {
                    mui.openWindow(this.getAttribute('href'))
                } else if (type == 'location') {
                    //ui.showToast("暂未开放")
                    /* {lat:'', lng:'', speed:'', accuracy:'', address:''} */
                    page.otherDiv.location.send()
                } else if (type == 'actresult') {
                    mui.openWindow(this.href);
                }
            })
            this.location.init()
            this.self = self;
            this.plusBtn = plusBtn;
        },
        location: {
            init: function () {
                var popover = $('#locationPopover')
                $('#sendLocationBtn').on('tap', function () {
                    var el = null;
                    $.each($('#locationPopover .mui-table-view-radio .mui-table-view-cell'), function () {
                        if ($(this).hasClass('mui-selected')) {
                            el = this;
                            return;
                        }
                    })
                    if (el) {
                        page.sendBtn.send_request(MessageType.LOCATION, {
                            lat: el.getAttribute('lat'),
                            lng: el.getAttribute('lng'),
                            speed: 0,
                            accuracy: 0,
                            address: el.getAttribute('addr'),
                            title: el.getAttribute('title')
                        }, function () {
                            mui('#locationPopover').popover('hide')
                        })
                    } else {
                        mui.toast('请选择地址哦');
                    }
                })
                this.popver = popover
            },
            create_item: function (addr) {
                var e_ul = this.popver.querySelector('.mui-table-view')
                e_ul.innerHTML = ''

                if (addr.status == 0) {
                    //拼凑当前位置
                    var res = addr.result;
                    var item1 = document.createElement('li')
                    item1.setAttribute('lat', res.location.lat)
                    item1.setAttribute('lng', res.location.lng)
                    item1.setAttribute('title', '')
                    item1.setAttribute('addr', res.formatted_address)
                    item1.className = 'mui-table-view-cell mui-selected'
                    item1.innerHTML = '<a class="mui-navigate-right">' +
                        '<p class="mui-ellipsis-2 ui-text-black">' + res.formatted_address + '</p>' +
                        '</a>';
                    e_ul.appendChild(item1)

                    var pois = res.pois
                    if (pois && pois.length > 0) {
                        for (var i = 0; i < pois.length; i++) {
                            var p = pois[i];
                            var item = document.createElement('li')
                            item.setAttribute('lat', p.point.y)
                            item.setAttribute('lng', p.point.x)
                            item.setAttribute('addr', p.addr)
                            item.setAttribute('title', p.name)
                            item.className = 'mui-table-view-cell'
                            item.innerHTML = '<a class="mui-navigate-right">' +
                                '<h5 class="ui-text-black">' + p.name + '</h5>' +
                                '<p class="mui-ellipsis-2">' + p.addr + '</p>' +
                                '</a>';
                            e_ul.appendChild(item)
                        }
                    }

                    mui(wxgh.query('#locationPopover .mui-scroll-wrapper')).scroll({
                        deceleration: 0.0005
                    });
                } else {
                    e_ul.innerHTML = '<div class="mui-content-padded mui-text-center ui-text-info">获取位置失败</div>'
                }
                this.popver.style.left = ((window.screen.availWidth - 280) / 2) + 'px';
                this.popver.style.top = ((window.screen.availHeight - 340) / 2) + 'px';
            },
            send: function () {
                if (!this.showloading) {
                    this.showloading = ui.loading('加载中...');
                }
                this.showloading.show()
                var self = this
                wx.getLocation({
                    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                    success: function (res) {
                        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                        var speed = res.speed; // 速度，以米/每秒计
                        var accuracy = res.accuracy; // 位置精度

                        var url = homePath + '/wx/chat/send/get_location.json';
                        var ifo = {
                            lng: longitude,
                            lat: latitude
                        }
                        mui.getJSON(url, ifo, function (res) {
                            page.otherDiv.location.create_item(res.data)
                            self.showloading.hide()
                            mui('#locationPopover').popover('show')
                        })
                    }
                });
            }
        },
        chooseImg: function () {
            wx.chooseImage({
                count: 1, // 默认9
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    var localIds = res.localIds
                    if (localIds && localIds.length > 0) {
                        wx.uploadImage({
                            localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                            isShowProgressTips: 1, // 默认为1，显示进度提示
                            success: function (res) {
                                var serverId = res.serverId; // 返回图片的服务器端ID
                                var url = homePath + '/wx/chat/send/image.json'
                                var info = {
                                    msgType: MessageType.IMAGE,
                                    username: userName,
                                    avatar: userAvatar,
                                    userid: userId,
                                    mediaid: serverId
                                }
                                mui.getJSON(url, info, function (res) {
                                    if (res.ok) {
                                        info['time'] = '刚刚';
                                        info['content'] = {imgSrc: localIds[0]}
                                        page.msg.send_message(info)
                                    } else {
                                        mui.toast(res.msg)
                                    }
                                })
                            }
                        });
                    }
                }
            });
        }
    },
    sendBtn: {
        init: function () {
            var self = $('#sendBtn')
            self.on('tap', function () {
                page.sendBtn.sendMessage()
            })
            this.self = self
        },
        sendMessage: function () {
            var msg = page.chatInput.self.val()
            if (!msg) {
                mui.toast('发送内容不能为空哦')
                return;
            }
            this.send_request(MessageType.TEXT, {text: encodeURIComponent(msg)}, function () {
                page.chatInput.self.val('')
                page.chatInput.resize();
            })
        },
        send_request: function (type, cnt, func) {
            var info = {
                msgType: type,
                username: userName,
                avatar: userAvatar,
                userid: userId,
                content: JSON.stringify(cnt)
            }
            var url = homePath + '/wx/chat/send/message.json';
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    if (func) func()
                    info['time'] = '刚刚';
                    page.msg.send_message(info)
                } else {
                    mui.toast('发送失败')
                }
            })
        }
    },
    sendVoice: {
        init: function () {
            var voiceBtn = $('#voiceBtn')
            var self = $('#voiceDiv')

            var recordDiaglog = $('#recordDialog')
            this.recordImg = recordDiaglog.find('#recordImage')

            var toastTxtDiv = recordDiaglog.find('.weui_toast_content');

            this.timer.init();

            self.on('touchstart', function (e) {
                e.preventDefault();
                page.sendVoice.isSend = true;
                toastTxtDiv.text('上滑取消发送')

                recordDiaglog.removeClass('mui-hidden')

                mui.later(function () {
                    page.sendVoice.timer.start();
                }, 400)
                wx.startRecord();
            })

            //长按，开始录音并显示dialog
            self.on('longtap', function (e) {

            })

            var clientY = window.screen.availHeight;

            self.on('touchmove', function (e) {
                var touch = e.originalEvent.targetTouches[0];
                var endx = touch.pageX;
                var endy = touch.pageY;
                console.log(endy < (clientY - 140))
                if (endy < (clientY - 140)) {
                    toastTxtDiv.text('松开取消发送')
                    page.sendVoice.isSend = false;
                } else {
                    toastTxtDiv.text('上滑取消发送')
                    page.sendVoice.isSend = true;
                }
                e.preventDefault();
            })

            //离开屏幕，结束录音并隐藏dialog
            self.on('release', function () {
                console.log('release')
                page.sendVoice.timer.stop();
                wxgh.addClass('mui-hidden')

                wx.stopRecord({
                    success: function (res) {
                        if (page.sendVoice.isSend) {
                            var localId = res.localId;
                            page.sendVoice.upload(localId);
                        }
                    }
                });
            })

            wx.onVoiceRecordEnd({
                // 录音时间超过一分钟没有停止的时候会执行 complete 回调
                complete: function (res) {
                    var localId = res.localId;

                    page.sendVoice.timer.stop();
                    recordDiaglog.addClass('mui-hidden')

                    page.sendVoice.upload(localId);
                }
            });

            voiceBtn.on('tap', function () {
                if (self.hasClass('mui-hidden')) {
                    page.chatInput.self.addClass('mui-hidden')
                    self.removeClass('mui-hidden')
                } else {
                    page.chatInput.self.removeClass('mui-hidden')
                    self.addClass('mui-hidden')
                }
            })

            this.self = self;
        },
        upload: function (localId) {
            if (localId) {
                if (!page.sendVoice.loadings) {
                    page.sendVoice.loadings = ui.loading('发送中...');
                }
                page.sendVoice.loadings.show();
                wx.uploadVoice({
                    localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
                    isShowProgressTips: 0,// 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回音频的服务器端ID

                        page.sendVoice.requet(serverId);
                    }
                });
            }
        },
        requet: function (mediaId, func) {
            var info = {
                msgType: MessageType.VOICE,
                username: userName,
                avatar: userAvatar,
                userid: userId,
                mediaid: mediaId,
                second: 0
            }
            var url = homePath + '/wx/chat/send/voice.json';
            mui.getJSON(url, info, function (res) {
                page.sendVoice.loadings.hide();
                if (res.ok) {
                    if (func) func()
                    info['time'] = '刚刚';
                    info['content'] = JSON.parse(res.data);
                    page.msg.send_message(info)
                } else {
                    mui.toast('发送失败')
                }
            })
        },
        timer: {
            init: function () {
                this.i = 1;
            },
            start: function () {
                var i = this.i;
                //动态切换图片
                this.interval = setInterval(function () {
                    if (i == 15) i = 1;
                    console.log(homePath + '/weixin/image/chat/record/record_animate_' + (i < 10 ? ('0' + i) : i) + '.png')
                    page.sendVoice.recordImg.attr('src', homePath + '/weixin/image/chat/record/record_animate_' + (i < 10 ? ('0' + i) : i) + '.png');
                    i++;
                }, 150);
            },
            stop: function () {
                clearInterval(this.interval);
            }
        }
    },
    chatInput: {
        init: function () {
            var self = $('#chatInput');
            self.on('focus', this.onFocus);

            var inputHeight = self.outerHeight();
            self.on('keyup', function () {
                //var scHeight = this.scrollHeight;
                //if (scHeight <= 73) {
                //    this.style.height = this.scrollHeight + 'px';
                //}
                page.chatInput.resize()
                refresh.scrollBottom()
            })

            this.self = self;
            this.inputHeight = inputHeight;
        },
        resize: function () {
            var scHeight = page.chatInput.self[0].scrollHeight;
            var txt = page.chatInput.self.val();
            if (scHeight <= 73) {
                page.chatInput.self.css('height', page.chatInput.self[0].scrollHeight);
            }

            if (txt.length <= 0) {
                page.chatInput.self.css('height', page.chatInput.inputHeight)
                if (!page.sendBtn.self.hasClass('mui-hidden')) {
                    page.sendBtn.self.addClass('mui-hidden')
                    if (page.otherDiv.plusBtn.hasClass('mui-hidden')) {
                        page.otherDiv.plusBtn.removeClass('mui-hidden')
                    }
                }
            } else {
                if (page.sendBtn.self.hasClass('mui-hidden')) {
                    page.sendBtn.self.removeClass('mui-hidden')
                    if (!page.otherDiv.plusBtn.hasClass('mui-hidden')) {
                        page.otherDiv.plusBtn.addClass('mui-hidden')
                    }
                }
            }
        },
        onFocus: function (e) {
            if (!page.otherDiv.self.hasClass('mui-hidden')) {
                page.otherDiv.self.addClass('mui-hidden')
                refresh_resize()
            }
            mui.later(function () {
                refresh.scrollBottom()
            }, 500)
        }
    },
    msg: {
        create_msgs: function (msgs) {
            if (msgs && msgs.length > 0) {
                for (var i = 0; i < msgs.length; i++) {
                    var item = this.create_item(msgs[i])
                    refresh.addItem(item)
                }
            } else {
                refresh.empty()
                refresh.addItem('<div style="background-color: #efeff4;" class="mui-content-padded mui-text-center ui-text-info">暂无消息</div>')
            }
        },
        send_message: function (msg) {

            var item = this.create_item(msg)
            refresh.addItem(item)
            refresh.scrollBottom(); //滚动到底部
        },
        create_item: function (message) {
            if (typeof message == 'string') {
                message = JSON.parse(message)
            }
            var cnt = message.content;
            if (typeof cnt == 'string') {
                var cnt = JSON.parse(cnt);
            }

            //如果为活动
            if (message.msgType == MessageType.ACTIVITIES) {
                return this.act_msg(cnt);
            }

            if (message.msgType == MessageType.NOTICE) {
                return this.notice_msg(cnt);
            }

            var e_item = document.createElement("div")
            e_item.className = 'ui-msg-item'
            if (message.userid != userId) { //接收的消息
                e_item.classList.add('ui-left')
            } else {
                e_item.classList.add('ui-right')
            }

            //发送时间
            if (message.time) {
                var e_p = document.createElement("p")
                e_p.className = 'mui-text-center ui-msg-time'
                e_p.innerText = message.time
            }

            var e_main = document.createElement('div')
            e_main.className = 'ui-msg-main'

            //头像
            var e_avatar = document.createElement("img")
            e_avatar.className = 'ui-user-head'
            e_avatar.src = (message.avatar ? message.avatar : homePath + '/image/default/user.png')

            //message body
            var e_body = document.createElement("div")
            e_body.className = "ui-msg-body"

            //用户名
            var e_username = document.createElement("small")
            e_username.className = 'ui-text-info'
            e_username.innerText = message.username + '：'

            //msg bg
            var e_bg = document.createElement("div")
            e_bg.className = 'ui-msg-bg'

            switch (message.msgType) {
                case MessageType.TEXT:
                    /* {text:''} */
                    e_bg.innerText = decodeURIComponent(cnt.text)
                    break;
                case MessageType.IMAGE:
                    /* {imgSrc:""} */
                    e_bg.appendChild(this.image_msg(cnt))
                    break;
                case MessageType.LOCATION:
                    e_bg.classList.add('ui-location-bg')
                    e_bg.appendChild(this.location_msg(cnt, message.userid))
                    break;
                case MessageType.VOICE:
                    e_bg.classList.add('ui-voice')
                    e_bg.appendChild(this.voice_msg(cnt, message.userid));
                    this.init_voice_envent(e_bg);
                    break;
            }

            e_body.appendChild(e_username)
            e_body.appendChild(e_bg)

            if (e_p) e_item.appendChild(e_p);

            if (message.userid != userId) { //接收的消息
                e_main.appendChild(e_avatar)
                e_main.appendChild(e_body)
            } else {
                e_main.appendChild(e_body)
                e_main.appendChild(e_avatar)
            }
            e_item.appendChild(e_main)
            return e_item;
        },
        init_voice_envent: function (e_bg) { //初始化语音点击事件
            var e_audio = e_bg.querySelector('audio');
            e_bg.addEventListener('tap', function () {
                if (e_audio.paused) {
                    e_audio.play(); //播放
                } else {
                    e_audio.pause(); //暂停
                }
            });
        },
        voice_msg: function (cnt, uid) {
            var e_voiceBody = document.createElement('div');
            e_voiceBody.className = 'ui-voice-body'

            var e_img = document.createElement('img')
            e_img.className = 'ui-voice-img'

            var e_small = document.createElement('small')
            e_small.className = 'ui-voice-time'
            e_small.innerText = parseInt(cnt.second / 1000) + 's';

            var e_audio = document.createElement('audio');
            e_audio.src = wxgh.get_image(cnt.voiceSrc);
            e_audio.setAttribute('hidden', 'hidden');
            e_audio.setAttribute('controls', 'controls');
            e_audio.setAttribute('preload', 'preload')

            if (uid != userId) {
                e_img.src = homePath + '/weixin/image/chat/msg/rc_ic_voice_receive.png';
                e_voiceBody.appendChild(e_img);
                e_voiceBody.appendChild(e_small);
            } else {
                e_img.src = homePath + '/weixin/image/chat/msg/rc_ic_voice_sent.png';
                e_voiceBody.appendChild(e_small);
                e_voiceBody.appendChild(e_img);
            }
            e_voiceBody.appendChild(e_audio);
            return e_voiceBody;
        },
        location_msg: function (cnt, uid) {
            /* {lat:'', lng:'', speed:'', accuracy:'', address:'', title:''} */
            var ebody = document.createElement('div')
            ebody.className = 'ui-location-body'
            //ebody.setAttribute('lat', cnt.lat)
            //ebody.setAttribute('lng', cnt.lng)
            //ebody.setAttribute('title', cnt.title)
            //ebody.setAttribute('addr', cnt.address)

            var e_txt = document.createElement('div')
            e_txt.className = 'ui-location-txt'
            e_txt.innerHTML = '<h5>位置分享</h5><span class="mui-ellipsis-2">' + (cnt.title ? cnt.title : cnt.address) + '</span>'

            var e_img = document.createElement('img')
            e_img.src = homePath + '/weixin/image/chat/msg/msg_location.png'

            if (uid != userId) { //接收的消息
                ebody.appendChild(e_img)
                ebody.appendChild(e_txt)
            } else {
                ebody.appendChild(e_txt)
                ebody.appendChild(e_img)
            }

            ebody.addEventListener('tap', function () {
                //wx.openLocation({
                //    latitude: cnt.lat, // 纬度，浮点数，范围为90 ~ -90
                //    longitude: cnt.lng, // 经度，浮点数，范围为180 ~ -180。
                //    name: cnt.title, // 位置名
                //    address: cnt.address, // 地址详情说明
                //    scale: 16, // 地图缩放级别,整形值,范围从1~28。默认为最大
                //    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                //});
                if (!cnt.lat && !cnt.lng) {
                    ui.showToast('未知位置')
                    return;
                }
                mui.openWindow('http://api.map.baidu.com/marker?location=' + cnt.lat + ',' + cnt.lng + '&title=' + cnt.title + '&content=' + cnt.address + '&output=html')
            })
            return ebody
        },
        image_msg: function (cnt) { //图片消息
            var e_imgdiv = document.createElement('div')
            e_imgdiv.className = 'ui-img-body'
            var e_img = document.createElement("img")
            e_img.src = wxgh.get_image(cnt.imgSrc)
            e_img.setAttribute('data-preview-src', "")
            e_img.setAttribute("data-preview-group", "chat_img")
            e_imgdiv.appendChild(e_img)
            return e_imgdiv
        },
        act_msg: function (cnt) {
            /* {title:'', time:'', imgSrc:'', txt:'', url:''} */
            var e_item = document.createElement('div')
            e_item.className = 'ui-act-item'

            var e_a = document.createElement('a')
            e_a.href = cnt.url;
            e_a.addEventListener('tap', function () {
                mui.openWindow(cnt.url);
            })

            var e_div = document.createElement('div')
            e_div.className = 'ui-act-body';

            e_div.innerHTML = '<h5 class="ui-text-black">【活动】' + (cnt.title ? decodeURIComponent(cnt.title) : '') + '</h5>' +
                '<p class="">' + cnt.time + '</p>' +
                '<img src="' + wxgh.get_image(cnt.imgSrc) + '" />' +
                '<p class="mui-ellipsis-2 ui-text-info">' +
                (cnt.txt ? decodeURIComponent(cnt.txt) : '') + '</p>' +
                '<div><small class="ui-text-black">查看全文</small></div>';

            e_a.appendChild(e_div)
            e_item.appendChild(e_a)
            return e_item;
        },
        notice_msg: function (cnt) {
            /* {title:'', time:'', imgSrc:'', txt:'', url:''} */
            var e_item = document.createElement('div')
            e_item.className = 'ui-act-item'

            var e_a = document.createElement('a')
            e_a.href = cnt.url;
            e_a.addEventListener('tap', function () {
                mui.openWindow(cnt.url);
            })

            var e_div = document.createElement('div')
            e_div.className = 'ui-act-body';

            e_div.innerHTML = '<h5 class="ui-text-black">【公告】' + (cnt.title ? decodeURIComponent(cnt.title) : '') + '</h5>' +
                '<p class="">' + cnt.time + '</p>' +
                '<img src="' + wxgh.get_image(cnt.imgSrc) + '" />' +
                '<p class="mui-ellipsis-2 ui-text-info">' +
                (cnt.txt ? decodeURIComponent(cnt.txt) : '') + '</p>' +
                '<div><small class="ui-text-black">查看全文</small></div>';

            e_a.appendChild(e_div)
            e_item.appendChild(e_a)
            return e_item;
        },
        resize: function (self) {
            //var e_time = self.querySelector('.ui-msg-time');
            //var e_body = self.querySelector('.ui-msg-body');
            //
            //if (e_time) {
            //    self.style.height = (e_body.offsetHeight + e_time.offsetHeight) + 'px';
            //    e_body.style.top = '22px'
            //} else {
            //    e_body.style.top = '2px';
            //    self.style.height = e_body.offsetHeight + 'px';
            //}
        }
    },
    sockect: {
        init: function () {
            this.isConnect = false;

            this.login()
        },
        login: function () {
            var url = homePath + '/wx/chat/login.json'
            var info = {
                id: groupId,
                userid: userId
            }
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    page.sockect.connect(res.data)
                } else {
                    mui.toast(res.msg)
                }
            })
        },
        connect: function (url) {
            url = url.substr(7, url.length)
            var target = 'ws://' + url + '/chat/chat.do';
            if ('WebSocket' in window) {
                this.ws = new WebSocket(target);
            } else if ("MozWebSocket" in window) {
                this.ws = new MozWebSocket(target);
            } else {
                console.error('对不起，您的浏览器不支持...')
            }

            // 连接成功事件
            this.ws.onopen = function () {
                console.log('连接成功...')
                page.sockect.isConnect = true;
            };
            //收到消息
            this.ws.onmessage = function (msg) {
                console.log(msg.data)
                page.msg.send_message(msg.data)
            }

            // 注入断开事件
            this.ws.onclose = function (event) {
                //mui.toast("与服务器失去连接");
                console.warn('与服务器失去连接')
                page.sockect.isConnect = false;
            };
        }
    }
}

window.onload = page.init();