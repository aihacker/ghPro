var DatePicker = function(options) {
	var opts = $.extend(DatePicker.DEFAULT, options);
	this.options = opts
}
DatePicker.DEFAULT = {
	date: new Date(),
	el: '',
	itemClick: function() {}
}
DatePicker.HTML = '<div class="panel panel-info ui-datepicker"><div class="panel-heading">' +
	'<span class="fa fa-angle-left"></span>' +
	'<h5 class="text-center"></h5>' +
	'<span class="fa fa-angle-right"></span>' +
	'</div><div class="panel-body">' +
	'<table class="table"><thead><tr>' +
	'<td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td>' +
	'</tr></thead><tbody></tbody></table></div></div>'
DatePicker.prototype = {
	init: function() {
		this.d = moment(this.options.date)
		var $main = $(DatePicker.HTML)
		$(this.options.el).append($main)
		this.$body = $main.find('table tbody')
		this.$title = $main.find('.panel-heading h5')
		this.__month()

		var self = this
		//初始化事件
		$main.on('click', '.fa-angle-left', function() {
			self.d = self.d.subtract(1, 'month')
			var nowD = moment(new Date())
			if(nowD.isSame(self.d, 'month')) {
				self.d.date(nowD.date())
			} else {
				self.d.date(1)
			}
			self.__month()
		})
		$main.on('click', '.fa-angle-right', function() {
			self.d = self.d.add(1, 'month')
			var nowD = moment(new Date())
			if(nowD.isSame(self.d, 'month')) {
				self.d.date(nowD.date())
			} else {
				self.d.date(1)
			}
			self.__month()
		})
	},
	setDate: function(d) {
		this.options.date = d
		this.d = moment(d)
		this.__month()
	},
	__month: function() {

		var curD = this.d.date()

		this.$title.text(this.d.format('YYYY-MM-DD'))

		var firstD = this.d.clone().date(1)
		var lastD = moment(firstD).endOf('month')

		var firstW = firstD.day()

		var d = firstD.clone()
		var isFirst = true
		var isSam = d.isSame(lastD, 'month') == true
		this.$body.empty()
		var self = this
		do {
			var $tr = $('<tr></tr>')
			for(var i = 0; i < 7; i++) {
				isSam = d.isSame(lastD, 'month') == true
				if(!isSam) {
					break
				}
				var $td = $('<td></td>')
				if(isFirst && firstW != i) {
					$td.html('&nbsp;')
				} else {
					if(firstW == i) {
						isFirst = false
					}
					var $span = $('<span>' + d.date() + '</span>')
					if(d.date() == curD) {
						$span.addClass('active')
					}
					$td.append($span)
					d = d.add(1, 'd')

					$span.on('click', function() {
						var $self = $(this)
						self.d = self.d.date($self.text())
						self.options.itemClick(self.d)
						self.$body.find('td span.active').removeClass('active')
						$self.addClass('active')
						self.$title.text(self.d.format('YYYY-MM-DD'))
					})
				}

				$tr.append($td)
			}
			this.$body.append($tr)
		} while (isSam)
	}
}
window.datepicker = function(el, date, func) {
	var optiosn = {
		el: el,
		date: date,
		itemClick: func
	}
	var dp = new DatePicker(optiosn)
	dp.init()
	return dp
}