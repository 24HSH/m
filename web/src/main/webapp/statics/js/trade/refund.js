myApp.onPageInit('trade.refund', function(page) {
			$$('form.ajax-submit.trade-refund-refund').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-refund-refund').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									member_index_stats();
									myApp.getCurrentView().router.back();
								});
					});

			$$('form.ajax-submit.trade-refund-refund').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function trade_refund_refund(tradeNo) {
	myApp.confirm('确定申请退款？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade/refund/refund').trigger("submit");
			});
}