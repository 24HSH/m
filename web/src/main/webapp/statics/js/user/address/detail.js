myApp.onPageInit('user.address.detail', function(page) {
	$$('form.ajax-submit.user-address-detail-form').on('beforeSubmit',
			function(e) {
			});

	$$('form.ajax-submit.user-address-detail-form').on('submitted',
			function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.getCurrentView().router.back({
					url : myApp.getCurrentView().history[myApp.getCurrentView().history.length
							- 2],
					force : true,
					ignoreCache : true
				});
			});

	$$('form.ajax-submit.user-address-detail-form').on('submitError',
			function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

});

function user_address_detail_update() {
	myApp.showIndicator();

	$$('#user/address/detail/update').trigger("submit");
}