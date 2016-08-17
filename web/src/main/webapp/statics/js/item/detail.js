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

						myApp.getCurrentView().router.loadPage(appUrl
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
			item_detail_specCat = $$('#item/detail/specCat').val();;
			item_detail_specCat = (item_detail_specCat != '') ? jQuery
					.parseJSON(item_detail_specCat) : '';

			item_detail_specItem = $$('#item/detail/specItem').val();;
			item_detail_specItem = (item_detail_specItem != '') ? jQuery
					.parseJSON(item_detail_specItem) : '';

			$.each(item_detail_specItem, function() {
						$$("#item/detail/specItem/0/" + this.specCId)
								.html($$("#item/detail/specItem/0/"
										+ this.specCId).html()
										+ this.specItemValue + " ");

						$$("#item/detail/specItem/" + this.specCId)
								.append("<span class='tag sku-tag' name='tag"
										+ this.specCId
										+ "' checked='false' id='"
										+ this.specItemId
										+ "' onclick='item_detail_change(this);'>"
										+ this.specItemValue + "</span> ");
					});

			//
			item_detail_skuId = "0";

			$$('.picker-modal.picker-item-detail .close-picker').on('click',
					function() {
						$('.page-content .item-detail-overlay')
								.removeClass('item-detail-overlay-visible');
					});
		});

function item_detail_change(tag) {
	$('span[name="' + $(tag).attr('name') + '"]').each(function() {
				if (this.checked && this != tag) {
					this.className = "tag sku-tag";
					this.checked = false;
				}
			});
	tag.className = "tag sku-tag active";
	tag.checked = true;

	var properties = "";
	$.each(item_detail_specCat, function() {
				var specCId = this.specCId;
				$('span[name="tag' + specCId + '"]').each(function() {
							if (this.checked) {
								if (properties != "") {
									properties += ";";
								}
								properties += specCId + ":" + this.id;
							}
						});
			});

	try {
		var o = document.getElementById("item_detail_price_" + properties);
		if (o == null) {
			$$("#item/detail/price").html("暂无此规格商品");
			$$("#item/detail/stock").html("");
		} else {
			$$("#item/detail/price").html("￥" + o.value);
			$$("#item/detail/stock")
					.html("剩余"
							+ document.getElementById("item_detail_stock_"
									+ properties).value + "件");

			item_detail_skuId = document.getElementById("item_detail_sku_"
					+ properties).value;
		}
	} catch (e) {
	}
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

function item_detail_trade(skuId) {
	myApp.closeModal('.picker-item-detail');
	$('.page-content .item-detail-overlay')
			.removeClass('item-detail-overlay-visible');

	myApp.showIndicator();

	$$('#item_detail_trade_skuId').val(skuId == "0" ? "0" : item_detail_skuId);
	$$('#item_detail_trade_quantity').val($$('#item/detail/quantity').val());

	$$('#item/detail/trade').trigger("submit");
}

function item_detail_cart(skuId) {
	myApp.closeModal('.picker-item-detail');
	$('.page-content .item-detail-overlay')
			.removeClass('item-detail-overlay-visible');

	myApp.showIndicator();

	$$('#item_detail_cart_skuId').val(skuId == "0" ? "0" : item_detail_skuId);
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