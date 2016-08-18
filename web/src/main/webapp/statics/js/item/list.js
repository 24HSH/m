myApp.onPageInit('item.list', function(page) {
			$$('form.ajax-submit.item-list-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.item-list-cart').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.item-list-form').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;

				if (item_list_flag == "create") {
					myApp.getCurrentView().router.load({
								url : appUrl + "/pay/index.htm?tradeNo="
										+ xhr.responseText
							});
				} else if (item_list_flag == "remove") {
					myApp.getCurrentView().router.refreshPage();

					// 更新首页购物车标记
					portal_homepage_cart_stats();
				}
			});

			$$('form.ajax-submit.item-list-cart').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;

				if (item_list_flag == "add") {
					if (xhr.responseText != '0') {
						item_list_cart_update_a(xhr.responseText,
								item_list_itemId, item_list_skuId,
								item_list_itemName, item_list_price);
					}
				} else if (item_list_flag == "delete") {
					item_list_cart_update_d(item_list_cartId);
				}

				item_list_stats();

				// 更新首页购物车标记
				portal_homepage_cart_stats();
			});

			$$('form.ajax-submit.item-list-form').on('submitError',
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

			// 购物车
			item_list_picker_flag = false;

			$$('.page-content .item-list-overlay').on('click', function() {
						myApp.closeModal('.picker-modal.picker-item-list');
						item_list_overlay_removeClass();
					});

			// 合计金额
			item_list_stats();

			// 根据 合计金额判断 是否 自动 弹出 购物车
			if ($$("#item/list/price").html() != '购物车是空的') {
				var itemCId = $$('.a47').data("itemcid");
				if (itemCId === undefined) {
					item_list_picker();
				}
			}

			// 商品类目
			$$('.a47 .a3u').find('li').each(function() {
				$$(this).click(function() {
					if ($$(this).hasClass("a3x")) {
						return;
					}

					var itemCId = $$(this).data("itemcid");
					var url;
					if (itemCId === undefined) {
						url = appUrl + "/item/list.htm?shopId="
								+ $$(this).data("shopid");
					} else {
						url = appUrl + "/item/list.htm?shopId="
								+ $$(this).data("shopid") + "&itemCId="
								+ itemCId;
					}
					myApp.getCurrentView().router.load({
								url : url,
								ignoreCache : true,
								reload : true
							});
				});
			});

			// 全选按钮
			$$('.picker-modal.picker-item-list .left .response-area').on(
					'click', function() {
						if ($$('#item/list/check').prop('checked')) {
							$$('#item/list/check').prop('checked', false);

							var cartIds = document
									.getElementById("item/list/form")
									.getElementsByTagName("INPUT");
							for (var i = 0; i < cartIds.length; i++) {
								var v = cartIds[i];
								if (v.name == 'cartIds') {
									$$(v).prop('checked', false);
								}
							}

							$$('.picker-modal.picker-item-list .a86').html("");
							$$("#item/list/price").html("￥0.00");
						} else {
							var cartIds = document
									.getElementById("item/list/form")
									.getElementsByTagName("INPUT");
							for (var i = 0; i < cartIds.length; i++) {
								var v = cartIds[i];
								if (v.name == 'cartIds') {
									$$(v).prop('checked', true);
								}
							}

							// 重新计算
							item_list_stats();
						}
					});

			// 监听 checkbox
			var cartIds = document.getElementById("item/list/form")
					.getElementsByTagName("INPUT");
			for (var i = 0; i < cartIds.length; i++) {
				var v = cartIds[i];
				if (v.name == 'cartIds') {
					v.onchange = function(e) {
						// 重新计算
						item_list_stats();
					};
				}
			}
		});

var item_list_flag;

var item_list_itemId;
var item_list_skuId;
var item_list_itemName;
var item_list_price;
var item_list_cartId;

