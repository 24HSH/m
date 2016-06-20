myApp.onPageInit('item.detail', function(page) {
			$$('form.ajax-submit.item-detail-trade').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.item-detail-cart').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.item-detail-trade').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;

						view2.router.loadPage(appUrl
								+ "/pay/index.htm?tradeNo=" + xhr.responseText);
					});

			$$('form.ajax-submit.item-detail-cart').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									// 更新首页购物车标记
									portal_homepage_cart_stats();
								});
					});

			$$('form.ajax-submit.item-detail-trade').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('form.ajax-submit.item-detail-cart').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			//
			var specCat = $$('#item/detail/specCat').val();;
			specCat = (specCat != '') ? jQuery.parseJSON(specCat) : '';
			var specItem = $$('#item/detail/specItem').val();;
			specItem = (specItem != '') ? jQuery.parseJSON(specItem) : '';

			$.each(specItem, function() {
						$$("#item/detail/specItem/" + this.specCId)
								.append("<span class='unchecked' name='span"
										+ this.specCId
										+ "' checked='false' id='"
										+ this.specItemId
										+ "' onclick='item_detail_change(this);'>"
										+ this.specItemValue + "</span> ");
					});

			//

			$$('.close-picker').on('click', function() {
				$('.page-content .item-detail-overlay')
						.removeClass('item-detail-overlay-visible');
			});
		});

function item_detail_change(span) {
	$('span[name="' + $(span).attr('name') + '"]').each(function() {
				if (this.checked && this != span) {
					this.className = "unchecked";
					this.checked = false;
				}
			});
	span.className = "checked";
	span.checked = true;
}

function item_detail_picker(type) {
	$('.page-content .item-detail-overlay')
			.addClass('item-detail-overlay-visible');

	if (type == 'cart') {
		$$('#picker/item/detail/trade').hide();
		$$('#picker/item/detail/cart').show();
		myApp.pickerModal('.picker-item-detail');
	} else if (type == 'trade') {
		$$('#picker/item/detail/cart').hide();
		$$('#picker/item/detail/trade').show();
		myApp.pickerModal('.picker-item-detail');
	}
}

function item_detail_trade(itemId, skuId) {
	myApp.closeModal('.picker-item-detail');
	$('.page-content .item-detail-overlay')
			.removeClass('item-detail-overlay-visible');

	myApp.showIndicator();

	$$('#item_detail_trade_itemId').val(itemId);
	$$('#item_detail_trade_skuId').val(skuId);
	$$('#item_detail_trade_quantity').val($$('#item/detail/quantity').val());

	$$('#item/detail/trade').trigger("submit");
}

function item_detail_cart(itemId, skuId) {
	myApp.closeModal('.picker-item-detail');
	$('.page-content .item-detail-overlay')
			.removeClass('item-detail-overlay-visible');

	myApp.showIndicator();

	$$('#item_detail_cart_itemId').val(itemId);
	$$('#item_detail_cart_skuId').val(skuId);
	$$('#item_detail_cart_quantity').val($$('#item/detail/quantity').val());

	$$('#item/detail/cart').trigger("submit");
}

function item_detail_minus() {
	var q = $$('#item/detail/quantity').val();

	if (q == 1) {
		return;
	}

	item_detail_num(dcmSub(q, 1));
}

function item_detail_plus() {
	var q = $$('#item/detail/quantity').val();
	item_detail_num(dcmAdd(q, 1));
}

function item_detail_num(quantity) {
	$$('#item/detail/quantity').val(quantity);
}