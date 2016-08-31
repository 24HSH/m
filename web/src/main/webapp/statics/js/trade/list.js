var trade_list_timer;

myApp.onPageInit('trade.list', function(page) {
			// 下拉刷新页面
			var ptrContent = $$('.pull-to-refresh-content');

			// 添加'refresh'监听器
			ptrContent.on('refresh', function(e) {
						// 模拟1s的加载过程
						setTimeout(function() {
									// 列表元素的HTML字符串
									var itemHTML = '';
									// 前插新列表元素
									// ptrContent.find('.more').prepend(itemHTML);
									// 加载完毕需要重置
									myApp.pullToRefreshDone();
								}, 1000);
					});

			$$('form.ajax-submit.trade-list-cancel').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-list-sign').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-list-copy').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-list-cancel').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;

						// member_index_stats();
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.trade-list-sign').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;

						// member_index_stats();
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.trade-list-copy').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;

				// member_index_stats();
				myApp.getCurrentView().router.load({
							url : appUrl + "/item/list.htm?shopId="
									+ xhr.responseText,
							ignoreCache : true
						});
			});

			$$('form.ajax-submit.trade-list-cancel').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('form.ajax-submit.trade-list-sign').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('form.ajax-submit.trade-list-copy').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			// trade_list_stats();

			// 待付款 订单 倒计时
			clearTimeout(trade_list_timer);
			trade_list_count(0);
		});

function trade_list(type, code) {
	myApp.getCurrentView().router.load({
				url : appUrl + "/trade/list.htm?type=" + type + "&code=" + code,
				ignoreCache : true,
				reload : true
			});
}

function trade_list_cancel(tradeNo) {
	myApp.confirm('确定取消订单？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade_list_cancel_tradeNo').val(tradeNo);
				$$('#trade/list/cancel').trigger("submit");
			});
}

function trade_list_sign(tradeNo) {
	myApp.confirm('确定收货？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade_list_sign_tradeNo').val(tradeNo);
				$$('#trade/list/sign').trigger("submit");
			});
}

function trade_list_copy(tradeNo) {
	myApp.showIndicator();

	$$('#trade_list_copy_tradeNo').val(tradeNo);
	$$('#trade/list/copy').trigger("submit");
}

function trade_list_stats() {
	$$.get(appUrl + '/trade/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#trade/list/topay').html(stats[0]);
				$$('#trade/list/tosend').html(stats[1]);
				$$('#trade/list/send').html(stats[2]);
				$$('#trade/list/sign').html(stats[3]);
			});
}

function trade_list_count(times) {
	$$(".card .card-header .pay-time-left").each(function() {
				var count = $$(this).data("count") - times;
				if (count > 0) {
					var m = parseInt(count / 60, 10);
					var s = parseInt(count - m * 60);

					if (m > 0) {
						$$(this).html("(还剩" + m + "分" + s + "秒)");
					} else {
						$$(this).html("(还剩" + s + "秒)");
					}
				} else {
					$$(this).html("");
				}
			});

	if (++times > 900) {
		clearTimeout(trade_list_timer);
	} else {
		trade_list_timer = setTimeout(function() {
					trade_list_count(times);
				}, 1000);
	}
}
