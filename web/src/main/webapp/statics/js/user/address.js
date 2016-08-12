myApp.onPageInit('user.address', function(page) {
			$$('form.ajax-submit.user-address-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-address-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									myApp.getCurrentView().router.back();
								});
					});

			$$('form.ajax-submit.user-address-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

		});

function user_address_create() {
	myApp.showIndicator();

	$$('#user/address/create').trigger("submit");
}