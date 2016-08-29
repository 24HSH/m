// Initialize your app
var myApp = new Framework7({
			animateNavBackIcon : true,
			animatePages : Framework7.prototype.device.ios,
			pushState : true,
			swipePanel : 'false',
			modalButtonOk : '确认',
			modalButtonCancel : '取消',
			imagesLazyLoadPlaceholder : imgUrl + '/image/loading.png',
			// Hide and show indicator during ajax requests
			onAjaxStart : function(xhr) {
				myApp.showIndicator();
			},
			onAjaxComplete : function(xhr) {
				myApp.hideIndicator();
			}
		});

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
			// Because we use fixed-through navbar we can enable dynamic navbar
			dynamicNavbar : true
		});

new Swiper('.swiper-container', {
			speed : 1000,
			autoplay : 2000,
			loop : true
		});

// ==============================

// var view_left = myApp.addView('#view-left', { dynamicNavbar : true });
// view_left.router.reloadPage(appUrl + "/item/cat.htm");

var view2 = myApp.addView('#view-2', {
			dynamicNavbar : true
		});
$$('#href-2').on('click', function() {
			if (view2.history.length == 1) {
				view2.router.load({
							url : appUrl + "/group/list.htm"
						});
			}
		});

var view3 = myApp.addView('#view-3', {
			dynamicNavbar : true
		});
$$('#href-3').on('click', function() {
			if (view3.history.length == 1) {
				view3.router.load({
							url : appUrl + "/facebook/index.htm"
						});
			}
		});

var view4 = myApp.addView('#view-4', {
			dynamicNavbar : true
		});
$$('#href-4').on('click', function() {
			if (view4.history.length == 1) {
				view4.router.load({
							url : appUrl + "/cart/index.htm",
							ignoreCache : true,
							reload : true
						});
			}
		});

var view5 = myApp.addView('#view-5', {
			dynamicNavbar : true
		});
$$('#href-5').on('click', function() {
			if (view5.history.length == 1) {
				view5.router.load({
							url : appUrl + "/trade/list.htm?code=view",
							ignoreCache : true,
							reload : true
						});
			}
		});

var view6 = myApp.addView('#view-6', {
			dynamicNavbar : true
		});
$$('#href-6').on('click', function() {
			if (view6.history.length == 1) {
				view6.router.load({
							url : appUrl + "/member/index.htm",
							ignoreCache : true,
							reload : true
						});
			}
		});

// ==============================

function portal_homepage_cart_stats() {
	$$.get(appUrl + '/cart/stats.htm', {}, function(data) {
				if (data > 0) {
					$$('#portal/homepage/cart').addClass('badge bg-red');
					$$('#portal/homepage/cart').html(data);
				} else {
					$$('#portal/homepage/cart').removeClass('badge bg-red');
					$$('#portal/homepage/cart').html('');
				}
			});
}

portal_homepage_cart_stats();

myApp.onPageInit('portal.homepage', function(page) {
			portal_homepage_cart_stats();
		});

myApp.onPageInit('deliver.time', function(page) {
			$$('form.ajax-submit.deliver-time-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.deliver-time-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									myApp.getCurrentView().router.back({
												url : appUrl
														+ "/pay/index.htm?tradeNo="
														+ $$('#deliver_time_tradeNo')
																.val(),
												force : true,
												ignoreCache : true
											});
								});
					});

			$$('form.ajax-submit.deliver-time-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('.content-block-inner .date').find('p').each(function() {
				$$(this).click(function() {
					if ($$(this).hasClass("cur")) {
						return;
					}

					var date = $$('.date  .sendDate.cur');
					// 1. 日期
					date.removeClass("cur");
					// 2. 时间
					var tab = date.data("tab");
					$$('.content-block-inner .time').find('div').each(
							function() {
								if (tab == $$(this).data("tab")) {
									$$(this).hide();
								}
							});
					// 3. 时间
					$$('.time .sendTime.cur').removeClass("cur");

					// 1. 日期
					$$(this).addClass("cur");
					// 2. 时间
					var tab = $$(this).data("tab");
					$$('.content-block-inner .time').find('div').each(
							function() {
								if (tab == $$(this).data("tab")) {
									$$(this).show();
								}
							});
				});
			});

			$$('.time .time_div').find('p').each(function() {
						$$(this).click(function() {
									if ($$(this).hasClass("cur")) {
										return;
									}

									$$('.time .sendTime.cur')
											.removeClass("cur");

									$$(this).addClass("cur");
								});
					});
		});

function deliver_time_set() {
	$$('#deliver_time_date').val($$('.date .sendDate.cur').data("tab"));

	var time = $$('.time .sendTime.cur');
	$$('#deliver_time_startTime').val(time.data("starttime"));
	$$('#deliver_time_endTime').val(time.data("endtime"));

	myApp.showIndicator();

	$$('#deliver/time/set').trigger("submit");
}
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
				var cartIds = document.getElementById("item/list/form")
						.getElementsByTagName("INPUT");
				for (var i = 0; i < cartIds.length; i++) {
					var v = cartIds[i];
					if (v.name == 'cartIds') {
						$$(v).prop('checked', true);
					}
				}

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
myApp.onPageInit('member.index', function(page) {
			// member_index_stats();
		});

