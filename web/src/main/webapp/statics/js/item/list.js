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
						myApp.alert(xhr.responseText, '信息', function() {
								});
					});

			$$('form.ajax-submit.item-list-cart').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									// 更新首页购物车标记
									portal_homepage_cart_stats();
								});
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

			$$('.open-picker').on('click', function() {
				$('.page-content .item-list-overlay')
						.addClass('item-list-overlay-visible');
			});
			$$('.close-picker').on('click', function() {
				$('.page-content .item-list-overlay')
						.removeClass('item-list-overlay-visible');
			});
		});

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

function item_list_trade(itemId, skuId) {
	myApp.closeModal('.picker-' + itemId + '-' + skuId);
	$('.page-content .item-list-overlay')
			.removeClass('item-list-overlay-visible');

	myApp.showIndicator();

	$$('#item_list_trade_itemId').val(itemId);
	$$('#item_list_trade_skuId').val(skuId);
	$$('#item_list_trade_quantity').val($$('#item/list/quantity/' + itemId
			+ '/' + skuId).val());

	$$('#item/list/trade').trigger("submit");
}

function item_list_cart(itemId, skuId) {
	myApp.closeModal('.picker-' + itemId + '-' + skuId);
	$('.page-content .item-list-overlay')
			.removeClass('item-list-overlay-visible');

	myApp.showIndicator();

	$$('#item_list_cart_itemId').val(itemId);
	$$('#item_list_cart_skuId').val(skuId);
	$$('#item_list_cart_quantity').val($$('#item/list/quantity/' + itemId + '/'
			+ skuId).val());

	$$('#item/list/cart').trigger("submit");
}

function item_list_minus(id) {
	var q = $$('#item/list/quantity/' + id).val();

	if (q == 1) {
		return;
	}

	item_list_num(id, dcmSub(q, 1));
}

function item_list_plus(id) {
	var q = $$('#item/list/quantity/' + id).val();
	item_list_num(id, dcmAdd(q, 1));
}

function item_list_num(id, quantity) {
	$$('#item/list/quantity/' + id).val(quantity);
}
