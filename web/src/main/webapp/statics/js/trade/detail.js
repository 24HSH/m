myApp.onPageInit('trade.detail', function(page) {
	$$('form.ajax-submit.trade-detail-delete').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.trade-detail-copy').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.trade-detail-delete').on('submitted', function(e) {
		myApp.hideIndicator();
		var xhr = e.detail.xhr;

		// member_index_stats();
		myApp.getCurrentView().router.back({
			url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
					- 2],
			force : true,
			ignoreCache : true
		});
	});

	$$('form.ajax-submit.trade-detail-copy').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;

				// member_index_stats();
				myApp.getCurrentView().router.load({
							url : appUrl + "/item/list.htm?shopId="
									+ xhr.responseText,
							ignoreCache : true
						});
			});

	$$('form.ajax-submit.trade-detail-delete').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

	$$('form.ajax-submit.trade-detail-copy').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

});

function trade_detail_service() {
	myApp.actions([[{
						text : '<a href="tel:12345678900" class="external">联系商家</a>'
					}, {
						text : '<a href="tel:12345678900" class="external">好社惠客服</a>'
					}], [{
						text : '取消'
					}]]);
}

function trade_detail_delete() {
	myApp.confirm('确定删除订单？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade/detail/delete').trigger("submit");
			});
}

function trade_detail_copy() {
	myApp.showIndicator();

	$$('#trade/detail/copy').trigger("submit");
}