function member_index_stats() {
	$$.get(appUrl + '/trade/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/topay').html(stats[0]);
				$$('#member/index/tosend').html(stats[1]);
				$$('#member/index/send').html(stats[2]);
				$$('#member/index/sign').html(stats[3]);
			});
}
myApp.onPageInit('pay.index', function(page) {
			$$('form.ajax-submit.pay-index-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.pay-index-form').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						getBrandWCPayRequest(xhr.responseText);

						// 更新首页购物车标记
						portal_homepage_cart_stats();
					});

			$$('form.ajax-submit.pay-index-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function pay_index_pay() {
	myApp.showIndicator();

	$$('#pay_index_wxpay_remark').val($$('#pay_index_input_remark').val());
	$$('#pay/index/wxpay').trigger("submit");
}

function getBrandWCPayRequest(data) {
	data = data.replace(/&quot;/g, "\"");
	var obj = JSON.parse(data);

	try {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
					"appId" : obj.appId,
					"timeStamp" : obj.timeStamp,
					"nonceStr" : obj.nonceStr,
					"package" : obj.packageValue,
					"signType" : obj.signType,
					"paySign" : obj.paySign
				}, function(res) {
					WeixinJSBridge.log(res.err_msg);

					if (res.err_msg == 'get_brand_wcpay_request:ok') {
						myApp.getCurrentView().router.back();
					} else if (res.err_msg == 'get_brand_wcpay_request:fail') {
						myApp.alert(res.err_code + res.err_desc + res.err_msg,
								'错误');
					}
				});
	} catch (e) {
		myApp.alert(e, '错误');
	}
}
myApp.onPageInit('trade.detail', function(page) {
			$$('form.ajax-submit.trade-detail-delete').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-detail-copy').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-detail-delete').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						// member_index_stats();
						myApp.getCurrentView().router.back({
									url : myApp.getCurrentView().history[0],
									force : true,
									ignoreCache : true
								});
					});

			$$('form.ajax-submit.trade-detail-copy').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						// member_index_stats();
						myApp.getCurrentView().router.load({
									url : appUrl + "/item/list.htm?shopId="
											+ xhr.responseText,
									ignoreCache : true
								});
					});

			$$('form.ajax-submit.trade-detail-delete').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('form.ajax-submit.trade-detail-copy').on('submitError',
					function(e) {
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
myApp.onPageInit('trade.list', function(page) {
	// 下拉刷新页面
	var ptrContent = $$('.pull-to-refresh-content');

	// 添加'refresh'监听器
	ptrContent.on('refresh', function(e) {
				// 模拟1s的加载过程
				setTimeout(function() {
							// 列表元素的HTML字符串
							var itemHTML = '';
							// 前插新列表元素
							// ptrContent.find('.more').prepend(itemHTML);
							// 加载完毕需要重置
							myApp.pullToRefreshDone();
						}, 1000);
			});

	$$('form.ajax-submit.trade-list-cancel').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.trade-list-sign').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.trade-list-copy').on('beforeSubmit', function(e) {
			});

	$$('form.ajax-submit.trade-list-cancel').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				// member_index_stats();
				myApp.getCurrentView().router.refreshPage();
			});

	$$('form.ajax-submit.trade-list-sign').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				// member_index_stats();
				myApp.getCurrentView().router.refreshPage();
			});

	$$('form.ajax-submit.trade-list-copy').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				// member_index_stats();
				myApp.getCurrentView().router.load({
							url : appUrl + "/item/list.htm?shopId="
									+ xhr.responseText,
							ignoreCache : true
						});
			});

	$$('form.ajax-submit.trade-list-cancel').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

	$$('form.ajax-submit.trade-list-sign').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

	$$('form.ajax-submit.trade-list-copy').on('submitError', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '错误');
			});

		// trade_list_stats();
	});

function trade_list(type, code) {
	myApp.getCurrentView().router.load({
				url : appUrl + "/trade/list.htm?type=" + type + "&code=" + code,
				ignoreCache : true,
				reload : true
			});
}

function trade_list_cancel(tradeNo) {
	myApp.confirm('确定取消订单？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade_list_cancel_tradeNo').val(tradeNo);
				$$('#trade/list/cancel').trigger("submit");
			});
}

function trade_list_sign(tradeNo) {
	myApp.confirm('确定收货？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade_list_sign_tradeNo').val(tradeNo);
				$$('#trade/list/sign').trigger("submit");
			});
}

function trade_list_copy(tradeNo) {
	myApp.showIndicator();

	$$('#trade_list_copy_tradeNo').val(tradeNo);
	$$('#trade/list/copy').trigger("submit");
}

function trade_list_stats() {
	$$.get(appUrl + '/trade/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#trade/list/topay').html(stats[0]);
				$$('#trade/list/tosend').html(stats[1]);
				$$('#trade/list/send').html(stats[2]);
				$$('#trade/list/sign').html(stats[3]);
			});
}
myApp.onPageInit('trade.refund', function(page) {
			$$('form.ajax-submit.trade-refund-refund').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-refund-refund').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									member_index_stats();
									myApp.getCurrentView().router.back();
								});
					});

			$$('form.ajax-submit.trade-refund-refund').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function trade_refund_refund(tradeNo) {
	myApp.confirm('确定申请退款？', '订单管理', function() {
				myApp.showIndicator();

				$$('#trade/refund/refund').trigger("submit");
			});
}
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