function item_list_cart_add(itemId, skuId, quantity, itemName, price) {
	item_list_flag = "add";

	myApp.showIndicator();

	item_list_itemId = itemId;
	item_list_skuId = skuId;
	item_list_itemName = itemName;
	item_list_price = price;

	$$('#item_list_cart_itemId').val(itemId);
	$$('#item_list_cart_skuId').val(skuId);
	$$('#item_list_cart_quantity').val(quantity);

	$$('#item/list/cart').attr("action", appUrl + "/cart/add.htm");

	$$('#item/list/cart').trigger("submit");
}

function item_list_cart_delete(cartId) {
	item_list_flag = "delete";

	myApp.showIndicator();

	item_list_cartId = cartId;

	$$('#item_list_cart_cartId').val(cartId);

	$$('#item/list/cart').attr("action", appUrl + "/cart/remove.htm");

	$$('#item/list/cart').trigger("submit");
}

function item_list_trade_create() {
	item_list_flag = "create";

	myApp.showIndicator();

	$$('#item/list/form').attr("action", appUrl + "/trade/create.htm");

	$$('#item/list/form').trigger("submit");
}

function item_list_cart_remove() {
	myApp.confirm('清空购物车中所有商品？', '提示', function() {
				item_list_flag = "remove";

				myApp.showIndicator();

				$$('#item/list/form').attr("action",
						appUrl + "/cart/remove.htm");

				$$('#item/list/form').trigger("submit");
			});
}

function item_list_cart_update_a(cartId, itemId, skuId, itemName, price) {
	var obj = $$('#item/list/quantity/' + cartId);
	if (obj.length == 1) {
		// 1. 数量增加
		obj.val(dcmAdd(obj.val(), 1));

		// 2. $$(v).prop('checked', true);
		var cartIds = document.getElementById("item/list/form")
				.getElementsByTagName("INPUT");
		for (var i = 0; i < cartIds.length; i++) {
			var v = cartIds[i];
			if (v.name == 'cartIds' && v.value == cartId) {
				$$(v).prop('checked', true);
			}
		}

		return;
	}

	var hmtl0 = '<div class="quantity quantity-op-'
			+ cartId
			+ '" data-itemId="'
			+ itemId
			+ '" data-skuId="'
			+ skuId
			+ '">'
			+ '<button class="minus" type="button"></button>'
			+ '<input id="item/list/quantity/0/'
			+ cartId
			+ '" type="text" class="txt" value="1" />'
			+ '<button class="plus" type="button"></button>'
			+ '<div class="response-area response-area-minus" onclick="item_list_minus('
			+ cartId
			+ ');"></div>'
			+ '<div class="response-area response-area-plus" onclick="item_list_plus('
			+ cartId + ');"></div>' + '</div>';

	$$('.a43 .a-op-' + itemId + '-' + skuId).hide();
	$$('.a43 .item-after-op-' + itemId + '-' + skuId).append(hmtl0);

	var hmtl1 = '<li id="item_list_cart_'
			+ cartId
			+ '">'
			+ '<div class="item-content">'
			+ '<label class="label-checkbox">'
			+ '<input type="checkbox" name="cartIds" value="'
			+ cartId
			+ '" checked></input>'
			+ '<div class="item-media" style="width: 28px;"><i class="icon icon-form-checkbox"></i></div>'
			+ '</label>'
			+ '<div class="item-inner">'
			+ '<div class="item-title">'
			+ itemName
			+ '</div>'
			+ '<div class="item-after">'
			+ '<span style="padding-top: 3px; padding-right: 10px; color: #ff5000;">￥'
			+ Number.format(price, 2)
			+ '</span>'
			+ '<input type="hidden" id="item/list/price/'
			+ cartId
			+ '" value="'
			+ price
			+ '" />'
			+ '<div class="quantity">'
			+ '<button class="minus" type="button"></button>'
			+ '<input id="item/list/quantity/'
			+ cartId
			+ '" type="text" class="txt" value="1" />'
			+ '<button class="plus" type="button"></button>'
			+ '<div class="response-area response-area-minus" onclick="item_list_minus('
			+ cartId
			+ ');"></div>'
			+ '<div class="response-area response-area-plus" onclick="item_list_plus('
			+ cartId + ');"></div>' + '</div>' + '</div>' + '</div>' + '</div>'
			+ '</li>';

	$$('#item_list_cart').prepend(hmtl1);
}

