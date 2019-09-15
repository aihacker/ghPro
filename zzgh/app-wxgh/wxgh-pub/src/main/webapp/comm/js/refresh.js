/**
 * 下拉刷新，上拉加载更多
 */
(function ($, re) {
    var Refresh = function (elId, options) {
        this.options = options
        this.elId = elId
        this.$el = document.querySelector(elId + ' ' + options.dataEl)
        this.ispage = options.ispage
    }

    //设置请求参数
    Refresh.prototype.setParam = function (param) {
        $.extend(true, this.options.data, param)
    }

    Refresh.prototype.getParam = function () {
        return this.options.data
    }

    Refresh.prototype.initParam = function (param) {
        this.options.data = param
    }

    Refresh.prototype.addItem = function (item) {
        var htm
        if (typeof item == 'string') {
            htm = this.$el.innerHTML
            htm += item
            this.$el.innerHTML = htm
        } else {
            this.$el.appendChild(item)
        }
    }

    //清空 ul数据
    Refresh.prototype.empty = function () {
        this.$el.innerHTML = ''
    }

    //设置请求地址
    Refresh.prototype.setUrl = function (url) {
        this.options['url'] = url
    }

    //设置当前page
    Refresh.prototype.setPage = function (curpage) {
        this.options.page['current'] = curpage
    }

    Refresh.prototype.getPage = function () {
        return this.options.page['current']
    }

    Refresh.prototype.getTotal = function () {
        return this.options.page['total']
    }

    //设置总共多少页page
    Refresh.prototype.setTotal = function (total) {
        this.options.page['total'] = total
    }

    //下拉刷新回调
    Refresh.prototype.downCallback = function (self) {
        self.request(self, 1, 'down')
    }

    //上拉加载更多回调
    Refresh.prototype.upCallback = function (self) {
        self.request(self, Number(self.getPage()) + 1, 'up')
    }

    Refresh.prototype.request = function (self, curPage, type) {
        if (self.ing && self.ing == 1) {
            return
        }
        var datas = self.options.data
        datas['currentPage'] = curPage
        var url = self.options.url
        var ispage = self.ispage
        $.post(url, datas, function (res) {
            if (type == 'down') {
                self.empty()
            }
            if (res.ok) {
                if (ispage) { //设置当前总页数
                    self.setTotal(res.data.total)
                    self.setPage(res.data.current)
                }
                self.options.bindFn(res.data)
            } else {
                self.options.errorFn(type)
            }
            if (type == 'up') {
                self.endUpRefresh()
            } else {
                self.endDownRefresh()
            }
            self.ing = 0
        }, 'json')
    }

    //初始化方法
    Refresh.prototype.init = function () {
        var eid = this.elId

        var self = this;

        var cntdown = this.options.cntdown
        var cntup = this.options.cntup
        var auto = this.options.auto
        mui.init({
            pullRefresh: {
                container: eid,
                down: {
                    height: 50,
                    auto: auto,
                    contentdown: cntdown.contentdown,
                    contentover: cntdown.contentover,
                    contentrefresh: cntdown.contentrefresh,
                    callback: function () {
                        self.downCallback(self)
                    }
                },
                up: {
                    contentrefresh: cntup.contentrefresh,
                    contentnomore: cntup.contentnomore,
                    callback: function () {
                        self.upCallback(self)
                    }
                }
            }
        });
    }

    //下拉刷新默认配置
    Refresh.DEFAULT = {
        page: {
            current: 1,
            total: 0
        },
        up: true,
        down: true,
        auto: true,
        cntdown: {
            contentdown: "下拉可以刷新",
            contentover: "释放立即刷新",
            contentrefresh: "正在刷新..."
        },
        cntup: {
            contentrefresh: "正在加载...",
            contentnomore: '没有更多数据'
        },
        ispage: false, //服务器是否使用Page bean
        dataEl: '.mui-table-view', //item父Element
        url: 'list.json', //服务器数据请求接口
        data: {}, //请求参数
        bindFn: function (res) {
        }, //数据绑定
        errorFn: function (type) {
        } //上拉加载更多或下拉刷新
    };

    //结束下拉刷新
    Refresh.prototype.endDownRefresh = function () {
        this.setPage(1)
        mui(this.elId).pullRefresh().endPulldownToRefresh();
        if (this.options.up) {
            var hasData = (this.getPage() >= this.getTotal())
            mui(this.elId).pullRefresh().refresh(true)
            mui(this.elId).pullRefresh().endPullupToRefresh(hasData)

            if (this.getTotal() <= 1) {
                mui(this.elId).pullRefresh().disablePullupToRefresh()
            } else {
                mui(this.elId).pullRefresh().enablePullupToRefresh()
            }
        }
    }

    //结束上拉加载
    Refresh.prototype.endUpRefresh = function () {
        var hasData = (this.getPage() >= this.getTotal());
        mui(this.elId).pullRefresh().endPullupToRefresh(hasData);
    }

    Refresh.prototype.refresh = function (param) {
        if (param) this.setParam(param)
        this.empty()
        mui(this.elId).pullRefresh().pulldownLoading()
    }

    re.newInstance = function (id, options) {
        var newOptions = {}
        $.extend(true, newOptions, Refresh.DEFAULT, options)
        var refresh = new Refresh(id, newOptions)
        refresh.init()
        return refresh;
    }

    window.refresh = function (id, options) {
        var newOptions = {}
        $.extend(true, newOptions, Refresh.DEFAULT, options)
        var refresh = new Refresh(id, newOptions)
        refresh.init()
        return refresh;
    }
})(mui, window.fresh = {})