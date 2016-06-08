myApp.onPageInit('pay.index', function(page) {
			$$('form.ajax-submit.pay-index-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.pay-index-form').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						getBrandWCPayRequest(xhr.responseText);

						// 更新首页购物车标记
						portal_homepage_cart_stats();
					});

			$$('form.ajax-submit.pay-index-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function pay_index_pay() {
	myApp.showIndicator();

	$$('#pay_index_wxpay_remark').val($$('#pay_index_input_remark').val());
	$$('#pay/index/wxpay').trigger("submit");
}

function getBrandWCPayRequest(data) {
	data = data.replace(/&quot;/g, "\"");
	var obj = JSON.parse(data);

	try {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
					"appId" : obj.appId,
					"timeStamp" : obj.timeStamp,
					"nonceStr" : obj.nonceStr,
					"package" : obj.packageValue,
					"signType" : obj.signType,
					"paySign" : obj.paySign
				}, function(res) {
					WeixinJSBridge.log(res.err_msg);

					if (res.err_msg == 'get_brand_wcpay_request:ok') {
						myApp.getCurrentView().router.back();
					} else if (res.err_msg == 'get_brand_wcpay_request:fail') {
						myApp.alert(res.err_code + res.err_desc + res.err_msg,
								'错误');
					}
				});
	} catch (e) {
		myApp.alert(e, '错误');
	}
}