function item_list_cart_update_d(cartId) {
	var q_op = $$('.a43 .quantity-op-' + cartId);
	var itemId = q_op.data("itemid");
	var skuId = q_op.data("skuid");

	$$('.a43 .quantity-op-' + cartId).remove();
	$$('.a43 .a-op-' + itemId + '-' + skuId).show();

	$$('#item_list_cart_' + cartId).remove();
}

function item_list_stats() {
	var num = 0;
	var num_ = 0;
	var total = 0;

	var cartIds = document.getElementById("item/list/form")
			.getElementsByTagName("INPUT");
	// 控制全选按钮状态
	var checked_flag = true;
	for (var i = 0; i < cartIds.length; i++) {
		var v = cartIds[i];
		if (v.name == 'cartIds') {
			var q = $$("#item/list/quantity/" + v.value).val();
			num = dcmAdd(num, q);
			if (v.checked) {
				num_ = dcmAdd(num_, q);
				total = dcmAdd(total, dcmMul($$("#item/list/price/" + v.value)
										.val(), q));
			} else {
				checked_flag = false;
			}
		}
	}

	if (checked_flag) {
		$$('#item/list/check').prop('checked', true);
	} else {
		$$('#item/list/check').prop('checked', false);
	}

	if (num > 0) {
		$$(".toolbar .toolbar-inner .a6s").html(num);
		if (num_ > 0) {
			$$('.picker-modal.picker-item-list .a86').html("(已选" + num_ + "件)");
		} else {
			$$('.picker-modal.picker-item-list .a86').html("");
		}
		$$("#item/list/price").html("￥"
				+ (total == 0 ? '0.00' : Number.format(total, 2)));
	} else {
		$$(".toolbar .toolbar-inner .a6s").html("0");
		$$('.picker-modal.picker-item-list .a86').html("");
		$$("#item/list/price").html("购物车是空的");
		// 如果 弹出购物车 则 自动关闭
		if (item_list_picker_flag) {
			item_list_picker();
		}
	}
}

function item_list_minus(cartId) {
	var q = $$('#item/list/quantity/' + cartId).val();

	if (q == 1) {
		item_list_cart_delete(cartId);
		return;
	}

	item_list_num(cartId, dcmSub(q, 1));
}

function item_list_plus(cartId) {
	var q = $$('#item/list/quantity/' + cartId).val();
	item_list_num(cartId, dcmAdd(q, 1));
}

function item_list_num(cartId, quantity) {
	$$.get(appUrl + '/cart/num.htm', {
				cartId : cartId,
				quantity : quantity
			}, function(data) {
				// 1. 更新
				$$('#item/list/quantity/0/' + cartId).val(data);
				$$('#item/list/quantity/' + cartId).val(data);

				// 2. $$(v).prop('checked', true);
				var cartIds = document.getElementById("item/list/form")
						.getElementsByTagName("INPUT");
				for (var i = 0; i < cartIds.length; i++) {
					var v = cartIds[i];
					if (v.name == 'cartIds' && v.value == cartId) {
						$$(v).prop('checked', true);
					}
				}

				item_list_stats();
			});
}

function item_list_picker() {
	// 如果 购物车弹出 并且 已空 则 自动关闭
	if (!item_list_picker_flag && $$("#item/list/price").html() == '购物车是空的') {
		return;
	}

	if (item_list_picker_flag) {
		myApp.closeModal('.picker-modal.picker-item-list');
		item_list_overlay_removeClass();
	} else {
		item_list_overlay_addClass();
		myApp.pickerModal('.picker-modal.picker-item-list');
	}
}

function item_list_overlay_removeClass() {
	$$('.page-content .item-list-overlay')
			.removeClass('item-list-overlay-visible');

	item_list_picker_flag = false;
}

function item_list_overlay_addClass() {
	$$('.page-content .item-list-overlay')
			.addClass('item-list-overlay-visible');

	item_list_picker_flag = true;
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
