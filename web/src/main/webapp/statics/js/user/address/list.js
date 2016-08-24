myApp.onPageInit('user.address.list', function(page) {
			$$('form.ajax-submit.user-address-list-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-address-list-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.user-address-list-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

		});

function user_address_list_delete(addId) {
	myApp.confirm('确定删除地址？', '地址管理', function() {
				myApp.showIndicator();

				$$('#user_address_list_addId').val(addId);
				$$('#user/address/list/delete').trigger("submit");
			});
}