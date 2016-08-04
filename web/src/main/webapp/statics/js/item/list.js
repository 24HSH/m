myApp.onPageInit('item.list', function(page) {
			$$('form.ajax-submit.item-list-trade').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.item-list-cart').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.item-list-trade').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;

				view2.router.loadPage(appUrl + "/pay/index.htm?tradeNo="
						+ xhr.responseText);
			});

			$$('form.ajax-submit.item-list-cart').on('submitted', function(e) {
						myApp.hideIndicator();

						portal_homepage_cart_stats();
					});

			$$('form.ajax-submit.item-list-trade').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('form.ajax-submit.item-list-cart').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('.close-picker').on('click', function() {
				$('.page-content .item-list-overlay')
						.removeClass('item-list-overlay-visible');

				item_list_picker_flag = false;
			});
		});

function item_list_cart_add(itemId, skuId, quantity) {
	myApp.showIndicator();

	$$('#item_list_cart_itemId').val(itemId);
	$$('#item_list_cart_skuId').val(skuId);
	$$('#item_list_cart_quantity').val(quantity);

	$$('#item/list/cart').trigger("submit");
}

item_list_picker_flag = false;

function item_list_picker() {
	if (item_list_picker_flag) {
		myApp.closeModal('.picker-modal.picker-item-list');
		$('.page-content .item-list-overlay')
				.removeClass('item-list-overlay-visible');

		item_list_picker_flag = false;
	} else {
		$('.page-content .item-list-overlay')
				.addClass('item-list-overlay-visible');
		myApp.pickerModal('.picker-modal.picker-item-list');

		item_list_picker_flag = true;
	}
}

function item_list_minus(cartId) {
	var q = $$('#cart/index/quantity/' + cartId).val();

	if (q == 1) {
		return;
	}

	item_list_num(cartId, dcmSub(q, 1));
}

function item_list_plus(cartId) {
	var q = $$('#cart/index/quantity/' + cartId).val();
	item_list_num(cartId, dcmAdd(q, 1));
}

function item_list_num(cartId, quantity) {
	$$.get(appUrl + '/cart/num.htm', {
				cartId : cartId,
				quantity : quantity
			}, function(data) {
				$$('#cart/index/quantity/' + cartId).val(data);
				$$('#cart/index/quantity/edited/' + cartId).html('×' + data);
			});
}

function item_list_scan() {
	wx.scanQRCode({
		needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		scanType : ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		success : function(res) {
			var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果

			var mySearchbar = $$('.searchbar.item-list-searchbar')[0].f7Searchbar;
			mySearchbar.search(result.split(',')[1]);
		}
	});
}
