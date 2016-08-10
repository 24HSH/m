myApp.onPageInit('group.detail', function(page) {
			$$('form.ajax-submit.group-detail-trade').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.group-detail-cart').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.group-detail-trade').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;

						view2.router.loadPage(appUrl
								+ "/pay/index.htm?tradeNo=" + xhr.responseText);
					});

			$$('form.ajax-submit.group-detail-cart').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									// 更新首页购物车标记
									portal_homepage_cart_stats();
								});
					});

			$$('form.ajax-submit.group-detail-trade').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('form.ajax-submit.group-detail-cart').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			//
			group_detail_specCat = $$('#group/detail/specCat').val();;
			group_detail_specCat = (group_detail_specCat != '') ? jQuery
					.parseJSON(group_detail_specCat) : '';

			group_detail_specItem = $$('#group/detail/specItem').val();;
			group_detail_specItem = (group_detail_specItem != '') ? jQuery
					.parseJSON(group_detail_specItem) : '';

			$.each(group_detail_specItem, function() {
						$$("#group/detail/specItem/0/" + this.specCId)
								.html($$("#group/detail/specItem/0/"
										+ this.specCId).html()
										+ this.specItemValue + " ");

						$$("#group/detail/specItem/" + this.specCId)
								.append("<span class='tag sku-tag' name='tag"
										+ this.specCId
										+ "' checked='false' id='"
										+ this.specItemId
										+ "' onclick='group_detail_change(this);'>"
										+ this.specItemValue + "</span> ");
					});

			//
			group_detail_skuId = "0";

			$$('.picker-modal.picker-group-detail .close-picker').on('click',
					function() {
						$('.page-content .group-detail-overlay')
								.removeClass('group-detail-overlay-visible');
					});
		});

function group_detail_change(tag) {
	$('span[name="' + $(tag).attr('name') + '"]').each(function() {
				if (this.checked && this != tag) {
					this.className = "tag sku-tag";
					this.checked = false;
				}
			});
	tag.className = "tag sku-tag active";
	tag.checked = true;

	var properties = "";
	$.each(group_detail_specCat, function() {
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
		var o = document.getElementById("group_detail_price_" + properties);
		if (o == null) {
			$$("#group/detail/price").html("暂无此规格商品");
			$$("#group/detail/stock").html("");
		} else {
			$$("#group/detail/price").html("￥" + o.value);
			$$("#group/detail/stock").html("剩余"
					+ document.getElementById("group_detail_stock_"
							+ properties).value + "件");

			group_detail_skuId = document.getElementById("group_detail_sku_"
					+ properties).value;
		}
	} catch (e) {
	}
}

function group_detail_picker(type) {
	$('.page-content .group-detail-overlay')
			.addClass('group-detail-overlay-visible');

	if (type == 'cart') {
		$$('#picker/group/detail/trade').hide();
		$$('#picker/group/detail/cart').show();
		myApp.pickerModal('.picker-group-detail');
	} else if (type == 'trade') {
		$$('#picker/group/detail/cart').hide();
		$$('#picker/group/detail/trade').show();
		myApp.pickerModal('.picker-group-detail');
	}
}

function group_detail_trade(skuId) {
	myApp.closeModal('.picker-group-detail');
	$('.page-content .group-detail-overlay')
			.removeClass('group-detail-overlay-visible');

	myApp.showIndicator();

	$$('#group_detail_trade_skuId')
			.val(skuId == "0" ? "0" : group_detail_skuId);
	$$('#group_detail_trade_quantity').val($$('#group/detail/quantity').val());

	$$('#group/detail/trade').trigger("submit");
}

function group_detail_cart(skuId) {
	myApp.closeModal('.picker-group-detail');
	$('.page-content .group-detail-overlay')
			.removeClass('group-detail-overlay-visible');

	myApp.showIndicator();

	$$('#group_detail_cart_skuId').val(skuId == "0" ? "0" : group_detail_skuId);
	$$('#group_detail_cart_quantity').val($$('#group/detail/quantity').val());

	$$('#group/detail/cart').trigger("submit");
}

function group_detail_minus() {
	var q = $$('#group/detail/quantity').val();

	if (q == 1) {
		return;
	}

	group_detail_num(dcmSub(q, 1));
}

function group_detail_plus() {
	var q = $$('#group/detail/quantity').val();
	group_detail_num(dcmAdd(q, 1));
}

function group_detail_num(quantity) {
	$$('#group/detail/quantity').val(quantity);
}